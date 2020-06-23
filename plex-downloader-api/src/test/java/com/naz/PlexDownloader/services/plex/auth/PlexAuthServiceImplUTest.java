package com.naz.PlexDownloader.services.plex.auth;

import com.naz.PlexDownloader.BaseUnitTest;
import com.naz.PlexDownloader.exceptions.BaseRuntimeException;
import com.naz.PlexDownloader.models.plex.Pin;
import com.naz.PlexDownloader.models.plex.PlexUser;
import com.naz.PlexDownloader.models.plex.UserEntity;
import com.naz.PlexDownloader.repositories.PlexRepository;
import com.naz.PlexDownloader.services.plex.PlexUserService;
import com.naz.PlexDownloader.util.CollectionUtil;
import com.naz.PlexDownloader.util.JwtTokenUtil;
import com.naz.PlexDownloader.util.PlexRestTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class PlexAuthServiceImplUTest extends BaseUnitTest {

    @InjectMocks
    private PlexAuthServiceImpl plexAuthServiceImpl;

    @Mock
    private PlexRestTemplate plexRestTemplate;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private PlexUserService plexUserService;

    private UserEntity userEntity;
    private String authToken = "Auth_Token";

    @BeforeEach
    protected void setUp() {

        PlexUser plexUser = new PlexUser();
        plexUser.setId(1L);
        plexUser.setAuthToken(this.authToken);

        this.userEntity = new UserEntity();
        this.userEntity.setUser(CollectionUtil.createList(plexUser));

        super.setUp();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testLoginBasicAuth() {

        when(plexRestTemplate.buildPlexRestTemplate(anyString(), anyString(), anyString(), any(), anyBoolean()))
                .thenReturn(this.userEntity);

        when(jwtTokenUtil.generateToken(any())).thenReturn(this.authToken);

        PlexUser result = plexAuthServiceImpl.loginBasicAuth("username", "password");

        assertEquals(authToken, result.getJwtToken());
    }

    @Test
    void testLoginBasicAuthUserNotFoundException() {

        this.userEntity.setUser(null);
        when(plexRestTemplate.buildPlexRestTemplate(anyString(), anyString(), anyString(), any(), anyBoolean()))
                .thenReturn(this.userEntity);

        when(jwtTokenUtil.generateToken(any())).thenReturn(this.authToken);


        Assertions.assertThrows(BaseRuntimeException.class, () -> {
            plexAuthServiceImpl.loginBasicAuth("username", "password");
        }, "user.cannot.be.found");
    }

    @Test
    void testLoginByAuthToken() {

        when(plexRestTemplate.buildPlexRestTemplate(anyString(), anyString(), any(), anyBoolean()))
                .thenReturn(this.userEntity);

        when(jwtTokenUtil.generateToken(any())).thenReturn(this.authToken);

        PlexUser result = plexAuthServiceImpl.loginByAuthToken("password");

        assertEquals(authToken, result.getJwtToken());
    }

    @Test
    void testLoginByAuthTokenUserNotFoundException() {

        this.userEntity.setUser(null);
        when(plexRestTemplate.buildPlexRestTemplate(anyString(), anyString(), any(), anyBoolean()))
                .thenReturn(this.userEntity);

        when(jwtTokenUtil.generateToken(any())).thenReturn(this.authToken);

        Assertions.assertThrows(BaseRuntimeException.class, () -> {
            plexAuthServiceImpl.loginByAuthToken("password");
        }, "user.cannot.be.found");
    }

    @Test
    void testRetrievePlexOAuthPin() {

        String testResolveURL = "https://app.plex.tv/auth#?clientID=Plex-Downloader&code=THE_CODE&X-Plex-Product=NAZ&X-Plex-Client-Identifier=NAZMB&X-Plex-Version=1.0.0";

        Pin pin = new Pin();
        pin.setAuthToken(this.authToken);
        pin.setCode("THE_CODE");

        when(plexRestTemplate.buildPlexRestTemplate(anyString(), any(), any(), anyBoolean()))
                .thenReturn(pin);

        Pin result = plexAuthServiceImpl.retrievePlexOAuthPin();

        assertEquals(testResolveURL, result.getResolvedUri());
    }

    @Test
    void testRetrievePlexOAuthPinException() {

        when(plexRestTemplate.buildPlexRestTemplate(anyString(), anyString(), any(), anyBoolean()))
                .thenReturn(null);
        Assertions.assertThrows(BaseRuntimeException.class, () -> {
            plexAuthServiceImpl.retrievePlexOAuthPin();
        }, "error");
    }

    @Test
    void testRetrieveOAuthToken() {
        Pin pin = new Pin();
        pin.setAuthToken(this.authToken);

        when(plexRestTemplate.buildPlexRestTemplate(anyString(), any(), any(), anyBoolean()))
                .thenReturn(pin);

        Pin result = plexAuthServiceImpl.retrieveOAuthToken("thePin");

        assertEquals(this.authToken, result.getAuthToken());
    }

    @Test
    void testRetrieveOAuthTokenException() {

        when(plexRestTemplate.buildPlexRestTemplate(anyString(), any(), any(), anyBoolean()))
                .thenReturn(null);

        Assertions.assertThrows(BaseRuntimeException.class, () -> {
            plexAuthServiceImpl.retrieveOAuthToken("thePin");
        }, "error");
    }

    @Test
    void testSavePlexUser() {
        PlexUser plexUser = CollectionUtil.getFirstElement(this.userEntity.getUser());

        when(plexAuthServiceImpl.savePlexUser(any())).thenReturn(plexUser);

        PlexUser result = plexAuthServiceImpl.savePlexUser(plexUser);

        assertEquals(plexUser.getId(), result.getId());

    }
}