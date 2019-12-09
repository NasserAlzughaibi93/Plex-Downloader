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
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlexUser {

    @Id
    private long id;

    private String uuid;
    private String email;
    @JsonProperty("joined_at")
    private String joinedAt;
    private String username;
    private String title;
    private String thumb;
    private boolean hasPassword;
    private String authToken;
    @JsonProperty("authentication_token")
    private String authenticationToken;

    @ElementCollection
    private List<String> entitlements;
    private String confirmedAt;
    @Transient
    private transient Object forumID;
    private boolean rememberMe;

    @OneToOne(cascade = CascadeType.ALL)
    private Subscription subscription;
    @OneToOne(cascade = CascadeType.ALL)
    private Roles roles;


    public long getID() {
        return id;
    }

    public void setID(long value) {
        this.id = value;
    }

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String value) {
        this.uuid = value;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String value) {
        this.email = value;
    }

    public String getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(String value) {
        this.joinedAt = value;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String value) {
        this.username = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String value) {
        this.thumb = value;
    }

    public boolean getHasPassword() {
        return hasPassword;
    }

    public void setHasPassword(boolean value) {
        this.hasPassword = value;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String value) {
        this.authToken = value;
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String value) {
        this.authenticationToken = value;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription value) {
        this.subscription = value;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles value) {
        this.roles = value;
    }

    public List<String> getEntitlements() {
        return entitlements;
    }

    public void setEntitlements(List<String> value) {
        this.entitlements = value;
    }

    public String getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(String value) {
        this.confirmedAt = value;
    }

    public Object getForumID() {
        return forumID;
    }

    public void setForumID(Object value) {
        this.forumID = value;
    }

    public boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean value) {
        this.rememberMe = value;
    }
}
