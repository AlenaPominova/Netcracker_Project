package ru.vsu.netcracker.parking.frontend.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import ru.vsu.netcracker.parking.frontend.exceptions.ResourceNotFoundException;
import ru.vsu.netcracker.parking.frontend.exceptions.UserAlreadyExistsException;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

/**
 * Error handler for RestTemplate
 */
public class CustomResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = response.getStatusCode();
        return statusCode.series() == CLIENT_ERROR || statusCode.series() == SERVER_ERROR;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        // ToDo Log
        HttpStatus statusCode = response.getStatusCode();
        String errorMessage = IOUtils.toString(response.getBody());
        String errorMessage2 = errorMessage.substring(1, errorMessage.length()-1);

        switch (statusCode) {
            case CONFLICT:
                switch (errorMessage2) {
                    case "User with such email or phone already exists":
                        throw new UserAlreadyExistsException(errorMessage);
                    case "Free spots count can not be less than 0":
                        throw new IllegalArgumentException(errorMessage);
                    default:
                        throw new IllegalArgumentException(errorMessage);
                }
            case NOT_FOUND:
                throw new ResourceNotFoundException(errorMessage);
            default:
                switch (statusCode.series()) {
                    case CLIENT_ERROR:
                        throw new HttpClientErrorException(statusCode, response.getStatusText(), response.getHeaders(), this.getResponseBody(response), this.getCharset(response));
                    case SERVER_ERROR:
                        throw new HttpServerErrorException(statusCode, response.getStatusText(), response.getHeaders(), this.getResponseBody(response), this.getCharset(response));
                    default:
                        throw new RestClientException("Unknown status code [" + statusCode + "]");
                }
        }
    }

    protected byte[] getResponseBody(ClientHttpResponse response) {
        try {
            return FileCopyUtils.copyToByteArray(response.getBody());
        } catch (IOException var3) {
            return new byte[0];
        }
    }

    protected Charset getCharset(ClientHttpResponse response) {
        HttpHeaders headers = response.getHeaders();
        MediaType contentType = headers.getContentType();
        return contentType != null ? contentType.getCharset() : null;
    }

}
