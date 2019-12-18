package com.naz.PlexDownloader.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class BuildVersion {

    private static String projectVersion;


    @Value("${info.build.version}")
    public void setProjectVersion(String version) {
        projectVersion = version;
    }

    public static String getBuildVersion(){
        return projectVersion;
    }
}