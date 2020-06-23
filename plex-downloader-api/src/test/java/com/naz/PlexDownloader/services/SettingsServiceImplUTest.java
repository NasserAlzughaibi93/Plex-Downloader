package com.naz.PlexDownloader.services;

import com.naz.PlexDownloader.BaseUnitTest;
import com.naz.PlexDownloader.dtos.SystemSettingsDTO;
import com.naz.PlexDownloader.exceptions.BaseRuntimeException;
import com.naz.PlexDownloader.exceptions.rest.RecordNotFoundException;
import com.naz.PlexDownloader.models.plex.PlexUser;
import com.naz.PlexDownloader.models.settings.About;
import com.naz.PlexDownloader.models.settings.SystemSettings;
import com.naz.PlexDownloader.repositories.SettingsRepository;
import com.naz.PlexDownloader.services.plex.PlexUserService;
import com.naz.PlexDownloader.util.JwtTokenUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Nasser Al-Zughaibi
 * @version 4/21/2020
 * @since plex-downloader
 */
class SettingsServiceImplUTest extends BaseUnitTest {

    @InjectMocks
    private SettingsServiceImpl settingsServiceImpl;

    @Mock
    private SettingsRepository settingsRepository;

    @Mock
    private PlexUserService plexUserService;

    @Mock
    private SystemSettings systemSettings;

    private PlexUser plexUser;

    private String authToken = "authToken";

    @BeforeEach
    @Override
    protected void setUp() {
        super.setUp();

        this.plexUser = new PlexUser();
        this.plexUser.setAuthToken(this.authToken);
        this.plexUser.setLibraryAuthToken(this.authToken);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testRetrieveSystemSettingsById() throws Exception {

        Optional optional = Optional.of(this.systemSettings);

        when(settingsRepository.findById(anyLong())).thenReturn(optional);

        SystemSettings result = this.settingsServiceImpl.retrieveSystemSettingsById(1L);

        assertEquals(this.systemSettings, result);
    }

    @Test
    void testRetrieveSystemSettingsByPlexUserAuthToken() throws Exception {

        when(plexUserService.retrieveUserByAuthToken(anyString())).thenReturn(this.plexUser);

        when(settingsRepository.findByPlexUser(anyObject())).thenReturn(this.systemSettings);

        SystemSettings result = this.settingsServiceImpl.retrieveSystemSettingsByPlexUserAuthToken(authToken);

        assertEquals(this.systemSettings, result);
    }

    @Test
    void testRetrieveSystemSettingsByPlexUserAuthToken_NotServerOwnerException() throws Exception {

        this.plexUser.setLibraryAuthToken("somethingElse");

        when(plexUserService.retrieveUserByAuthToken(anyString())).thenReturn(this.plexUser);

        when(settingsRepository.findByPlexUser(anyObject())).thenReturn(this.systemSettings);

        Assertions.assertThrows(RecordNotFoundException.class, () -> {
            this.settingsServiceImpl.retrieveSystemSettingsByPlexUserAuthToken(authToken);
        }, "user.is.not.server.owner");
    }

    @Test
    void testRetrieveSystemSettingsByPlexUserAuthToken_DefaultSystemSettings() throws Exception {

        when(plexUserService.retrieveUserByAuthToken(anyString())).thenReturn(this.plexUser);

        when(settingsRepository.findByPlexUser(anyObject())).thenReturn(null);

        Optional optional = Optional.of(this.systemSettings);

        when(settingsRepository.findById(anyLong())).thenReturn(optional);

        SystemSettings result = this.settingsServiceImpl.retrieveSystemSettingsByPlexUserAuthToken(authToken);

        assertEquals(this.systemSettings, result);
    }

    @Test
    void testSaveSystemSettingsForPlexUser() throws Exception {

        when(plexUserService.retrieveUserByAuthToken(anyString())).thenReturn(this.plexUser);

        when(settingsRepository.findByPlexUser(anyObject())).thenReturn(this.systemSettings);
        when(settingsRepository.save(anyObject())).thenReturn(this.systemSettings);

        SystemSettingsDTO systemSettingsDTO = new SystemSettingsDTO();
        systemSettingsDTO.setDownloadIntervalInMinutes(99);

        SystemSettings result = this.settingsServiceImpl.saveSystemSettingsForPlexUser(systemSettingsDTO, authToken);

        assertEquals(this.systemSettings, result);
    }

    @Test
    void testSaveSystemSettingsForPlexUser_NewSystemSettingForUser() throws Exception {

        when(plexUserService.retrieveUserByAuthToken(anyString())).thenReturn(this.plexUser);

        when(settingsRepository.findByPlexUser(anyObject())).thenReturn(this.systemSettings);
        when(settingsRepository.save(anyObject())).thenReturn(this.systemSettings);

        SystemSettingsDTO systemSettingsDTO = new SystemSettingsDTO();
        systemSettingsDTO.setDownloadIntervalInMinutes(99);

        SystemSettings result = this.settingsServiceImpl.saveSystemSettingsForPlexUser(systemSettingsDTO, authToken);

        assertEquals(this.systemSettings, result);
    }


    @Test
    void testSaveSystemSettingsForPlexUser_NotServerOwnerException() throws Exception {

        this.plexUser.setLibraryAuthToken("somethingElse");

        when(plexUserService.retrieveUserByAuthToken(anyString())).thenReturn(this.plexUser);

        when(settingsRepository.findByPlexUser(anyObject())).thenReturn(this.systemSettings);
        when(settingsRepository.save(anyObject())).thenReturn(this.systemSettings);

        SystemSettingsDTO systemSettingsDTO = new SystemSettingsDTO();
        systemSettingsDTO.setDownloadIntervalInMinutes(99);

        Assertions.assertThrows(RecordNotFoundException.class, () -> {
            this.settingsServiceImpl.saveSystemSettingsForPlexUser(systemSettingsDTO, authToken);
        }, "user.is.not.server.owner");
    }

    @Test
    void testSaveSystemSettings() throws Exception {
        when(settingsRepository.save(any())).thenReturn(this.systemSettings);

        SystemSettings result = this.settingsServiceImpl.saveSystemSettings(this.systemSettings);

        assertEquals(this.systemSettings, result);
    }

    @Test
    void testRetrieveAppInfo() throws Exception {

        About about = settingsServiceImpl.retrieveAppInfo();

        assertEquals(System.getProperty("os.name"), about.getOsName());
    }

    @Test
    void testValidateFullSettingsAccess() throws Exception {
    }
}
