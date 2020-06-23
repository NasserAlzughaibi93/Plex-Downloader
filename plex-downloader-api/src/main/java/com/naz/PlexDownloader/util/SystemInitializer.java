package com.naz.PlexDownloader.util;


import com.naz.PlexDownloader.exceptions.rest.RecordNotFoundException;
import com.naz.PlexDownloader.models.settings.LoggingLevel;
import com.naz.PlexDownloader.models.settings.SystemSettings;
import com.naz.PlexDownloader.services.SettingsService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class SystemInitializer implements InitializingBean {

    private static final Logger logger = Logger.getLogger(SystemInitializer.class.toString());

    @Autowired
    private SettingsService settingsService;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.initializeSystemSettings();
    }

    private void initializeSystemSettings() {

        SystemSettings systemSettings = null;

        try {
            systemSettings = this.settingsService.retrieveSystemSettingsById(1L);
        }catch (RecordNotFoundException e) {
            logger.info("No default settings exists.");
        }

        if (systemSettings == null) {
            systemSettings = new SystemSettings();
            systemSettings.setLoggingLevel(LoggingLevel.INFO);
            systemSettings.setMaxDownloadsPerUser(3);
            systemSettings.setDownloadIntervalInMinutes(60);

            this.settingsService.saveSystemSettings(systemSettings);

            logger.info("initialized default settings");
        } else {
            logger.info("Loaded default settings");
        }
    }

    public SettingsService getSettingsService() {
        return settingsService;
    }

    public void setSettingsService(SettingsService settingsService) {
        this.settingsService = settingsService;
    }
}
