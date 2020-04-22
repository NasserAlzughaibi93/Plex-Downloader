package com.naz.PlexDownloader.services;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.naz.PlexDownloader.dtos.SystemSettingsDTO;
import com.naz.PlexDownloader.exceptions.rest.RecordNotFoundException;
import com.naz.PlexDownloader.models.plex.PlexUser;
import com.naz.PlexDownloader.models.settings.About;
import com.naz.PlexDownloader.models.settings.LoggingLevel;
import com.naz.PlexDownloader.models.settings.SystemSettings;
import com.naz.PlexDownloader.repositories.SettingsRepository;
import com.naz.PlexDownloader.services.plex.PlexUserService;
import com.naz.PlexDownloader.util.BuildVersion;
import com.naz.PlexDownloader.util.ValidationUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsServiceImpl implements SettingsService {

    private static final Log logger = LogFactory.getLog(SettingsServiceImpl.class);

    @Autowired
    private PlexUserService plexUserService;

    @Autowired
    private SettingsRepository settingsRepository;


    /**
     * Find the system settings by ID
     * @param systemSettingsId - the system setting ID.
     * @return {@link SystemSettings}
     */
    @Override
    public SystemSettings retrieveSystemSettingsById(final Long systemSettingsId) {

        if (!this.settingsRepository.findById(systemSettingsId).isPresent())
            throw new RecordNotFoundException("setting.with.given.identifier.could.not.be.found");

        return this.settingsRepository.findById(systemSettingsId).get();
    }

    /**
     * Retrieves the system settings for that specific user.
     *
     * @param authToken - the user auth token
     * @return {@link SystemSettings}
     */
    @Override
    public SystemSettings retrieveSystemSettingsByPlexUserAuthToken(String authToken) {

        PlexUser plexUser = this.plexUserService.retrieveUserByAuthToken(authToken);

        ValidationUtil.NotNullOrEmpty("user.cannot.be.found", plexUser);

        if (!validateIfUserIsServerOwner(plexUser)) {
            throw new RecordNotFoundException("user.is.not.server.owner");
        }

        SystemSettings systemSettings = this.settingsRepository.findByPlexUser(plexUser);

        if (null == systemSettings) {
            systemSettings = this.retrieveSystemSettingsById(1L);//Default system settings
        }

        return systemSettings;
    }

    /**
     * Save the system settings for that user (user must be a server owner)
     *
     * @param systemSettingsDTO - {@link SystemSettingsDTO}
     * @param authToken - the user auth token
     *
     * @return {@link SystemSettings}
     */
    @Override
    public SystemSettings saveSystemSettingsForPlexUser(SystemSettingsDTO systemSettingsDTO, String authToken) {

        PlexUser plexUser = this.plexUserService.retrieveUserByAuthToken(authToken);

        ValidationUtil.NotNullOrEmpty("user.cannot.be.found", plexUser);

        if (!validateIfUserIsServerOwner(plexUser)) {
            throw new RecordNotFoundException("user.is.not.server.owner");
        }

        SystemSettings systemSettings = this.settingsRepository.findByPlexUser(plexUser);

        if (null == systemSettings) {
            systemSettings = new SystemSettings(systemSettingsDTO, plexUser);
        } else {
            systemSettings.setDownloadIntervalInMinutes(systemSettingsDTO.getDownloadIntervalInMinutes());
            systemSettings.setMaxDownloadsPerUser(systemSettingsDTO.getMaxDownloadsPerUser());
            systemSettings.setLoggingLevel(systemSettingsDTO.getLoggingLevel());
            systemSettings.setPlexUser(plexUser);
        }

        return this.saveSystemSettings(systemSettings);
    }

    @Override
    public SystemSettings saveSystemSettings(SystemSettings systemSettings) {
        updateApplicationLogLevel(systemSettings.getLoggingLevel());
        return this.settingsRepository.save(systemSettings);
    }

    /**
     * Retrieve application info such as build version and OS info
     *
     * @return {@link About}
     */
    @Override
    public About retrieveAppInfo() {

        About about = new About();

        about.setVersion(BuildVersion.getProjectVersion());
        about.setBranch(BuildVersion.getProjectBranch());
        about.setOsArchitecture(System.getProperty("os.arch"));
        about.setOsName(System.getProperty("os.name"));
        about.setOsVersion(System.getProperty("os.version"));

        return about;
    }

    /**
     * Validate the user can access the full system settings.
     *
     * @param authToken - the plex user auth token
     * @return boolean
     */
    @Override
    public boolean validateFullSettingsAccess(final String authToken) {

        PlexUser plexUser = this.plexUserService.retrieveUserByAuthToken(authToken);

        return validateIfUserIsServerOwner(plexUser);
    }

    /**
     * Verify that this is the owner of the server. As of now server owner is assumed to be the admin.
     * TODO verify logic for people with access to different servers besides their own
     * @param plexUser {@link PlexUser}
     *
     * @return Whether the user is the server owner or not.
     */
    private boolean validateIfUserIsServerOwner(final PlexUser plexUser) {

        if (logger.isDebugEnabled()) {
            logger.debug("Validating if user '" + plexUser.getUsername() + "' is the server owner");
        }

        return plexUser.getLibraryAuthToken().equals(plexUser.getAuthToken());
    }

    /**
     * Updates the application logging level
     *
     * @param loggingLevel - {@link LoggingLevel}
     */
    private void updateApplicationLogLevel(LoggingLevel loggingLevel) {
        try {
            logger.info("Updating logging level to " + loggingLevel.getLoggingLevel());
            Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
            root.setLevel(Level.toLevel(loggingLevel.toString()));
        } catch (RuntimeException e) {
            logger.error("Could not update application logging level: " + e.getLocalizedMessage());
        }
    }

    public PlexUserService getPlexUserService() {
        return plexUserService;
    }

    public void setPlexUserService(PlexUserService plexUserService) {
        this.plexUserService = plexUserService;
    }
}
