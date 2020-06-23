package com.naz.PlexDownloader.controllers;

import com.naz.PlexDownloader.BaseUnitTest;
import com.naz.PlexDownloader.models.DownloadRequest;
import com.naz.PlexDownloader.models.MediaType;
import com.naz.PlexDownloader.models.plex.Device;
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
    private Device device;

    @BeforeEach
    protected void setUp() {
        this.video = new Video();
        this.video.setId(1L);

        this.directory = new Directory();
        this.directory.setDirectoryId(1L);

        this.device = new Device();
        this.device.setDeviceId(1L);

        super.setUp();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findPlexResources() {

        List<Device> devices = CollectionUtil.createList(this.device);


        when(plexLibraryService.findPlexResources(anyString())).thenReturn(devices);

        List<Device> result = plexLibraryController.findPlexResources("token");

        assertEquals(devices, result);

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


//        List<Video> videos = CollectionUtil.createList(this.video);

        MediaContainer mediaContainer = new MediaContainer();
        mediaContainer.setVideo(CollectionUtil.createList(this.video));

        when(plexLibraryService
                .retrieveLibrarySectionBySectionKeyAndDirectoryKey(anyString(), anyString(), anyString(), anyString())
        ).thenReturn(mediaContainer);

        List<Video> results =
                plexLibraryController.retrieveLibrarySectionBySectionKeyAndDirectoryKey("serverIP",
                        "authToken", "sectionKey", "directoryKey").getVideo();

        assertEquals(mediaContainer.getVideo(), results);
    }

    @Test
    void retrieveMediaMetadata() {

        MediaContainer mediaContainer = new MediaContainer();
        mediaContainer.setVideo(CollectionUtil.createList(this.video));

        when(plexLibraryService.retrieveMediaMetadata(anyString(), anyString(), anyString())).thenReturn(mediaContainer);

        List<Video> result =
                plexLibraryController.retrieveMediaMetadata("serverIp", "authToken", "libraryKey").getVideo();

        assertEquals(mediaContainer.getVideo().size(), result.size());
    }

    @Test
    void retrieveMediaDownloadLink() {

        String downloadURL = "DownloadLink";

        when(plexLibraryService.retrieveMediaDownloadLink(anyString(), anyString(), any(DownloadRequest.class))).thenReturn(downloadURL);

        DownloadRequest downloadRequest = new DownloadRequest();
        downloadRequest.setKey("key");
        downloadRequest.setMediaType(MediaType.Video);

        String result = plexLibraryController.retrieveMediaDownloadLink("serverIP", "authToken", downloadRequest);

        assertEquals(downloadURL, result);
    }
}
