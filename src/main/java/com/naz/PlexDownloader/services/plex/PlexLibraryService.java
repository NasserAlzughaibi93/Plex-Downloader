package com.naz.PlexDownloader.services.plex;

import com.naz.PlexDownloader.models.plex.MediaContainer;
import com.naz.PlexDownloader.models.plex.PlexUser;

public interface PlexLibraryService {

    MediaContainer findPlexResources(PlexUser plexUser);

    MediaContainer retrieveLibraryOnDeck(PlexUser plexUser, String serverIp);

    MediaContainer retrieveLibraryRecentlyAdded(PlexUser plexUser, String serverIp);

    MediaContainer retrieveLibrarySections(PlexUser plexUser, String serverIp);

    MediaContainer retrieveMediaMetadata(PlexUser plexUser, String serverIp, String libraryKey);

    String retrieveMediaDownloadLink(PlexUser plexUser, String serverIp, MediaContainer mediaContainer);
}
