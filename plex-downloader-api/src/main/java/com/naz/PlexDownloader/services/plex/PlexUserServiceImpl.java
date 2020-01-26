package com.naz.PlexDownloader.services.plex;

import com.naz.PlexDownloader.models.plex.MediaContainer;
import com.naz.PlexDownloader.models.plex.PlexUser;
import com.naz.PlexDownloader.repositories.PlexRepository;
import com.naz.PlexDownloader.util.PlexRestTemplate;
import com.naz.PlexDownloader.util.ValidationUtil;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlexUserServiceImpl implements PlexUserService {

    private static final String PLEX_USERS_URL = "https://plex.tv/api/users/";

    @Autowired
    private PlexRepository plexRepository;

    /**
     * Retrieve list of users associated with logged in plex user.
     * @param authToken - Plex Authentication Token.
     * @return {@link List<PlexUser>}
     */
    @Override
    public List<PlexUser> retrieveUsers(String authToken) {
        MediaContainer mediaContainer = (MediaContainer)
                PlexRestTemplate.buildPlexRestTemplateForXMLResponse(PLEX_USERS_URL, authToken, MediaContainer.class, false);

        ValidationUtil.NotNullOrEmpty("could.not.retrieve.users", mediaContainer, mediaContainer.getUser());

        return mediaContainer.getUser();
    }

    /**
     * Retrieve the user locally stored on the DB.
     * @param authToken - Plex Authentication Token.
     * @return {@link PlexUser}
     */
    @Override
    public PlexUser retrieveUserByAuthToken(String authToken) {

        PlexUser plexUser = this.plexRepository.findPlexUserByAuthToken(authToken);

        ValidationUtil.NotNullOrEmpty( "user.cannot.be.found", plexUser);

        return plexUser;
    }

    public PlexRepository getPlexRepository() {
        return plexRepository;
    }

    public void setPlexRepository(PlexRepository plexRepository) {
        this.plexRepository = plexRepository;
    }
}
