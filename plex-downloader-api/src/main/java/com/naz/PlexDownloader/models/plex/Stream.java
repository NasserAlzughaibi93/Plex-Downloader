
package com.naz.PlexDownloader.models.plex;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.math.BigDecimal;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Stream {

    @Id
    @XmlAttribute(name = "id")
    private Integer id;
    @XmlAttribute(name = "streamType")
    private Integer streamType;
    @XmlAttribute(name = "default")
    private Integer _default;
    @XmlAttribute(name = "codec")
    private String codec;
    @XmlAttribute(name = "index")
    private Integer index;
    @XmlAttribute(name = "bitrate")
    private Integer bitrate;
    @XmlAttribute(name = "bitDepth")
    private Integer bitDepth;
    @XmlAttribute(name = "chromaLocation")
    private String chromaLocation;
    @XmlAttribute(name = "chromaSubsampling")
    private String chromaSubsampling;
    @XmlAttribute(name = "frameRate")
    private BigDecimal frameRate;
    @XmlAttribute(name = "hasScalingMatrix")
    private Integer hasScalingMatrix;
    @XmlAttribute(name = "height")
    private Integer height;
    @XmlAttribute(name = "level")
    private Integer level;
    @XmlAttribute(name = "profile")
    private String profile;
    @XmlAttribute(name = "refFrames")
    private Integer refFrames;
    @XmlAttribute(name = "scanType")
    private String scanType;
    @XmlAttribute(name = "width")
    private Integer width;
    @XmlAttribute(name = "displayTitle")
    private String displayTitle;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStreamType() {
        return streamType;
    }

    public void setStreamType(Integer streamType) {
        this.streamType = streamType;
    }

    public Integer get_default() {
        return _default;
    }

    public void set_default(Integer _default) {
        this._default = _default;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getBitrate() {
        return bitrate;
    }

    public void setBitrate(Integer bitrate) {
        this.bitrate = bitrate;
    }

    public Integer getBitDepth() {
        return bitDepth;
    }

    public void setBitDepth(Integer bitDepth) {
        this.bitDepth = bitDepth;
    }

    public String getChromaLocation() {
        return chromaLocation;
    }

    public void setChromaLocation(String chromaLocation) {
        this.chromaLocation = chromaLocation;
    }

    public String getChromaSubsampling() {
        return chromaSubsampling;
    }

    public void setChromaSubsampling(String chromaSubsampling) {
        this.chromaSubsampling = chromaSubsampling;
    }

    public BigDecimal getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(BigDecimal frameRate) {
        this.frameRate = frameRate;
    }

    public Integer getHasScalingMatrix() {
        return hasScalingMatrix;
    }

    public void setHasScalingMatrix(Integer hasScalingMatrix) {
        this.hasScalingMatrix = hasScalingMatrix;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Integer getRefFrames() {
        return refFrames;
    }

    public void setRefFrames(Integer refFrames) {
        this.refFrames = refFrames;
    }

    public String getScanType() {
        return scanType;
    }

    public void setScanType(String scanType) {
        this.scanType = scanType;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getDisplayTitle() {
        return displayTitle;
    }

    public void setDisplayTitle(String displayTitle) {
        this.displayTitle = displayTitle;
    }
}
