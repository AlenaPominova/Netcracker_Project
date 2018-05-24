package ru.vsu.netcracker.parking.backend.services;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.nio.charset.Charset;

public class BaseAuthorizationInterceptor implements ClientHttpRequestInterceptor {
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final String username;
    private final String password;

    public BaseAuthorizationInterceptor(String username, String password) {
        Assert.hasLength(username, "Username must not be empty");
        this.username = username;
        this.password = password != null ? password : "";
    }

    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String token = Base64Utils.encodeToString((this.username + ":" + this.password).getBytes(UTF_8));
        request.getHeaders().add("Authorization", "Base " + token);
        return execution.execute(request, body);
    }
}
