package com.naz.PlexDownloader.repositories;

import com.naz.PlexDownloader.models.plex.PlexUser;
import com.naz.PlexDownloader.models.settings.SystemSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsRepository extends JpaRepository<SystemSettings, Long> {

    SystemSettings findByPlexUser(PlexUser plexUser);
}
