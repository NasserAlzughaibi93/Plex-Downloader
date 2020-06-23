package com.naz.PlexDownloader.models.settings;

import com.naz.PlexDownloader.dtos.SystemSettingsDTO;
import com.naz.PlexDownloader.models.plex.PlexUser;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class SystemSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * TODO A plex server admin can have their own settings for their server.
     */
    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private PlexUser plexUser;

    private LoggingLevel loggingLevel;

    private Integer maxDownloadsPerUser;

    /**
     * how many downloads the user can have active in a 60 minute time frame.
     */
    private Integer downloadIntervalInMinutes;

    public SystemSettings(SystemSettingsDTO systemSettingsDTO, PlexUser plexUser) {
        this.loggingLevel = systemSettingsDTO.getLoggingLevel();
        this.downloadIntervalInMinutes = systemSettingsDTO.getDownloadIntervalInMinutes();
        this.maxDownloadsPerUser = systemSettingsDTO.getMaxDownloadsPerUser();
        this.plexUser = plexUser;
    }

    public SystemSettings(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public PlexUser getPlexUser() {
        return plexUser;
    }

    public void setPlexUser(PlexUser plexUser) {
        this.plexUser = plexUser;
    }
}
