package com.naz.PlexDownloader.services;

import com.naz.PlexDownloader.models.User;

public interface UserService {

    User save(User user);

    User findByUsername(String username);

    User findUserByUserNameAndPassword(String username, String password);
}
