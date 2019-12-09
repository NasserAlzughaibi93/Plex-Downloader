package com.naz.PlexDownloader.models.plex;

import java.util.List;

public class UserEntity {

    /*private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }*/
    private List<PlexUser> user;

    public List<PlexUser> getUser() {
        return user;
    }

    public void setUser(List<PlexUser> user) {
        this.user = user;
    }
}
