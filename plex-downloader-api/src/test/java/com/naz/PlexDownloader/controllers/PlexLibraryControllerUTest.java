package com.naz.PlexDownloader.controllers;

import com.naz.PlexDownloader.BaseUnitTest;
import com.naz.PlexDownloader.models.plex.Directory;
import com.naz.PlexDownloader.models.plex.Media;
import com.naz.PlexDownloader.models.plex.MediaContainer;
import com.naz.PlexDownloader.models.plex.Part;
import com.naz.PlexDownloader.models.plex.Video;
import com.naz.PlexDownloader.services.plex.PlexLibraryService;
import com.naz.PlexDownloader.util.CollectionUtil;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class PlexLibraryControllerUTest extends BaseUnitTest {

    @InjectMocks
    private PlexLibraryController plexLibraryController;

    @Mock
    private PlexLibraryService plexLibraryService;

    private Video video;
    private Directory directory;

    @BeforeEach
    protected void setUp() {
        this.video = new Video();
        this.video.setId(1L);

        directory = new Directory();
        directory.setDirectoryId(1L);

        super.setUp();

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findPlexResources() {

        MediaContainer mediaContainer = new MediaContainer();
        mediaContainer.setIdentifier("1");

        when(plexLibraryService.findPlexResources(anyString())).thenReturn(mediaContainer);

        MediaContainer result = plexLibraryController.findPlexResources("token");

        assertEquals(mediaContainer, result);

    }

    @Test
    void retrieveLibraryOnDeck() {

        List<Video> videos = CollectionUtil.createList(this.video);

        when(plexLibraryService.retrieveLibraryOnDeck(anyString(), anyString())).thenReturn(videos);

        List<Video> results = plexLibraryController.retrieveLibraryOnDeck("serverIP", "authToken");

        assertEquals(videos, results);
    }

    @Test
    void retrieveLibraryRecentlyAdded() {

        List<Video> videos = CollectionUtil.createList(this.video);

        when(plexLibraryService.retrieveLibraryRecentlyAdded(anyString(), anyString())).thenReturn(videos);

        List<Video> results = plexLibraryController.retrieveLibraryRecentlyAdded("serverIP", "authToken");

        assertEquals(videos, results);
    }

    @Test
    void retrieveLibrarySections() {

        List<Directory> directories = CollectionUtil.createList(directory);

        when(plexLibraryService.retrieveLibrarySections(anyString(), anyString())).thenReturn(directories);

        List<Directory> results = plexLibraryController.retrieveLibrarySections("serverIP", "authToken");

        assertEquals(directories, results);
    }

    @Test
    void retrieveLibrarySectionBySectionKey() {

        List<Directory> directories = CollectionUtil.createList(directory);

        when(plexLibraryService
                .retrieveLibrarySectionBySectionKey(anyString(), anyString(), anyString())
        ).thenReturn(directories);

        List<Directory> results = plexLibraryController.retrieveLibrarySectionBySectionKey("serverIP", "authToken", "sectionKey");

        assertEquals(directories, results);
    }

    @Test
    void retrieveLibrarySectionBySectionKeyAndDirectoryKey() {


        List<Video> videos = CollectionUtil.createList(this.video);

        when(plexLibraryService
                .retrieveLibrarySectionBySectionKeyAndDirectoryKey(anyString(), anyString(), anyString(), anyString())
        ).thenReturn(videos);

        List<Video> results =
                plexLibraryController.retrieveLibrarySectionBySectionKeyAndDirectoryKey("serverIP", "authToken", "sectionKey", "directoryKey");

        assertEquals(videos, results);
    }

    @Test
    void retrieveMediaMetadata() {

        when(plexLibraryService.retrieveMediaMetadata(anyString(), anyString(), anyString())).thenReturn(this.video);

        Video result =
                plexLibraryController.retrieveMediaMetadata("serverIp", "libraryKey", "authToken");

        assertEquals(this.video, result);
    }

    @Test
    void retrieveMediaDownloadLink() {

        String downloadURL = "DownloadLink";

        when(plexLibraryService.retrieveMediaDownloadLink(anyString(), anyString(), any(Video.class))).thenReturn(downloadURL);

        String result = plexLibraryController.retrieveMediaDownloadLink("serverIP", "authToken", this.video);

        assertEquals(downloadURL, result);
    }
}