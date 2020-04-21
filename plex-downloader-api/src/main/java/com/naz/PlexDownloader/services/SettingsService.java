package com.naz.PlexDownloader.services;

import com.naz.PlexDownloader.dtos.SystemSettingsDTO;
import com.naz.PlexDownloader.models.settings.About;
import com.naz.PlexDownloader.models.settings.SystemSettings;

public interface SettingsService {

    SystemSettings retrieveSystemSettingsById(final Long systemSettingsId);

    SystemSettings retrieveSystemSettingsByPlexUserAuthToken(final String authToken);

    SystemSettings saveSystemSettingsForPlexUser(final SystemSettingsDTO systemSettingsDTO, final String authToken);

    SystemSettings saveSystemSettings(SystemSettings systemSettings);

    About retrieveAppInfo();

    boolean validateFullSettingsAccess(final String authToken);
}
