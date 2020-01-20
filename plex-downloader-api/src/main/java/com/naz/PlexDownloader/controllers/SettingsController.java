package com.naz.PlexDownloader.controllers;

import com.naz.PlexDownloader.models.settings.About;
import com.naz.PlexDownloader.services.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/settings")
public class SettingsController {

    @Autowired
    private SettingsService settingsService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String test() {
        return "Testing";
    }


    @GetMapping("/about")
    @ResponseStatus(HttpStatus.OK)
    public About retrieveAppInfo() {
        return settingsService.retrieveAppInfo();
    }
}
