package com.naz.PlexDownloader.services.plex.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.naz.PlexDownloader.models.plex.PlexUser;
import com.naz.PlexDownloader.models.plex.UserEntity;
import com.naz.PlexDownloader.repositories.PlexRepository;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class PlexAuthServiceImpl implements PlexAuthService {

    private static final String PLEX_BASE_URL = "https://plex.tv/";

    private static final String PLEX_AUTH_URL = PLEX_BASE_URL + "users/sign_in.json";

    @Autowired
    private PlexRepository plexRepository;

    @Override
    public PlexUser loginBasicAuth(String username, String password) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("X-Plex-Product", "Plex-Downloader");
        headers.add("X-Plex-Client-Identifier", "Plex-Downloader");
        headers.add("X-Plex-Version", "1.0.0");
        headers.setBasicAuth(username, password);

        HttpEntity<HttpHeaders> request = new HttpEntity<>(headers);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(mapper);
        restTemplate.setMessageConverters(Collections.singletonList(converter));

        UserEntity user = restTemplate.postForObject(PLEX_AUTH_URL, request,  UserEntity.class);

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
