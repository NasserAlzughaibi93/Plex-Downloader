package com.naz.PlexDownloader.repositories;

import com.naz.PlexDownloader.models.plex.PlexUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlexRepository extends JpaRepository<PlexUser, Long> {

    PlexUser findPlexUserByAuthToken(String authToken);

}
