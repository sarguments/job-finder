package me.saru.jobfinder.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiScrapper {
    private RestTemplate restTemplate;

    public ApiScrapper(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String get(String uri) {
        return restTemplate.getForObject(uri, String.class);
    }
}
