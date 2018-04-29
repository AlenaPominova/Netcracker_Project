package ru.vsu.netcracker.parking.backend.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import ru.vsu.netcracker.parking.backend.dao.ObjectsDAO;
import ru.vsu.netcracker.parking.backend.dao.PostgresObjectsDAO;
import ru.vsu.netcracker.parking.backend.json.JsonConverter;
import ru.vsu.netcracker.parking.backend.models.Attributes;

import javax.sql.DataSource;

@Configuration
@PropertySource(value = "classpath:db.properties")
public class SpringDaoConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.username"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
        dataSource.setValidationQuery(env.getProperty("jdbc.validationQuery"));
        return dataSource;
    }

    @Bean
    public Attributes attributes() {
        return new Attributes();
    }

    @Bean
    public JsonConverter jsonConverter() {
        return new JsonConverter(attributes());
    }

    @Bean
    public ObjectsDAO objectsDAO() {
        return new PostgresObjectsDAO(dataSource(), jsonConverter());
    }
}
