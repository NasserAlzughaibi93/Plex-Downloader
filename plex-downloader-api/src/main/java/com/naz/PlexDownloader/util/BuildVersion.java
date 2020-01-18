package com.naz.PlexDownloader.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class BuildVersion {

    private static String projectVersion;
    private static String projectBranch;


    @Value("${info.build.version}")
    public void setProjectVersion(String version) {
        projectVersion = version;
    }

    @Value("${info.build.branch}")
    public void setProjectBranch(String branch) {
        projectBranch = branch;
    }

    public static String getProjectVersion(){
        return projectVersion;
    }

    public static String getProjectBranch() {
        return projectBranch;
    }

    public static void getOSInfo() {

        String osName = System.getProperty("os.name");
        System.out.println(osName);

        String osArch = System.getProperty("os.arch");
        System.out.println(osArch);

        String osVersion = System.getProperty("os.version");
        System.out.println(osVersion);

    }
}