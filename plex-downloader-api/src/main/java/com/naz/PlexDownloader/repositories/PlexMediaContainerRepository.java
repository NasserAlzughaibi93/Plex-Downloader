package com.naz.PlexDownloader.repositories;

import com.naz.PlexDownloader.models.plex.MediaContainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlexMediaContainerRepository extends JpaRepository<MediaContainer, Long> {
}
