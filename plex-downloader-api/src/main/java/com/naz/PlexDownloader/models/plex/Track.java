package com.naz.PlexDownloader.models.plex;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Track
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trackId;

    @XmlAttribute
    private String grandparentTitle;

    @XmlAttribute
    private String parentIndex;

    @XmlAttribute
    private String grandparentThumb;

    @XmlAttribute
    private String addedAt;

    @XmlAttribute
    private String thumb;

    @XmlAttribute
    private String parentRatingKey;

    @XmlAttribute
    private String grandparentKey;

    @XmlAttribute
    private String ratingKey;

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String title;

    @XmlAttribute
    private String duration;

    @XmlAttribute
    private String grandparentRatingKey;

    @XmlAttribute
    private String key;

    @XmlAttribute
    private String grandparentGuid;

    @XmlAttribute
    private String updatedAt;

    @XmlAttribute
    private String summary;

    @XmlAttribute
    private String art;

    @XmlElement(name = "Media")
    @OneToOne
    private Media Media;

    @XmlAttribute
    private String index;

    @XmlAttribute
    private String parentTitle;

    @XmlAttribute
    private String parentThumb;

    @XmlAttribute
    private String grandparentArt;

    @XmlAttribute
    private String parentKey;

    @XmlAttribute
    private String originalTitle;

    @XmlAttribute
    private String parentGuid;

    @XmlAttribute
    private String guid;

    public Long getTrackId() {
        return trackId;
    }

    public void setTrackId(Long trackId) {
        this.trackId = trackId;
    }

    public String getGrandparentTitle() {
        return grandparentTitle;
    }

    public void setGrandparentTitle(String grandparentTitle) {
        this.grandparentTitle = grandparentTitle;
    }

    public String getParentIndex() {
        return parentIndex;
    }

    public void setParentIndex(String parentIndex) {
        this.parentIndex = parentIndex;
    }

    public String getGrandparentThumb() {
        return grandparentThumb;
    }

    public void setGrandparentThumb(String grandparentThumb) {
        this.grandparentThumb = grandparentThumb;
    }

    public String getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(String addedAt) {
        this.addedAt = addedAt;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getParentRatingKey() {
        return parentRatingKey;
    }

    public void setParentRatingKey(String parentRatingKey) {
        this.parentRatingKey = parentRatingKey;
    }

    public String getGrandparentKey() {
        return grandparentKey;
    }

    public void setGrandparentKey(String grandparentKey) {
        this.grandparentKey = grandparentKey;
    }

    public String getRatingKey() {
        return ratingKey;
    }

    public void setRatingKey(String ratingKey) {
        this.ratingKey = ratingKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getGrandparentRatingKey() {
        return grandparentRatingKey;
    }

    public void setGrandparentRatingKey(String grandparentRatingKey) {
        this.grandparentRatingKey = grandparentRatingKey;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getGrandparentGuid() {
        return grandparentGuid;
    }

    public void setGrandparentGuid(String grandparentGuid) {
        this.grandparentGuid = grandparentGuid;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public com.naz.PlexDownloader.models.plex.Media getMedia() {
        return Media;
    }

    public void setMedia(com.naz.PlexDownloader.models.plex.Media media) {
        Media = media;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getParentTitle() {
        return parentTitle;
    }

    public void setParentTitle(String parentTitle) {
        this.parentTitle = parentTitle;
    }

    public String getParentThumb() {
        return parentThumb;
    }

    public void setParentThumb(String parentThumb) {
        this.parentThumb = parentThumb;
    }

    public String getGrandparentArt() {
        return grandparentArt;
    }

    public void setGrandparentArt(String grandparentArt) {
        this.grandparentArt = grandparentArt;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getParentGuid() {
        return parentGuid;
    }

    public void setParentGuid(String parentGuid) {
        this.parentGuid = parentGuid;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
