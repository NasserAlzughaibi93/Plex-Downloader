package com.naz.PlexDownloader.controllers;

import com.naz.PlexDownloader.BaseUnitTest;
import com.naz.PlexDownloader.dtos.UserDTO;
import com.naz.PlexDownloader.models.plex.Pin;
import com.naz.PlexDownloader.models.plex.PlexUser;
import com.naz.PlexDownloader.services.plex.auth.PlexAuthService;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class PlexAuthControllerUTest extends BaseUnitTest {

    @InjectMocks
    private PlexAuthController plexAuthController;

    @Mock
    private PlexAuthService plexAuthService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @BeforeEach
    protected void setUp() {
        super.setUp();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void loginBasicAuth() {
        PlexUser plexUser = new PlexUser();
        plexUser.setEmail("aaa@aaa.com");
        when(plexAuthService.loginBasicAuth(anyString(), anyString())).thenReturn(plexUser);

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username");
        userDTO.setPassword("password");
        PlexUser user = plexAuthController.loginBasicAuth(userDTO);

        assertNotNull(user);
    }

    @Test
    void retrievePlexOAuthPin() {

        Pin pin = new Pin();
        pin.setCode("111111");

        when(plexAuthService.retrievePlexOAuthPin()).thenReturn(pin);

        Pin result = plexAuthController.retrievePlexOAuthPin();

        assertEquals(pin, result);

    }

    @Test
    void retrieveOAuthToken() {

        String authToken = "abcdefg";
        Pin pin = new Pin();
        pin.setAuthToken(authToken);

        PlexUser plexUser = new PlexUser();
        plexUser.setAuthToken(authToken);

        when(plexAuthService.retrieveOAuthToken(anyString())).thenReturn(pin);
        when(plexAuthService.loginByAuthToken(anyString())).thenReturn(plexUser);

        PlexUser result = plexAuthController.loginByOAuth("authPin");

        assertEquals(authToken, result.getAuthToken());


    }
}