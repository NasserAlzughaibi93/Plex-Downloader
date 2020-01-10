package com.naz.PlexDownloader.models.plex;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean active;
    private String status;
    private String plan;

    @ElementCollection
    private List<String> features;

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean value) {
        this.active = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String value) {
        this.status = value;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String value) {
        this.plan = value;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> value) {
        this.features = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
