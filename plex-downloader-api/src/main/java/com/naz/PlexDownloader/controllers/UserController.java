package com.naz.PlexDownloader.controllers;


import com.naz.PlexDownloader.dtos.UserDTO;
import com.naz.PlexDownloader.models.User;
import com.naz.PlexDownloader.models.plex.Connection;
import com.naz.PlexDownloader.models.plex.Device;
import com.naz.PlexDownloader.models.plex.MediaContainer;
import com.naz.PlexDownloader.models.plex.PlexUser;
import com.naz.PlexDownloader.models.plex.Video;
import com.naz.PlexDownloader.services.SecurityService;
import com.naz.PlexDownloader.services.UserService;
import com.naz.PlexDownloader.services.plex.PlexLibraryService;
import com.naz.PlexDownloader.services.plex.auth.PlexAuthService;
import com.naz.PlexDownloader.validators.UserValidator;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    //Temp
    @Autowired
    private PlexAuthService plexAuthService;

    @Autowired
    private PlexLibraryService plexLibraryService;

    @RequestMapping("/registration")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void registration(@RequestBody UserDTO userDTO) {

        Map<String,String> map = new HashMap<>();
        MapBindingResult err = new MapBindingResult(map, UserDTO.class.getName());

        userValidator.validate(userDTO, err);

        if (err.hasErrors()) {
            throw new NotYetImplementedException();
        }

        User savedUser = userService.save(new User(userDTO));

        if (null == savedUser) {
            throw new NotYetImplementedException();
        }
    }

    /*@RequestMapping("/login")
    public User login(@RequestBody UserDTO userDTO) {

        User user = userService.findUserByUserNameAndPassword(userDTO.getUsername(), userDTO.getPassword());

        if (null == user) {
            throw new NotYetImplementedException();
        }

        return user;
    }*/

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public SecurityService getSecurityService() {
        return securityService;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    public UserValidator getUserValidator() {
        return userValidator;
    }

    public void setUserValidator(UserValidator userValidator) {
        this.userValidator = userValidator;
    }

    public PlexAuthService getPlexAuthService() {
        return plexAuthService;
    }

    public void setPlexAuthService(PlexAuthService plexAuthService) {
        this.plexAuthService = plexAuthService;
    }

    public PlexLibraryService getPlexLibraryService() {
        return plexLibraryService;
    }

    public void setPlexLibraryService(PlexLibraryService plexLibraryService) {
        this.plexLibraryService = plexLibraryService;
    }
}
