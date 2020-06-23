package com.naz.PlexDownloader.dtos;

import com.naz.PlexDownloader.models.settings.LoggingLevel;

public class SystemSettingsDTO {

    private LoggingLevel loggingLevel;

    private Integer maxDownloadsPerUser;

    private Integer downloadIntervalInMinutes;

    public LoggingLevel getLoggingLevel() {
        return loggingLevel;
    }

    public void setLoggingLevel(LoggingLevel loggingLevel) {
        this.loggingLevel = loggingLevel;
    }

    public Integer getMaxDownloadsPerUser() {
        return maxDownloadsPerUser;
    }

    public void setMaxDownloadsPerUser(Integer maxDownloadsPerUser) {
        this.maxDownloadsPerUser = maxDownloadsPerUser;
    }

    public Integer getDownloadIntervalInMinutes() {
        return downloadIntervalInMinutes;
    }

    public void setDownloadIntervalInMinutes(Integer downloadIntervalInMinutes) {
        this.downloadIntervalInMinutes = downloadIntervalInMinutes;
    }
}
