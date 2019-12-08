package com.naz.PlexDownloader.controllers;


import com.naz.PlexDownloader.dtos.UserDTO;
import com.naz.PlexDownloader.models.User;
import com.naz.PlexDownloader.services.SecurityService;
import com.naz.PlexDownloader.services.UserService;
import com.naz.PlexDownloader.validators.UserValidator;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;



    @RequestMapping("/registration")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void registration(@RequestBody UserDTO userDTO) {

        Map<String,String> map = new HashMap<String,String>();
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

    @RequestMapping("/login")
    public User login(@RequestBody UserDTO userDTO) {

        User user = userService.findUserByUserNameAndPassword(userDTO.getUsername(), userDTO.getPassword());

        if (null == user) {
            throw new NotYetImplementedException();
        }

        return user;
    }

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
}
