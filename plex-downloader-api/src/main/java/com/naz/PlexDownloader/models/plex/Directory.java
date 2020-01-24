package com.naz.PlexDownloader.models.plex;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Directory
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long directoryId;

    @XmlAttribute
    private String art;

    @XmlAttribute
    private String agent;

    @XmlAttribute
    private String scannedAt;

    @XmlAttribute
    private String thumb;

    @XmlAttribute
    private String contentChangedAt;

    @XmlAttribute
    private String allowSync;

    @XmlAttribute
    private String language;

    @XmlAttribute
    private String filters;

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String title;

    @XmlAttribute
    private String uuid;

    @XmlAttribute
    private String directory;

    @XmlAttribute
    private String content;

    @XmlAttribute
    private String createdAt;

    @XmlAttribute
    private String composite;

    @XmlAttribute
    private String refreshing;

    @XmlAttribute
    private String scanner;

    @XmlAttribute
    private String secondary;

    @XmlAttribute
    private String prompt;

    @XmlAttribute
    private String summary;

    @XmlAttribute
    private String banner;

    @XmlElement(name = "Genre")
    @Transient
    private List<Genre> genre;

    @XmlElement(name = "Role")
    @Transient
    private List<RoleType> role;

    @XmlAttribute
    private String childCount;

    @XmlAttribute
    private String rating;

    @XmlAttribute
    private String year;

    @XmlAttribute
    private String search;

    @XmlAttribute
    private String key;

    @XmlAttribute
    private String updatedAt;

    @XmlElement
    @OneToOne
    private Location Location;

    public String getArt ()
    {
        return art;
    }

    public void setArt (String art)
    {
        this.art = art;
    }

    public String getAgent ()
    {
        return agent;
    }

    public void setAgent (String agent)
    {
        this.agent = agent;
    }

    public String getScannedAt ()
    {
        return scannedAt;
    }

    public void setScannedAt (String scannedAt)
    {
        this.scannedAt = scannedAt;
    }

    public String getThumb ()
    {
        return thumb;
    }

    public void setThumb (String thumb)
    {
        this.thumb = thumb;
    }

    public String getContentChangedAt ()
    {
        return contentChangedAt;
    }

    public void setContentChangedAt (String contentChangedAt)
    {
        this.contentChangedAt = contentChangedAt;
    }

    public String getAllowSync ()
    {
        return allowSync;
    }

    public void setAllowSync (String allowSync)
    {
        this.allowSync = allowSync;
    }

    public String getLanguage ()
    {
        return language;
    }

    public void setLanguage (String language)
    {
        this.language = language;
    }

    public String getFilters ()
    {
        return filters;
    }

    public void setFilters (String filters)
    {
        this.filters = filters;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getUuid ()
    {
        return uuid;
    }

    public void setUuid (String uuid)
    {
        this.uuid = uuid;
    }

    public String getDirectory ()
    {
        return directory;
    }

    public void setDirectory (String directory)
    {
        this.directory = directory;
    }

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    public String getCreatedAt ()
    {
        return createdAt;
    }

    public void setCreatedAt (String createdAt)
    {
        this.createdAt = createdAt;
    }

    public String getComposite ()
    {
        return composite;
    }

    public void setComposite (String composite)
    {
        this.composite = composite;
    }

    public String getRefreshing ()
    {
        return refreshing;
    }

    public void setRefreshing (String refreshing)
    {
        this.refreshing = refreshing;
    }

    public String getScanner ()
    {
        return scanner;
    }

    public void setScanner (String scanner)
    {
        this.scanner = scanner;
    }

    public String getKey ()
    {
        return key;
    }

    public void setKey (String key)
    {
        this.key = key;
    }

    public String getUpdatedAt ()
    {
        return updatedAt;
    }

    public void setUpdatedAt (String updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    public Location getLocation ()
    {
        return Location;
    }

    public void setLocation (Location Location)
    {
        this.Location = Location;
    }

    public Long getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(Long directoryId) {
        this.directoryId = directoryId;
    }

    public String getSecondary() {
        return secondary;
    }

    public void setSecondary(String secondary) {
        this.secondary = secondary;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public List<Genre> getGenre() {
        return genre;
    }

    public void setGenre(List<Genre> genre) {
        this.genre = genre;
    }

    public List<RoleType> getRole() {
        return role;
    }

    public void setRole(List<RoleType> role) {
        this.role = role;
    }

    public String getChildCount() {
        return childCount;
    }

    public void setChildCount(String childCount) {
        this.childCount = childCount;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [art = "+art+", agent = "+agent+", scannedAt = "+scannedAt+", thumb = "+thumb+", contentChangedAt = "+contentChangedAt+", allowSync = "+allowSync+", language = "+language+", filters = "+filters+", type = "+type+", title = "+title+", uuid = "+uuid+", directory = "+directory+", content = "+content+", createdAt = "+createdAt+", composite = "+composite+", refreshing = "+refreshing+", scanner = "+scanner+", key = "+key+", updatedAt = "+updatedAt+", Location = "+Location+"]";
    }
}
