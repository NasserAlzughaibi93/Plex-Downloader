package com.naz.PlexDownloader.services;

import com.naz.PlexDownloader.dtos.SystemSettingsDTO;
import com.naz.PlexDownloader.exceptions.rest.RecordNotFoundException;
import com.naz.PlexDownloader.models.plex.PlexUser;
import com.naz.PlexDownloader.models.settings.About;
import com.naz.PlexDownloader.models.settings.SystemSettings;
import com.naz.PlexDownloader.repositories.SettingsRepository;
import com.naz.PlexDownloader.services.plex.PlexUserService;
import com.naz.PlexDownloader.util.BuildVersion;
import com.naz.PlexDownloader.util.JwtTokenUtil;
import com.naz.PlexDownloader.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
public class SettingsServiceImpl implements SettingsService {

    @Autowired
    private PlexUserService plexUserService;

    @Autowired
    private SettingsRepository settingsRepository;


    @Override
    public SystemSettings retrieveSystemSettingsById(final Long systemSettingsId) {

        if (!this.settingsRepository.findById(systemSettingsId).isPresent())
            throw new RecordNotFoundException("setting.with.given.identifier.could.not.be.found");

        return this.settingsRepository.findById(systemSettingsId).get();
    }

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
        return this.settingsRepository.save(systemSettings);
    }

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
        return plexUser.getLibraryAuthToken().equals(plexUser.getAuthToken());
    }

    public PlexUserService getPlexUserService() {
        return plexUserService;
    }

    public void setPlexUserService(PlexUserService plexUserService) {
        this.plexUserService = plexUserService;
    }
}
