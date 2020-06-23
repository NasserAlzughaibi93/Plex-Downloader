package com.naz.PlexDownloader.services.plex;

import com.naz.PlexDownloader.BaseUnitTest;
import com.naz.PlexDownloader.models.plex.MediaContainer;
import com.naz.PlexDownloader.models.plex.PlexUser;
import com.naz.PlexDownloader.repositories.PlexRepository;
import com.naz.PlexDownloader.util.CollectionUtil;
import com.naz.PlexDownloader.util.PlexRestTemplate;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class PlexUserServiceImplUTest extends BaseUnitTest {

    @InjectMocks
    private PlexUserServiceImpl plexUserServiceImpl;

    @Mock
    private PlexRestTemplate plexRestTemplate;

    @Mock
    private PlexRepository plexRepository;

    private PlexUser plexUser;

    private String authToken = "Auth_Token";

    @BeforeEach
    protected void setUp() {
        plexUser = new PlexUser();
        plexUser.setId(1L);
        plexUser.setAuthToken(this.authToken);

        super.setUp();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testRetrieveUsers() {

        List<PlexUser> plexUserCollection = CollectionUtil.createList(this.plexUser);

        MediaContainer mediaContainer = new MediaContainer();
        mediaContainer.setUser(plexUserCollection);

        when(plexRestTemplate.buildPlexRestTemplateForXMLResponse(anyString(), anyString(), any(), anyBoolean())).thenReturn(mediaContainer);

        List<PlexUser> result = plexUserServiceImpl.retrieveUsers(authToken);

        assertEquals(plexUserCollection.size(), result.size());
        assertEquals(this.plexUser.getId(), CollectionUtil.getFirstElement(result).getId());
    }

    @Test
    void testRetrieveUserByAuthToken() {

        when(plexRepository.findPlexUserByAuthToken(anyString())).thenReturn(this.plexUser);

        PlexUser result = plexUserServiceImpl.retrieveUserByAuthToken(this.authToken);

        assertEquals(this.plexUser.getId(), result.getId());

    }
}