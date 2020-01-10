
package com.naz.PlexDownloader.models.plex;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Part {

    @Id
    @XmlAttribute(name = "id")
    private Integer id;
    @OneToMany
    @XmlElement(name = "Stream")
    private List<Stream> stream;
    @XmlAttribute(name = "key")
    private String key;
    @XmlAttribute(name = "duration")
    private Integer duration;
    @XmlAttribute(name = "file")
    private String file;
    @XmlAttribute(name = "size")
    private Integer size;
    @XmlAttribute(name = "container")
    private String container;
    @XmlAttribute(name = "videoProfile")
    private String videoProfile;
    @XmlAttribute(name = "audioProfile")
    private String audioProfile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Stream> getStream() {
        return stream;
    }

    public void setStream(List<Stream> stream) {
        this.stream = stream;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getVideoProfile() {
        return videoProfile;
    }

    public void setVideoProfile(String videoProfile) {
        this.videoProfile = videoProfile;
    }

    public String getAudioProfile() {
        return audioProfile;
    }

    public void setAudioProfile(String audioProfile) {
        this.audioProfile = audioProfile;
    }
}
