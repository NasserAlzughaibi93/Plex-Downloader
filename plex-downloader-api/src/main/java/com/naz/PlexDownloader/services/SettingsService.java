package com.naz.PlexDownloader.services;

import com.naz.PlexDownloader.models.settings.About;

public interface SettingsService {

    About retrieveAppInfo();
}
