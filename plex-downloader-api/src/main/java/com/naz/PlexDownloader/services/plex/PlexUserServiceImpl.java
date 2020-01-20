package com.naz.PlexDownloader.services.plex;

import com.naz.PlexDownloader.models.plex.MediaContainer;
import com.naz.PlexDownloader.models.plex.PlexUser;
import com.naz.PlexDownloader.util.PlexRestTemplate;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlexUserServiceImpl implements PlexUserService {

    private static final String PLEX_USERS_URL = "https://plex.tv/api/users/";


    @Override
    public List<PlexUser> retrieveUsers(String authToken) {
        MediaContainer mediaContainer = (MediaContainer)
                PlexRestTemplate.buildPlexRestTemplateForXMLResponse(PLEX_USERS_URL, authToken, MediaContainer.class, false);

        if (null == mediaContainer || null == mediaContainer.getUser()) {
            throw new NotYetImplementedException();
        }

        return mediaContainer.getUser();
    }
}
