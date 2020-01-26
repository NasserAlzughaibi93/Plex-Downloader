package com.naz.PlexDownloader.services.plex.auth;

import com.naz.PlexDownloader.models.plex.Pin;
import com.naz.PlexDownloader.models.plex.PlexUser;

public interface PlexAuthService {

    PlexUser loginBasicAuth(String username, String password);

    PlexUser loginByAuthToken(String authToken);

    PlexUser savePlexUser(PlexUser plexUser);

    Pin retrievePlexOAuthPin();

    Pin retrieveOAuthToken(String oAuthPin);

}
