package com.naz.PlexDownloader.services.plex;

import com.naz.PlexDownloader.models.DownloadRequest;
import com.naz.PlexDownloader.models.plex.Connection;
import com.naz.PlexDownloader.models.plex.Device;
import com.naz.PlexDownloader.models.plex.Directory;
import com.naz.PlexDownloader.models.plex.MediaContainer;
import com.naz.PlexDownloader.models.plex.Part;
import com.naz.PlexDownloader.models.plex.PlexUser;
import com.naz.PlexDownloader.models.plex.Track;
import com.naz.PlexDownloader.models.plex.Video;
import com.naz.PlexDownloader.util.CollectionUtil;
import com.naz.PlexDownloader.util.PlexRestTemplate;
import com.naz.PlexDownloader.util.ValidationUtil;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String PHOTO_HEIGHT = "height=500&";

    private static final String PHOTO_WIDTH = "width=500&";

    @Autowired
    private PlexRestTemplate plexRestTemplate;

    @Autowired
    private PlexUserService plexUserService;

    /**
     * Find available resources of a given server instance.
     *
     * @param plexAuthToken - The plex auth token.
     * @return {@link MediaContainer}
     */
    @Override
    public List<Device> findPlexResources(String plexAuthToken) {

        MediaContainer mediaContainer = this.buildPlexRestCall(plexAuthToken, PLEX_RESOURCES_URL, false);
        List<Device> serverDevices = new ArrayList<>();

        ValidationUtil.NotNullOrEmpty("could.not.retrieve.media", mediaContainer);

        if (!CollectionUtil.isNullOrEmpty(mediaContainer.getDevice())) {

            for (Device device : mediaContainer.getDevice()) {
                if (device.getProvides().contains("server")) {
                    serverDevices.add(device);
                }
            }

            PlexUser plexUser = this.plexUserService.retrieveUserByAuthToken(plexAuthToken);
            plexUser.setAccessibleServers(serverDevices);
            plexUser.setLibraryAuthToken(plexAuthToken);

            this.plexUserService.savePlexUser(plexUser);
        }

        return serverDevices;
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

        ValidationUtil.NotNullOrEmpty("could.not.retrieve.media", mediaContainer, mediaContainer.getVideo());

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

        ValidationUtil.NotNullOrEmpty("could.not.retrieve.media", mediaContainer, mediaContainer.getVideo());

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

        plexAuthToken = this.updateLibraryAuthToken(plexAuthToken, serverIp);

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
    public MediaContainer retrieveMediaMetadata(String plexAuthToken, String serverIp, String libraryKey) {

        String url = serverIp + libraryKey;

        MediaContainer mediaContainer = this.buildPlexRestCall(plexAuthToken, url, false);

        ValidationUtil.NotNullOrEmpty("could.not.retrieve.media", mediaContainer);

        return mediaContainer;
    }

    /**
     * Retrieves the media metadata children (Show seasons).
     *
     * @param plexAuthToken - The plex auth token.
     * @param serverIp      - The plex instance server IP.
     * @param libraryKey    - The medias metadata url path.
     * @return - The {@link MediaContainer}
     */
    @Override
    public MediaContainer retrieveMediaMetadataChildren(String plexAuthToken, String serverIp, String libraryKey) {
        String url = serverIp + libraryKey;

        MediaContainer mediaContainer = this.buildPlexRestCall(plexAuthToken, url, false);

        ValidationUtil.NotNullOrEmpty("could.not.retrieve.media", mediaContainer);

        return mediaContainer;
    }

    /**
     * Generate the download link for the given media.
     *
     * @param plexAuthToken   - The plex auth token.
     * @param serverIp        - The plex instance server IP.
     * @param downloadRequest - The download request key and type
     * @return - The download link
     */
    @Override
    public String retrieveMediaDownloadLink(final String plexAuthToken, final String serverIp, final DownloadRequest downloadRequest) {

        String downloadUrl = "";

        ValidationUtil.NotNullOrEmpty("missing.required.parameter",
                new Object[]{"downloadRequest"}, downloadRequest);

        MediaContainer mediaContainer = this.retrieveMediaMetadata(plexAuthToken, serverIp, downloadRequest.getKey());

        switch (downloadRequest.getMediaType()) {

            case Video:
                Video video = CollectionUtil.getFirstElement(mediaContainer.getVideo());
                Part part = video.getMedia().getPart();

                ValidationUtil.NotNullOrEmpty("could.not.retrieve.media", video.getMedia(), video.getMedia().getPart());

                downloadUrl = serverIp + part.getKey() + "?download=1&X-Plex-Token=" + plexAuthToken;
                break;
            case Music:
                Track track = CollectionUtil.getFirstElement(mediaContainer.getTrack());
                Part musicPart = track.getMedia().getPart();

                ValidationUtil.NotNullOrEmpty("could.not.retrieve.media", track.getMedia(), track.getMedia().getPart());

                downloadUrl = serverIp + musicPart.getKey() + "?download=1&X-Plex-Token=" + plexAuthToken;
            case Series:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + downloadRequest.getMediaType());
        }

        ValidationUtil.NotNullOrEmpty(downloadUrl);

        return downloadUrl;
    }

    @Override
    public MediaContainer retrieveSearchResults(String serverIp, String searchQuery, String plexAuthToken) {

        String url = serverIp + PLEX_SEARCH + searchQuery;

        MediaContainer mediaContainer = this.buildPlexRestCall(plexAuthToken, url, false);

        ValidationUtil.NotNullOrEmpty("could.not.retrieve.media", mediaContainer,
                mediaContainer.getVideo(), mediaContainer.getDirectory());

        retrieveTranscodedPhotosForMedia(mediaContainer, serverIp, plexAuthToken);

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

        ValidationUtil.NotNullOrEmpty("could.not.retrieve.media", mediaContainer, mediaContainer.getDirectory());

        return mediaContainer.getDirectory();
    }

    @Override
    public MediaContainer retrieveLibrarySectionBySectionKeyAndDirectoryKey(String plexAuthToken, String serverIp, String librarySectionKey, String directoryKey) {
        String url = serverIp + PLEX_SECTIONS + "/" + librarySectionKey + "/" + directoryKey;

        MediaContainer mediaContainer = this.buildPlexRestCall(plexAuthToken, url, false);

        ValidationUtil.NotNullOrEmpty("could.not.retrieve.media", mediaContainer);

        retrieveTranscodedPhotosForMedia(mediaContainer, serverIp, plexAuthToken);

        return mediaContainer;
    }

    @Override
    public String retrievePhotoFromPlexServer(String plexAuthToken, String serverIp, String metadataKey) {

        String url = serverIp + "/photo/:/transcode?url=" + metadataKey + "&" + PHOTO_HEIGHT + PHOTO_WIDTH + "X-Plex-Token=" + plexAuthToken;

        if (!url.contains("http://") && !url.contains("https://")) {
            url = "http://" + url;
        }

//        String photoUrl = (String) PlexRestTemplate.buildPlexRestTemplate(url, plexAuthToken, String.class, false);

        return url;
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

        return (MediaContainer) plexRestTemplate.buildPlexRestTemplateForXMLResponse(url, plexAuthToken, MediaContainer.class, isPostCall);
    }

    private void retrieveTranscodedPhotosForMedia(MediaContainer mediaContainer, String serverIp, String plexAuthToken) {

        if (!CollectionUtil.isNullOrEmpty(mediaContainer.getVideo())) {
            final List<Video> videos = mediaContainer.getVideo();

            for (Video video: videos) {

                String photoMetaDataKey = "";

                if (video.getType().equals("movie")) {
                    photoMetaDataKey = video.getThumb();
                } else {
                    photoMetaDataKey = video.getGrandparentThumb();
                }

                String transcodedPhoto = retrievePhotoFromPlexServer(plexAuthToken, serverIp, photoMetaDataKey);

                video.setTranscodedPhoto(transcodedPhoto);
            }
        }

        if (!CollectionUtil.isNullOrEmpty(mediaContainer.getDirectory())) {
            final List<Directory> shows = mediaContainer.getDirectory();
            for (Directory show: shows) {
                String photoMetaDataKey = show.getThumb();

                String transcodedPhoto = retrievePhotoFromPlexServer(plexAuthToken, serverIp, photoMetaDataKey);

                show.setTranscodedPhoto(transcodedPhoto);
            }
        }
    }

    private String updateLibraryAuthToken(final String plexAuthToken, final String serverIp) {

        PlexUser plexUser = this.plexUserService.retrieveUserByAuthToken(plexAuthToken);

        if (!CollectionUtil.isNullOrEmpty(plexUser.getAccessibleServers())) {
            for (Device device : plexUser.getAccessibleServers()) {
                if (!CollectionUtil.isNullOrEmpty(device.getConnection())) {
                    for (Connection connection : device.getConnection()) {
                        if (serverIp.contains(connection.getAddress())) {

                            plexUser.setLibraryAuthToken(device.getAccessToken());
                            this.plexUserService.savePlexUser(plexUser);

                            return device.getAccessToken();
                        }
                    }
                }
            }

        }

        return plexAuthToken;
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
