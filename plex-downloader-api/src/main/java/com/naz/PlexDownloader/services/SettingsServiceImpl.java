package com.naz.PlexDownloader.services;

import com.naz.PlexDownloader.models.settings.About;
import com.naz.PlexDownloader.util.BuildVersion;
import org.springframework.stereotype.Service;

@Service
public class SettingsServiceImpl implements SettingsService {

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


}
