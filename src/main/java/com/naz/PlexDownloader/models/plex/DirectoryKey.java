package com.naz.PlexDownloader.models.plex;

public enum DirectoryKey {

    ALL("all"),
    UNWATCHED("unwatched"),
    NEWEST("newest"),
    RECENTLY_ADDED("recentlyAdded"),
    RECENTLY_VIEWED("recentlyViewed"),
    ON_DECK("onDeck"),
    COLLECTION("collection"),
    GENRE("genre"),
    YEAR("year"),
    DECADE("decade"),
    DIRECTOR("director"),
    ACTOR("actor"),
    COUNTRY("country"),
    CONTENT_RATING("contentRating"),
    RATING("rating"),
    RESOLUTION("resolution"),
    FIRST_CHARACTER("firstCharacter");

    private String directoryKey;

    DirectoryKey(String directoryKey) {
        this.directoryKey = directoryKey;
    }

    public String getDirectoryKey() {
        return directoryKey;
    }

}
