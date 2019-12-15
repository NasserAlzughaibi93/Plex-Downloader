package com.naz.PlexDownloader.models.plex;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "MediaContainer")
public class MediaContainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mediaContainerId;

    @XmlAttribute(name = "size")
    private String size;

    @OneToMany
    @XmlElement(name = "Device")
    private List<Device> device;

    @XmlAttribute
    private String mediaTagPrefix;

    @XmlAttribute
    private String identifier;

    @XmlAttribute
    private String allowSync;

    @XmlAttribute
    private String title;

    @XmlAttribute
    private String mediaTagVersion;

    @OneToMany
    @XmlElement(name = "Directory")
    private List<Directory> Directory;

    @OneToMany
    @XmlElement(name = "Video")
    private List<Video> Video;

    @XmlAttribute
    private String mixedParents;

    public Long getMediaContainerId() {
        return mediaContainerId;
    }

    public void setMediaContainerId(Long mediaContainerId) {
        this.mediaContainerId = mediaContainerId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public List<Device> getDevice() {
        return device;
    }

    public void setDevice(List<Device> device) {
        this.device = device;
    }

    public String getMediaTagPrefix() {
        return mediaTagPrefix;
    }

    public void setMediaTagPrefix(String mediaTagPrefix) {
        this.mediaTagPrefix = mediaTagPrefix;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getAllowSync() {
        return allowSync;
    }

    public void setAllowSync(String allowSync) {
        this.allowSync = allowSync;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMediaTagVersion() {
        return mediaTagVersion;
    }

    public void setMediaTagVersion(String mediaTagVersion) {
        this.mediaTagVersion = mediaTagVersion;
    }

    public List<com.naz.PlexDownloader.models.plex.Directory> getDirectory() {
        return Directory;
    }

    public void setDirectory(List<com.naz.PlexDownloader.models.plex.Directory> directory) {
        Directory = directory;
    }

    public List<com.naz.PlexDownloader.models.plex.Video> getVideo() {
        return Video;
    }

    public void setVideo(List<com.naz.PlexDownloader.models.plex.Video> video) {
        Video = video;
    }

    public String getMixedParents() {
        return mixedParents;
    }

    public void setMixedParents(String mixedParents) {
        this.mixedParents = mixedParents;
    }
}
