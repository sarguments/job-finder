package me.saru.jobfinder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan("me.saru.jobfinder.domain")
public class WebMvcConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
