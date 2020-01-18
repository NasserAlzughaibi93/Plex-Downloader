package com.naz.PlexDownloader.models;

public enum RoleType {

    ADMIN("admin"),
    VIEW_ONLY("View Only"),
    UNLIMITED_DOWNLOAD("Unlimited Downloads"),
    LIMITED_DOWNLOAD("Limited Downloads");

    private String roleKey;

    RoleType(String roleKey) {
        this.roleKey = roleKey;
    }

    public String getRoleKey() {
        return roleKey;
    }

}
