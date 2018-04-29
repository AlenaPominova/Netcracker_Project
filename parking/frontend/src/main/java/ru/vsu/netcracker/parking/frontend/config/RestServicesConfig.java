package ru.vsu.netcracker.parking.frontend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.vsu.netcracker.parking.frontend.utils.CustomRestTemplate;

@Configuration
public class RestServicesConfig {

    @Bean
    public RestTemplate parkingBackendRestTemplate() {
        return new CustomRestTemplate("http://localhost:8080/", "rest@gmail.com", "cmVzdEBnbWFpbC5jb21tB9hVpucaKSiH/KIbrrqH7Ay0obL+KS42xpxd87TmFA==");
    }

    @Bean
    public CustomRestTemplate evacServiceRestTemplate() {
        return new CustomRestTemplate("http://someUrl:8080/", "someUsername", "somePassword");
    }
}
