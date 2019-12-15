
package com.naz.PlexDownloader.models.plex;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Media {

    @Id
    @XmlAttribute(name = "id")
    private Integer id;
    @OneToOne
    @XmlElement(name = "Part")
    private Part part;
    @XmlAttribute(name = "duration")
    private Integer duration;
    @XmlAttribute(name = "bitrate")
    private Integer bitrate;
    @XmlAttribute(name = "width")
    private Integer width;
    @XmlAttribute(name = "height")
    private Integer height;
    @XmlAttribute(name = "aspectRatio")
    private BigDecimal aspectRatio;
    @XmlAttribute(name = "audioChannels")
    private Integer audioChannels;
    @XmlAttribute(name = "audioCodec")
    private String audioCodec;
    @XmlAttribute(name = "videoCodec")
    private String videoCodec;
    @XmlAttribute(name = "videoResolution")
    private Integer videoResolution;
    @XmlAttribute(name = "container")
    private String container;
    @XmlAttribute(name = "videoFrameRate")
    private String videoFrameRate;
    @XmlAttribute(name = "videoProfile")
    private String videoProfile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getBitrate() {
        return bitrate;
    }

    public void setBitrate(Integer bitrate) {
        this.bitrate = bitrate;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public BigDecimal getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(BigDecimal aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public Integer getAudioChannels() {
        return audioChannels;
    }

    public void setAudioChannels(Integer audioChannels) {
        this.audioChannels = audioChannels;
    }

    public String getAudioCodec() {
        return audioCodec;
    }

    public void setAudioCodec(String audioCodec) {
        this.audioCodec = audioCodec;
    }

    public String getVideoCodec() {
        return videoCodec;
    }

    public void setVideoCodec(String videoCodec) {
        this.videoCodec = videoCodec;
    }

    public Integer getVideoResolution() {
        return videoResolution;
    }

    public void setVideoResolution(Integer videoResolution) {
        this.videoResolution = videoResolution;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getVideoFrameRate() {
        return videoFrameRate;
    }

    public void setVideoFrameRate(String videoFrameRate) {
        this.videoFrameRate = videoFrameRate;
    }

    public String getVideoProfile() {
        return videoProfile;
    }

    public void setVideoProfile(String videoProfile) {
        this.videoProfile = videoProfile;
    }
}
