package ru.vsu.netcracker.parking.frontend.utils;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class VerifyCaptcha {

    private static final String URL = "https://www.google.com/recaptcha/api/siteverify";
    private static final String SECRET = "6Lfqp1sUAAAAACjJaoI4NjdwGnw2LtUxuVHyyRE2";

    public static boolean verify(String gRecaptchaResponse) {
        if (gRecaptchaResponse == null || "".equals(gRecaptchaResponse)) {
            return false;
        }
        String requestParams = "?secret=" + SECRET + "&response="
                + gRecaptchaResponse;
        RestTemplate restTemplate = new RestTemplate();

        JsonNode json = null;
        try {
            json = restTemplate.postForObject(URL + requestParams, null, JsonNode.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            return false;
        }
        boolean success = json.get("success").asBoolean();
        return success;
    }
}
