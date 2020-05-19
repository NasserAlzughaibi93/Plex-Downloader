package com.naz.PlexDownloader.models;

public enum MediaType {

    Video("Video"),
    Music("Music"),
    Series("Series");

    private String mediaType;

    MediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaKey() {
        return mediaType;
    }
}
