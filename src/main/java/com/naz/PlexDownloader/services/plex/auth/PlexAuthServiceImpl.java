package com.naz.PlexDownloader.services.plex.auth;

import com.naz.PlexDownloader.models.plex.PlexUser;
import com.naz.PlexDownloader.models.plex.UserEntity;
import com.naz.PlexDownloader.repositories.PlexRepository;
import com.naz.PlexDownloader.util.PlexRestTemplate;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlexAuthServiceImpl implements PlexAuthService {

    private static final String PLEX_BASE_URL = "https://plex.tv/";

    private static final String PLEX_AUTH_URL = PLEX_BASE_URL + "users/sign_in.json";

    @Autowired
    private PlexRepository plexRepository;

    @Override
    public PlexUser loginBasicAuth(String username, String password) {

        UserEntity user = (UserEntity)
                PlexRestTemplate.buildPlexRestTemplate(PLEX_AUTH_URL, username, password, UserEntity.class, true);

//        UserEntity user = restTemplate.postForObject(PLEX_AUTH_URL, request,  UserEntity.class);

        /*ResponseEntity<PlexUser[]> response = restTemplate.postForEntity(PLEX_AUTH_URL, request, PlexUser[].class);

        PlexUser user = response.getBody()[0];*/
        /*ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        try {
            UserEntity plexUser = mapper.readValue(user, UserEntity.class);

            int i = 1;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }*/

        if (null == user) {
            throw new NotYetImplementedException();
        }

        return user.getUser().get(0);
    }

    @Override
    public PlexUser savePlexUser(PlexUser plexUser) {
        return this.plexRepository.save(plexUser);
    }

    public PlexRepository getPlexRepository() {
        return plexRepository;
    }

    public void setPlexRepository(PlexRepository plexRepository) {
        this.plexRepository = plexRepository;
    }
}
