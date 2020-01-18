package com.naz.PlexDownloader.services.plex;

import com.naz.PlexDownloader.models.plex.Device;
import com.naz.PlexDownloader.models.plex.Directory;
import com.naz.PlexDownloader.models.plex.MediaContainer;
import com.naz.PlexDownloader.models.plex.Part;
import com.naz.PlexDownloader.models.plex.Video;
import com.naz.PlexDownloader.util.CollectionUtil;
import com.naz.PlexDownloader.util.PlexRestTemplate;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlexLibraryServiceImpl implements PlexLibraryService {

    private static final String PLEX_BASE_URL = "https://plex.tv/api";

    private static final String PLEX_RESOURCES_URL = PLEX_BASE_URL + "/resources/";

    private static final String PLEX_LIBRARY = "/library";

    private static final String PLEX_ONDECK = PLEX_LIBRARY + "/onDeck";

    private static final String PLEX_SECTIONS = PLEX_LIBRARY + "/sections";

    private static final String PLEX_RECENTLY_ADDED = PLEX_LIBRARY + "/recentlyAdded";

    private static final String PLEX_SEARCH = "/search?query=";

    /**
     * Find available resources of a given server instance.
     *
     * @param plexAuthToken - The plex auth token.
     * @return {@link MediaContainer}
     */
    @Override
    public MediaContainer findPlexResources(String plexAuthToken) {

        MediaContainer mediaContainer = this.buildPlexRestCall(plexAuthToken, PLEX_RESOURCES_URL, false);

        if (null == mediaContainer) {
            throw new NotYetImplementedException();
        }

        if (!CollectionUtil.isNullOrEmpty(mediaContainer.getDevice())) {
            List<Device> serverDevices = new ArrayList<>();

            for (Device device : mediaContainer.getDevice()) {
                if (device.getProvides().contains("server")) {
                    serverDevices.add(device);
                }
            }

            mediaContainer.setDevice(serverDevices);
        }

        return mediaContainer;
    }

    /**
     * Retrieves the On Deck Section of a given server instance.
     *
     * @param plexAuthToken - The plex auth token.
     * @param serverIp      - The plex instance server IP.
     * @return List of videos
     */
    @Override
    public List<Video> retrieveLibraryOnDeck(String plexAuthToken, String serverIp) {

        String url = serverIp + PLEX_ONDECK;

        MediaContainer mediaContainer = this.buildPlexRestCall(plexAuthToken, url, false);

        if (null == mediaContainer || CollectionUtil.isNullOrEmpty(mediaContainer.getVideo())) {
            throw new NotYetImplementedException();
        }

        return mediaContainer.getVideo();
    }

    /**
     * Retrieves the recently added section of a given server instance
     *
     * @param plexAuthToken - The plex auth token.
     * @param serverIp      - The plex instance server IP.
     * @return - Media part of the section.
     */
    @Override
    public List<Video> retrieveLibraryRecentlyAdded(String plexAuthToken, String serverIp) {

        String url = serverIp + PLEX_RECENTLY_ADDED;

        MediaContainer mediaContainer = this.buildPlexRestCall(plexAuthToken, url, false);

        if (null == mediaContainer || CollectionUtil.isNullOrEmpty(mediaContainer.getVideo())) {
            throw new NotYetImplementedException();
        }

        return mediaContainer.getVideo();
    }

    /**
     * Retrieve the library sections of a given server instance.
     *
     * @param plexAuthToken - The plex auth token.
     * @param serverIp      - The plex instance server IP.
     * @return - List of media directories
     */
    @Override
    public List<Directory> retrieveLibrarySections(String plexAuthToken, String serverIp) {

        String url = serverIp + PLEX_SECTIONS;

        MediaContainer mediaContainer = this.buildPlexRestCall(plexAuthToken, url, false);

        if (null == mediaContainer || CollectionUtil.isNullOrEmpty(mediaContainer.getDirectory())) {
            throw new NotYetImplementedException();
        }

        return mediaContainer.getDirectory();
    }

    /**
     * Retrieves the media metadata.
     *
     * @param plexAuthToken - The plex auth token.
     * @param serverIp      - The plex instance server IP.
     * @param libraryKey    - The medias metadata url path.
     * @return - The {@link MediaContainer}
     */
    @Override
    public Video retrieveMediaMetadata(String plexAuthToken, String serverIp, String libraryKey) {


        String url = serverIp + libraryKey;

        MediaContainer mediaContainer = this.buildPlexRestCall(plexAuthToken, url, false);

        if (null == mediaContainer || CollectionUtil.isNullOrEmpty(mediaContainer.getVideo())) {
            throw new NotYetImplementedException();
        }

        return CollectionUtil.getFirstElement(mediaContainer.getVideo());
    }

    /**
     * Generate the download link for the given media.
     *
     * @param plexAuthToken - The plex auth token.
     * @param serverIp      - The plex instance server IP.
     * @param video         - The media
     * @return - The download link
     */
    @Override
    public String retrieveMediaDownloadLink(String plexAuthToken, String serverIp, Video video) {

        String downloadUrl;

        if (null == video) {
            throw new NotYetImplementedException();
        }

        if (null == video.getMedia() || null == video.getMedia().getPart()) {
            video = this.retrieveMediaMetadata(plexAuthToken, serverIp, video.getKey());
        }

        Part part = video.getMedia().getPart();

        if (null == video.getMedia() || null == video.getMedia().getPart()) {
            throw new NotYetImplementedException();
        }

        downloadUrl = serverIp + part.getKey() + "?download=1&X-Plex-Token=" + plexAuthToken;

        return downloadUrl;
    }

    @Override
    public MediaContainer retrieveSearchResults(String serverIp, String searchQuery, String authToken) {

        String url = serverIp + PLEX_SEARCH + searchQuery;

        MediaContainer mediaContainer = this.buildPlexRestCall(authToken, url, false);

        if (null == mediaContainer ||
                (CollectionUtil.isNullOrEmpty(mediaContainer.getVideo()) &&
                        CollectionUtil.isNullOrEmpty(mediaContainer.getDirectory()))) {
            throw new NotYetImplementedException();
        }


        return mediaContainer;
    }

    /**
     * @param plexAuthToken     - The plex auth token.
     * @param serverIp          - The plex instance server IP.
     * @param librarySectionKey - Specific section key. E.g. {server_ip}/library/sections/5
     * @return List of media directories.
     */
    @Override
    public List<Directory> retrieveLibrarySectionBySectionKey(String plexAuthToken, String serverIp, String librarySectionKey) {

        String url = serverIp + PLEX_SECTIONS + "/" + librarySectionKey;

        MediaContainer mediaContainer = this.buildPlexRestCall(plexAuthToken, url, false);

        if (null == mediaContainer || CollectionUtil.isNullOrEmpty(mediaContainer.getDirectory())) {
            throw new NotYetImplementedException();
        }

        return mediaContainer.getDirectory();
    }

    //TODO check if this is even logical once front end is setup.
    @Override
    public List<Video> retrieveLibrarySectionBySectionKeyAndDirectoryKey(String plexAuthToken, String serverIp, String librarySectionKey, String directoryKey) {
        String url = serverIp + PLEX_SECTIONS + "/" + librarySectionKey + "/" + directoryKey;

        MediaContainer mediaContainer = this.buildPlexRestCall(plexAuthToken, url, false);

        if (null == mediaContainer || CollectionUtil.isNullOrEmpty(mediaContainer.getVideo())) {
            //throw new NotYetImplementedException();
        }

        return mediaContainer.getVideo();
    }

    /**
     * Common rest call for plex MediaContainer apis
     *
     * @param plexAuthToken - The plex auth token.
     * @param url           - The plex instance server IP and api url.
     * @param isPostCall    - Whether to use a POST or GET.
     * @return {@link MediaContainer}
     */
    private MediaContainer buildPlexRestCall(String plexAuthToken, String url, boolean isPostCall) {

        if (!url.contains("http://") && !url.contains("https://")) {
            url = "http://" + url;
        }

        return (MediaContainer) PlexRestTemplate.buildPlexRestTemplateForXMLResponse(url, plexAuthToken, MediaContainer.class, isPostCall);
    }
}


// Trouble shooting xml unmarshalling
/*try {
            JAXBContext jaxbContext = JAXBContext.newInstance(MediaContainer.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            *//*unmarshaller.setEventHandler(
                    new ValidationEventHandler() {
                        @Override
                        public boolean handleEvent(ValidationEvent event ) {
                            throw new RuntimeException(event.getMessage(),
                                    event.getLinkedException());
                        }
                    });*//*
            StringReader reader = new StringReader(response);
            mediaContainer = (MediaContainer) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            e.printStackTrace();
        }*/