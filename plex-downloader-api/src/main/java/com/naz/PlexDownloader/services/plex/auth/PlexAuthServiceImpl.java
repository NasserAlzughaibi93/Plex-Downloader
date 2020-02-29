package com.naz.PlexDownloader.services.plex.auth;

import com.naz.PlexDownloader.exceptions.rest.RecordNotFoundException;
import com.naz.PlexDownloader.models.plex.Pin;
import com.naz.PlexDownloader.models.plex.PlexUser;
import com.naz.PlexDownloader.models.plex.UserEntity;
import com.naz.PlexDownloader.repositories.PlexRepository;
import com.naz.PlexDownloader.util.CollectionUtil;
import com.naz.PlexDownloader.util.JwtTokenUtil;
import com.naz.PlexDownloader.util.PlexRestTemplate;
import com.naz.PlexDownloader.util.ValidationUtil;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    private PlexRestTemplate plexRestTemplate;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * Basic plex authentication (as opposed to OAuth login)
     * @param username - Plex username
     * @param password - Plex Password
     * @return - Logged in {@link PlexUser}
     */
    @Override
    public PlexUser loginBasicAuth(String username, String password) {

        UserEntity user = (UserEntity)
                plexRestTemplate.buildPlexRestTemplate(PLEX_AUTH_URL, username, password, UserEntity.class, true);

        ValidationUtil.NotNullOrEmpty("user.cannot.be.found", user, user.getUser());

        PlexUser plexUser = CollectionUtil.getFirstElement(user.getUser());

        String token = jwtTokenUtil.generateToken(plexUser);

        plexUser.setJwtToken(token);
        /*plexUser.setAuthenticationToken(bCryptPasswordEncoder.encode(plexUser.getAuthenticationToken()));
        plexUser.setAuthToken(bCryptPasswordEncoder.encode(plexUser.getAuthToken()));*/

        this.savePlexUser(plexUser);

        return plexUser;
    }

    /**
     * Login to plex by auth token (used when logging in through oauth)
     * @param authToken - Plex Authentication Token
     * @return {@link PlexUser}
     */
    @Override
    public PlexUser loginByAuthToken(String authToken) {

        UserEntity user = (UserEntity)
                plexRestTemplate.buildPlexRestTemplate(PLEX_AUTH_URL, authToken, UserEntity.class, true);

        ValidationUtil.NotNullOrEmpty("user.cannot.be.found", user, user.getUser());

        PlexUser plexUser = CollectionUtil.getFirstElement(user.getUser());

        String token = jwtTokenUtil.generateToken(plexUser);

        plexUser.setJwtToken(token);
        /*plexUser.setAuthenticationToken(bCryptPasswordEncoder.encode(plexUser.getAuthenticationToken()));
        plexUser.setAuthToken(bCryptPasswordEncoder.encode(plexUser.getAuthToken()));*/

        this.savePlexUser(plexUser);

        return plexUser;
    }

    /**
     * Retrieve the plex pin used for OAuth login.
     * @return {@link Pin}
     */
    @Override
    public Pin retrievePlexOAuthPin() {

        String url = PLEX_PIN_URL + "?strong=true";

        Pin pin = (Pin) plexRestTemplate.buildPlexRestTemplate(url, null, Pin.class, true);

        ValidationUtil.NotNullOrEmpty("error", pin);

        String resolvedUrl = "https://app.plex.tv/auth#?clientID={CLIENT_ID}&code={CODE}&X-Plex-Product=NAZ&X-Plex-Client-Identifier=NAZMB&X-Plex-Version=1.0.0";

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

        Pin pin = (Pin) plexRestTemplate.buildPlexRestTemplate(url, null, Pin.class, false);

        ValidationUtil.NotNullOrEmpty("error", pin);

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

    public JwtTokenUtil getJwtTokenUtil() {
        return jwtTokenUtil;
    }

    public void setJwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }
}
