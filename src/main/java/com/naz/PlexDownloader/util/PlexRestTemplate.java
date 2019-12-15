package com.naz.PlexDownloader.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;

public class PlexRestTemplate {

    private static final String PLEX_PRODUCT = "Plex-Downloader";

    /**
     * Prepare the rest template with headers that will always be used.
     * @param url - External URL to call.
     * @param username - Plex Username.
     * @param password - Plex user Password.
     * @param tClass - The model class.
     * @return - Ready to go RestTempltate
     */
    public static Object buildPlexRestTemplate(String url,
                                               String username,
                                               String password,
                                               Class tClass, boolean isPostCall) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("X-Plex-Product", PLEX_PRODUCT);
        headers.add("X-Plex-Client-Identifier", PLEX_PRODUCT);
        headers.add("X-Plex-Version", "0.0.1");
        headers.setBasicAuth(username, password);

        HttpEntity<HttpHeaders> request = new HttpEntity<>(headers);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(mapper);
        restTemplate.setMessageConverters(Collections.singletonList(converter));

        if (isPostCall) {
            ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.POST, request, tClass);
            return responseEntity.getBody();
        } else {
            ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, tClass);
            return responseEntity.getBody();
        }

    }

    /**
     * Prepare the rest template with headers that will always be used.
     * @param url - External URL to call.
     * @param authToken - Plex auth token.
     * @param tClass - The model class.
     * @return - Ready to go RestTempltate
     */
    public static Object buildPlexRestTemplate(String url,
                                               String authToken,
                                               Class tClass, boolean isPostCall) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("X-Plex-Product", PLEX_PRODUCT);
        headers.add("X-Plex-Client-Identifier", PLEX_PRODUCT);
        headers.add("X-Plex-Version", "0.0.1");
        headers.add("X-Plex-Token", "authToken");

        HttpEntity<HttpHeaders> request = new HttpEntity<>(headers);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(mapper);
        restTemplate.setMessageConverters(Collections.singletonList(converter));

        if (isPostCall) {
            ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.POST, request, tClass);
            return responseEntity.getBody();
        } else {
            ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, tClass);
            return responseEntity.getBody();
        }

    }

    /**
     * Prepare the rest template with headers that will always be used. XML is the expected response.
     * @param url - External URL to call.
     * @param authToken - Plex auth token.
     * @param tClass - The model class.
     * @return - Ready to go RestTempltate
     */
    public static Object buildPlexRestTemplateForXMLResponse(String url,
                                        String authToken,
                                        Class tClass, boolean isPostCall) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("X-Plex-Product", PLEX_PRODUCT);
        headers.add("X-Plex-Client-Identifier", PLEX_PRODUCT);
        headers.add("X-Plex-Version", "0.0.1");
        headers.add("X-Plex-Token", authToken);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));

        HttpEntity<HttpHeaders> request = new HttpEntity<>(headers);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        /*MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(mapper);
        restTemplate.setMessageConverters(Collections.singletonList(converter));*/

        if (isPostCall) {
            ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.POST, request, tClass);
            return responseEntity.getBody();
        } else {
            ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, tClass);
            return responseEntity.getBody();
        }
    }
}
