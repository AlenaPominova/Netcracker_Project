package ru.vsu.netcracker.parking.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import ru.vsu.netcracker.parking.backend.security.CustomAuthenticationProvider;
import ru.vsu.netcracker.parking.backend.security.CustomSHA256PasswordEncoder;

@EnableWebSecurity(debug = false)
public class SpringHttpSecurityConfig {

    @Autowired
    CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Bean
    CustomSHA256PasswordEncoder passwordEncoder() {
        return new CustomSHA256PasswordEncoder();
    }

    @Configuration
    @Order(1)
    public class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/restapi/**")
                    .authorizeRequests()
                    .anyRequest().hasRole("REST_API_USER")
                    .and()
                    .httpBasic()
                    .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and().csrf().disable();
        }
    }

    @Configuration
    public class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .anyRequest().hasRole("ADMIN")
                    .and()
                    .formLogin()
                    .defaultSuccessUrl("/", true)
                    .failureUrl("/404")
                    .and()
                    .csrf().disable();
        }
    }

}