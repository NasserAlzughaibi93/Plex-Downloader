package com.naz.PlexDownloader.models.plex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlAccessorType(XmlAccessType.FIELD)
public class PlexUser {

    @Id
    private long id;

    @XmlAttribute
    private String uuid;
    @XmlAttribute
    private String email;
    @JsonProperty("joined_at")
    @XmlAttribute
    private String joinedAt;
    @XmlAttribute
    private String username;
    @XmlAttribute
    private String title;
    @XmlAttribute
    private String thumb;
    @XmlAttribute
    private boolean hasPassword;
    @XmlAttribute
    private String authToken;
    @JsonProperty("authentication_token")
    @XmlAttribute
    private String authenticationToken;
    @XmlAttribute
    private String allowChannels;

    @XmlAttribute
    private String allowSubtitleAdmin;

    @Transient
//    @XmlAttribute(name = "Server")
    private Server Server;

    @XmlAttribute
    private String allowTuners;

    @XmlAttribute
    private String allowSync;

    @XmlAttribute
    private String filterTelevision;

    @XmlAttribute
    private String home;

    @XmlAttribute
    private String allowCameraUpload;

    @XmlAttribute
    private String filterMovies;

    @XmlAttribute
    private String isProtected;

    @XmlAttribute
    private String restricted;

    @XmlAttribute
    private String filterPhotos;

    @XmlAttribute
    private String recommendationsPlaylistId;

    @XmlAttribute
    private String filterMusic;

    @XmlAttribute
    private String filterAll;

    @ElementCollection
    @XmlAttribute(name = "Entitlements")
    private List<String> entitlements;
    @XmlAttribute
    private String confirmedAt;
    @Transient
    private transient Object forumID;
    @XmlAttribute
    private boolean rememberMe;

    @OneToOne(cascade = CascadeType.ALL)
    private Subscription subscription;
    @OneToOne(cascade = CascadeType.ALL)
    private Roles roles;

    private transient String jwtToken;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(String joinedAt) {
        this.joinedAt = joinedAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public boolean isHasPassword() {
        return hasPassword;
    }

    public void setHasPassword(boolean hasPassword) {
        this.hasPassword = hasPassword;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    public String getAllowChannels() {
        return allowChannels;
    }

    public void setAllowChannels(String allowChannels) {
        this.allowChannels = allowChannels;
    }

    public String getAllowSubtitleAdmin() {
        return allowSubtitleAdmin;
    }

    public void setAllowSubtitleAdmin(String allowSubtitleAdmin) {
        this.allowSubtitleAdmin = allowSubtitleAdmin;
    }

    public com.naz.PlexDownloader.models.plex.Server getServer() {
        return Server;
    }

    public void setServer(com.naz.PlexDownloader.models.plex.Server server) {
        Server = server;
    }

    public String getAllowTuners() {
        return allowTuners;
    }

    public void setAllowTuners(String allowTuners) {
        this.allowTuners = allowTuners;
    }

    public String getAllowSync() {
        return allowSync;
    }

    public void setAllowSync(String allowSync) {
        this.allowSync = allowSync;
    }

    public String getFilterTelevision() {
        return filterTelevision;
    }

    public void setFilterTelevision(String filterTelevision) {
        this.filterTelevision = filterTelevision;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAllowCameraUpload() {
        return allowCameraUpload;
    }

    public void setAllowCameraUpload(String allowCameraUpload) {
        this.allowCameraUpload = allowCameraUpload;
    }

    public String getFilterMovies() {
        return filterMovies;
    }

    public void setFilterMovies(String filterMovies) {
        this.filterMovies = filterMovies;
    }

    public String getIsProtected() {
        return isProtected;
    }

    public void setIsProtected(String isProtected) {
        this.isProtected = isProtected;
    }

    public String getRestricted() {
        return restricted;
    }

    public void setRestricted(String restricted) {
        this.restricted = restricted;
    }

    public String getFilterPhotos() {
        return filterPhotos;
    }

    public void setFilterPhotos(String filterPhotos) {
        this.filterPhotos = filterPhotos;
    }

    public String getRecommendationsPlaylistId() {
        return recommendationsPlaylistId;
    }

    public void setRecommendationsPlaylistId(String recommendationsPlaylistId) {
        this.recommendationsPlaylistId = recommendationsPlaylistId;
    }

    public String getFilterMusic() {
        return filterMusic;
    }

    public void setFilterMusic(String filterMusic) {
        this.filterMusic = filterMusic;
    }

    public String getFilterAll() {
        return filterAll;
    }

    public void setFilterAll(String filterAll) {
        this.filterAll = filterAll;
    }

    public List<String> getEntitlements() {
        return entitlements;
    }

    public void setEntitlements(List<String> entitlements) {
        this.entitlements = entitlements;
    }

    public String getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(String confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public Object getForumID() {
        return forumID;
    }

    public void setForumID(Object forumID) {
        this.forumID = forumID;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
