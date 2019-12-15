package com.naz.PlexDownloader.services.plex;

import com.naz.PlexDownloader.models.plex.Directory;
import com.naz.PlexDownloader.models.plex.DirectoryKey;
import com.naz.PlexDownloader.models.plex.MediaContainer;
import com.naz.PlexDownloader.models.plex.PlexUser;
import com.naz.PlexDownloader.models.plex.Video;

import java.util.List;

public interface PlexLibraryService {

    MediaContainer findPlexResources(PlexUser plexUser);

    List<Video> retrieveLibraryOnDeck(PlexUser plexUser, String serverIp);

    List<Video> retrieveLibraryRecentlyAdded(PlexUser plexUser, String serverIp);

    List<Directory> retrieveLibrarySections(PlexUser plexUser, String serverIp);

    List<Directory> retrieveLibrarySectionBySectionKey(PlexUser plexUser, String serverIp, String librarySectionKey);

    List<Directory> retrieveLibrarySectionBySectionKeyAndDirectoryKey(PlexUser plexUser, String serverIp, String librarySectionKey, DirectoryKey directoryKey);

    Video retrieveMediaMetadata(PlexUser plexUser, String serverIp, String libraryKey);

    String retrieveMediaDownloadLink(PlexUser plexUser, String serverIp, Video video);
}
