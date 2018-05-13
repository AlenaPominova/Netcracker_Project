package ru.vsu.netcracker.parking.frontend.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.*;

import java.net.URI;

public class CustomRestTemplate extends RestTemplate {

    private final String BASE_URL;
    private final String USERNAME;
    private final String PASSWORD;

    public CustomRestTemplate(String baseURL, String username, String password) {
        super();
        this.BASE_URL = baseURL;
        this.USERNAME = username;
        this.PASSWORD = password;
        this.getInterceptors().add(new BasicAuthorizationInterceptor(this.USERNAME, this.PASSWORD));
    }

    @Override
    public <T> T getForObject(String url, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return super.getForObject(BASE_URL + url, responseType, uriVariables);
    }

    @Override
    public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return super.getForEntity(BASE_URL + url, responseType, uriVariables);
    }

    @Override
    public <T> T postForObject(String url, Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return super.postForObject(BASE_URL + url, request, responseType, uriVariables);
    }

    @Override
    public void put(String url, Object request, Object... uriVariables) throws RestClientException {
        super.put(BASE_URL + url, request, uriVariables);
    }

    @Override
    public void delete(URI url) throws RestClientException {
        super.delete(BASE_URL + url);
    }

    @Override
    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return super.exchange(BASE_URL + url, method, requestEntity, responseType, uriVariables);
    }

}
