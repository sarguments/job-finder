package me.saru.jobfinder.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ApiScrapper {
    private RestTemplate restTemplate;

    public ApiScrapper(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String get(String uri) {
        return restTemplate.getForObject(uri, String.class);
    }

    public String fetchWantedJson() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("https").host("www.wanted.co.kr")
                .path("/api/v3/search")
                .queryParam("locale", "ko-kr")
                .queryParam("country", "KR")
                .queryParam("years", "-1")
                .queryParam("limit", "20")
                .queryParam("offset", "0")
                .queryParam("job_sort", "-confirm_time")
                .queryParam("tag_type_id", "{tag-type-id}")
                .buildAndExpand("872").encode();

        String uri = uriComponents.toUriString();
        return restTemplate.getForObject(uri, String.class);
    }

    public String fetchNextJob(String next) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("https").host("www.wanted.co.kr")
                .path(next)
                .build();

        String uri = uriComponents.toUriString();
        return restTemplate.getForObject(uri, String.class);
    }

    public String fetchJobs(int jobId) {
        String uri = "https://www.wanted.co.kr/api/v1/jobs/" + jobId;
        return restTemplate.getForObject(uri, String.class);
    }
}
