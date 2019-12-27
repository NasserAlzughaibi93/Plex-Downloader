package com.naz.PlexDownloader.services.plex.auth;

import com.naz.PlexDownloader.models.plex.Pin;
import com.naz.PlexDownloader.models.plex.PlexUser;
import com.naz.PlexDownloader.models.plex.UserEntity;
import com.naz.PlexDownloader.repositories.PlexRepository;
import com.naz.PlexDownloader.util.PlexRestTemplate;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.PropertyPlaceholderHelper;

import static com.naz.PlexDownloader.util.Constants.PLEX_PRODUCT;

@Service
public class PlexAuthServiceImpl implements PlexAuthService {

    private static final String PLEX_BASE_URL = "https://plex.tv/";

    private static final String PLEX_AUTH_URL = PLEX_BASE_URL + "users/sign_in.json";

    private static final String PLEX_PIN_URL = "https://plex.tv/api/v2/pins";

    @Autowired
    private PlexRepository plexRepository;

    /**
     * Basic plex authentication (as opposed to OAuth login)
     * @param username - Plex username
     * @param password - Plex Password
     * @return - Logged in {@link PlexUser}
     */
    @Override
    public PlexUser loginBasicAuth(String username, String password) {

        UserEntity user = (UserEntity)
                PlexRestTemplate.buildPlexRestTemplate(PLEX_AUTH_URL, username, password, UserEntity.class, true);

//        UserEntity user = restTemplate.postForObject(PLEX_AUTH_URL, request,  UserEntity.class);

        /*ResponseEntity<PlexUser[]> response = restTemplate.postForEntity(PLEX_AUTH_URL, request, PlexUser[].class);

        PlexUser user = response.getBody()[0];*/
        /*ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        try {
            UserEntity plexUser = mapper.readValue(user, UserEntity.class);

            int i = 1;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }*/

        if (null == user) {
            throw new NotYetImplementedException();
        }

        return user.getUser().get(0);
    }

    /**
     * Retrieve the plex pin used for OAuth login.
     * @return {@link Pin}
     */
    @Override
    public Pin retrievePlexOAuthPin() {

        String url = PLEX_PIN_URL + "?strong=true";

        Pin pin = (Pin) PlexRestTemplate.buildPlexRestTemplate(url, null, Pin.class, true);

        if (null == pin) {
            throw new NotYetImplementedException();
        }

        String resolvedUrl = "https://app.plex.tv/auth#?forwardUrl=http://google.com&clientID={CLIENT_ID}&code={CODE}&X-Plex-Product=NAZ&X-Plex-Client-Identifier=NAZMB&X-Plex-Version=1.0.0";

        resolvedUrl = resolvedUrl.replace("{CODE}", pin.getCode()).replace("{CLIENT_ID}", PLEX_PRODUCT);
        pin.setResolvedUri(resolvedUrl);

        return pin;
    }

    /**
     * Retrieve auth token {PlexUser.authToken} used to login and for future requests.
     * @param oAuthPin - code returned when the user signs in through the OAuth login window.
     * @return Resolved pin with plex auth token.
     */
    @Override
    public Pin retrieveOAuthToken(String oAuthPin) {

        String url = PLEX_PIN_URL + "/" + oAuthPin;

        Pin pin = (Pin) PlexRestTemplate.buildPlexRestTemplate(url, null, Pin.class, false);

        if (null == pin) {
            throw new NotYetImplementedException();
        }

        return pin;
    }

    @Override
    public PlexUser savePlexUser(PlexUser plexUser) {
        return this.plexRepository.save(plexUser);
    }

    public PlexRepository getPlexRepository() {
        return plexRepository;
    }

    public void setPlexRepository(PlexRepository plexRepository) {
        this.plexRepository = plexRepository;
    }
}
