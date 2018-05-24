package ru.vsu.netcracker.parking.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.Yaml;
import ru.vsu.netcracker.parking.backend.models.RestService;
import ru.vsu.netcracker.parking.backend.services.CustomRestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Configuration
public class RestServicesConfig {

    private Map<String, RestService> restServices;

    public void setServices(Map<String, RestService> restServices) {
        this.restServices = restServices;
    }

    @PostConstruct
    private void getServices() throws IOException {
        Yaml yaml = new Yaml();
        Resource resource = new ClassPathResource("rest-services.yml");
        try (InputStream in = resource.getInputStream()) {
            this.restServices = yaml.loadAs(in, RestServicesConfig.class).restServices;
        }
    }

    @Bean
    public CustomRestTemplate evacServiceRestTemplate() {
        RestService service = restServices.get("evac");
        return new CustomRestTemplate(service.getUrl(), service.getUsername(), service.getPassword());
    }
}
