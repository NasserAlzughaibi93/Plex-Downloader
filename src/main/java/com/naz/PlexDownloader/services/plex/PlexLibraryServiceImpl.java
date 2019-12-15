package com.naz.PlexDownloader.services.plex;

import com.naz.PlexDownloader.models.plex.MediaContainer;
import com.naz.PlexDownloader.models.plex.Part;
import com.naz.PlexDownloader.models.plex.PlexUser;
import com.naz.PlexDownloader.models.plex.Video;
import com.naz.PlexDownloader.util.CollectionUtil;
import com.naz.PlexDownloader.util.PlexRestTemplate;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;

@Service
public class PlexLibraryServiceImpl implements PlexLibraryService {

    private static final String PLEX_BASE_URL = "https://plex.tv/api";

    private static final String PLEX_RESOURCES_URL = PLEX_BASE_URL + "/resources/";

    private static final String PLEX_LIBRARY = "/library";

    private static final String PLEX_ONDECK = PLEX_LIBRARY + "/onDeck";

    private static final String PLEX_SECTIONS = PLEX_LIBRARY + "/sections";

    private static final String PLEX_RECENTLY_ADDED = PLEX_LIBRARY + "/recentlyAdded";

    /**
     * Find available resources of a given server instance.
     * @param plexUser - The plex user.
     * @return
     */
    @Override
    public MediaContainer findPlexResources(PlexUser plexUser) {
        String authToken = plexUser.getAuthToken();

        MediaContainer mediaContainer =
                (MediaContainer) PlexRestTemplate.buildPlexRestTemplateForXMLResponse(PLEX_RESOURCES_URL, authToken, MediaContainer.class, false);

        if (null == mediaContainer) {
            throw new NotYetImplementedException();
        }

        return mediaContainer;
    }

    /**
     * Retrieves the On Deck Section of a given server instance.
     * @param plexUser - The plex user.
     * @param serverIp - The plex instance server IP.
     * @return
     */
    @Override
    public MediaContainer retrieveLibraryOnDeck(PlexUser plexUser, String serverIp) {
        String authToken = plexUser.getAuthToken();

        String url = serverIp + PLEX_ONDECK;

        MediaContainer mediaContainer =
                (MediaContainer) PlexRestTemplate.buildPlexRestTemplateForXMLResponse(url, authToken, MediaContainer.class, false);

        if (null == mediaContainer) {
            throw new NotYetImplementedException();
        }

        return mediaContainer;
    }

    /**
     * Retrieves the recently added section of a given server instance
     * @param plexUser - The plex user.
     * @param serverIp - The plex instance server IP.
     * @return - Media part of the section.
     */
    @Override
    public MediaContainer retrieveLibraryRecentlyAdded(PlexUser plexUser, String serverIp) {
        String authToken = plexUser.getAuthToken();

        String url = serverIp + PLEX_RECENTLY_ADDED;

        MediaContainer mediaContainer =
                (MediaContainer) PlexRestTemplate.buildPlexRestTemplateForXMLResponse(url, authToken, MediaContainer.class, false);

        if (null == mediaContainer) {
            throw new NotYetImplementedException();
        }

        return mediaContainer;
    }

    /**
     * Retrieve the library sections of a given server instance.
     * @param plexUser - The plex user.
     * @param serverIp - The plex instance server IP.
     * @return
     */
    @Override
    public MediaContainer retrieveLibrarySections(PlexUser plexUser, String serverIp) {
        String authToken = plexUser.getAuthToken();

        String url = serverIp + PLEX_SECTIONS;

        MediaContainer mediaContainer =
                (MediaContainer) PlexRestTemplate.buildPlexRestTemplateForXMLResponse(url, authToken, MediaContainer.class, false);

        if (null == mediaContainer) {
            throw new NotYetImplementedException();
        }

        return mediaContainer;
    }

    /**
     * Retrieves the media metadata.
     * @param plexUser - The plex user.
     * @param serverIp - The plex instance server IP.
     * @param libraryKey - The medias metadata url path.
     * @return - The {@link MediaContainer}
     */
    @Override
    public MediaContainer retrieveMediaMetadata(PlexUser plexUser, String serverIp, String libraryKey) {

        String authToken = plexUser.getAuthToken();

        String url = serverIp + libraryKey;

        MediaContainer mediaContainer =
                (MediaContainer) PlexRestTemplate.buildPlexRestTemplateForXMLResponse(url, authToken, MediaContainer.class, false);

        if (null == mediaContainer) {
            throw new NotYetImplementedException();
        }

        return mediaContainer;
    }

    /**
     * Generate the download link for the given media.
     * @param plexUser - The plex user.
     * @param serverIp - The plex instance server IP.
     * @param mediaContainer - The media
     * @return - The download link
     */
    @Override
    public String retrieveMediaDownloadLink(PlexUser plexUser, String serverIp, MediaContainer mediaContainer) {

        String downloadUrl = null;
        if (null == mediaContainer.getVideo() || mediaContainer.getVideo().isEmpty()) {
            throw new NotYetImplementedException();
        }

        Video video = CollectionUtil.getFirstElement(mediaContainer.getVideo());

        if (null == video.getMedia() || null == video.getMedia().getPart()) {
            mediaContainer = this.retrieveMediaMetadata(plexUser, serverIp, video.getKey());
            video = CollectionUtil.getFirstElement(mediaContainer.getVideo());
        }

        Part part = video.getMedia().getPart();

        if (null == video.getMedia() || null == video.getMedia().getPart()) {
            throw new NotYetImplementedException();
        }

        downloadUrl = serverIp + part.getKey() + "?download=1&X-Plex-Token=" + plexUser.getAuthToken();

        return downloadUrl;
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