
package com.naz.PlexDownloader.models.plex;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;
import java.util.List;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @XmlElement(name = "Media")
    private Media media;
    @OneToMany
    @XmlElement(name = "Writer")
    private List<Writer> writer;
    @OneToOne
    @XmlElement(name = "Director")
    private Director director;
    @OneToMany
    @XmlElement(name = "Role")
    private List<RoleType> role;
    @OneToMany
    @XmlElement(name = "Country")
    private List<Country> country;
    @XmlAttribute(name = "allowSync")
    private Integer allowSync;
    @XmlAttribute(name = "librarySectionID")
    private Integer librarySectionID;
    @XmlAttribute(name = "librarySectionTitle")
    private String librarySectionTitle;
    @XmlAttribute(name = "librarySectionUUID")
    private String librarySectionUUID;
    @XmlAttribute(name = "ratingKey")
    private Integer ratingKey;
    @XmlAttribute(name = "key")
    private String key;
    @XmlAttribute(name = "parentRatingKey")
    private Integer parentRatingKey;
    @XmlAttribute(name = "grandparentRatingKey")
    private Integer grandparentRatingKey;
    @XmlAttribute(name = "guid")
    private String guid;
    @XmlAttribute(name = "parentGuid")
    private String parentGuid;
    @XmlAttribute(name = "grandparentGuid")
    private String grandparentGuid;
    @XmlAttribute(name = "type")
    private String type;
    @XmlAttribute(name = "title")
    private String title;
    @XmlAttribute(name = "grandparentKey")
    private String grandparentKey;
    @XmlAttribute(name = "parentKey")
    private String parentKey;
    @XmlAttribute(name = "librarySectionKey")
    private String librarySectionKey;
    @XmlAttribute(name = "grandparentTitle")
    private String grandparentTitle;
    @XmlAttribute(name = "parentTitle")
    private String parentTitle;
    @XmlAttribute(name = "contentRating")
    private String contentRating;
    @XmlAttribute(name = "summary")
    private String summary;
    @XmlAttribute(name = "index")
    private Integer index;
    @XmlAttribute(name = "parentIndex")
    private Integer parentIndex;
    @XmlAttribute(name = "rating")
    private BigDecimal rating;
    @XmlAttribute(name = "year")
    private Integer year;
    @XmlAttribute(name = "thumb")
    private String thumb;
    @XmlAttribute(name = "art")
    private String art;
    @XmlAttribute(name = "parentThumb")
    private String parentThumb;
    @XmlAttribute(name = "grandparentThumb")
    private String grandparentThumb;
    @XmlAttribute(name = "grandparentArt")
    private String grandparentArt;
    @XmlAttribute(name = "duration")
    private Integer duration;
    @XmlAttribute(name = "originallyAvailableAt")
    private String originallyAvailableAt;
    @XmlAttribute(name = "addedAt")
    private Integer addedAt;
    @XmlAttribute(name = "updatedAt")
    private Integer updatedAt;
    @XmlElement(name = "Genre")
    @OneToMany
    private List<Genre> genres;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public List<Writer> getWriter() {
        return writer;
    }

    public void setWriter(List<Writer> writer) {
        this.writer = writer;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public List<RoleType> getRole() {
        return role;
    }

    public void setRole(List<RoleType> role) {
        this.role = role;
    }

    public List<Country> getCountry() {
        return country;
    }

    public void setCountry(List<Country> country) {
        this.country = country;
    }

    public Integer getAllowSync() {
        return allowSync;
    }

    public void setAllowSync(Integer allowSync) {
        this.allowSync = allowSync;
    }

    public Integer getLibrarySectionID() {
        return librarySectionID;
    }

    public void setLibrarySectionID(Integer librarySectionID) {
        this.librarySectionID = librarySectionID;
    }

    public String getLibrarySectionTitle() {
        return librarySectionTitle;
    }

    public void setLibrarySectionTitle(String librarySectionTitle) {
        this.librarySectionTitle = librarySectionTitle;
    }

    public String getLibrarySectionUUID() {
        return librarySectionUUID;
    }

    public void setLibrarySectionUUID(String librarySectionUUID) {
        this.librarySectionUUID = librarySectionUUID;
    }

    public Integer getRatingKey() {
        return ratingKey;
    }

    public void setRatingKey(Integer ratingKey) {
        this.ratingKey = ratingKey;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getParentRatingKey() {
        return parentRatingKey;
    }

    public void setParentRatingKey(Integer parentRatingKey) {
        this.parentRatingKey = parentRatingKey;
    }

    public Integer getGrandparentRatingKey() {
        return grandparentRatingKey;
    }

    public void setGrandparentRatingKey(Integer grandparentRatingKey) {
        this.grandparentRatingKey = grandparentRatingKey;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getParentGuid() {
        return parentGuid;
    }

    public void setParentGuid(String parentGuid) {
        this.parentGuid = parentGuid;
    }

    public String getGrandparentGuid() {
        return grandparentGuid;
    }

    public void setGrandparentGuid(String grandparentGuid) {
        this.grandparentGuid = grandparentGuid;
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

    public String getGrandparentKey() {
        return grandparentKey;
    }

    public void setGrandparentKey(String grandparentKey) {
        this.grandparentKey = grandparentKey;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getLibrarySectionKey() {
        return librarySectionKey;
    }

    public void setLibrarySectionKey(String librarySectionKey) {
        this.librarySectionKey = librarySectionKey;
    }

    public String getGrandparentTitle() {
        return grandparentTitle;
    }

    public void setGrandparentTitle(String grandparentTitle) {
        this.grandparentTitle = grandparentTitle;
    }

    public String getParentTitle() {
        return parentTitle;
    }

    public void setParentTitle(String parentTitle) {
        this.parentTitle = parentTitle;
    }

    public String getContentRating() {
        return contentRating;
    }

    public void setContentRating(String contentRating) {
        this.contentRating = contentRating;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getParentIndex() {
        return parentIndex;
    }

    public void setParentIndex(Integer parentIndex) {
        this.parentIndex = parentIndex;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public String getParentThumb() {
        return parentThumb;
    }

    public void setParentThumb(String parentThumb) {
        this.parentThumb = parentThumb;
    }

    public String getGrandparentThumb() {
        return grandparentThumb;
    }

    public void setGrandparentThumb(String grandparentThumb) {
        this.grandparentThumb = grandparentThumb;
    }

    public String getGrandparentArt() {
        return grandparentArt;
    }

    public void setGrandparentArt(String grandparentArt) {
        this.grandparentArt = grandparentArt;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getOriginallyAvailableAt() {
        return originallyAvailableAt;
    }

    public void setOriginallyAvailableAt(String originallyAvailableAt) {
        this.originallyAvailableAt = originallyAvailableAt;
    }

    public Integer getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Integer addedAt) {
        this.addedAt = addedAt;
    }

    public Integer getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Integer updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
