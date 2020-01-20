package com.naz.PlexDownloader.controllers;

import com.naz.PlexDownloader.dtos.UserDTO;
import com.naz.PlexDownloader.models.plex.Pin;
import com.naz.PlexDownloader.models.plex.PlexUser;
import com.naz.PlexDownloader.services.plex.auth.PlexAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PlexAuthController {

    @Autowired
    private PlexAuthService plexAuthService;

    @PostMapping("/basiclogin")//TODO resolve springs default login screen
    @ResponseStatus(HttpStatus.OK)
    public PlexUser loginBasicAuth(@RequestBody UserDTO userDTO) {
        return plexAuthService.loginBasicAuth(userDTO.getUsername(), userDTO.getPassword());
    }

    @GetMapping("/oAuth")
    @ResponseStatus(HttpStatus.OK)
    public Pin retrievePlexOAuthPin() {
        return plexAuthService.retrievePlexOAuthPin();
    }

    @GetMapping("/oAuth/{oAuthPin}")
    @ResponseStatus(HttpStatus.OK)
    public Pin retrieveOAuthToken(@PathVariable String oAuthPin) {
        return plexAuthService.retrieveOAuthToken(oAuthPin);
    }

}
