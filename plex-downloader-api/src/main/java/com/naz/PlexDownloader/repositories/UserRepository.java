package com.naz.PlexDownloader.repositories;

import com.naz.PlexDownloader.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsername(String username);

}
