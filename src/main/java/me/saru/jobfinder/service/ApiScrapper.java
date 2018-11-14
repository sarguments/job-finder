package me.saru.jobfinder.service;

import me.saru.jobfinder.domain.Company;
import me.saru.jobfinder.dto.CompanyDto;
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

    public CompanyDto fetchCompanyInfo(Company company) {
        // TODO 인터페이스 수정
        String wantedCompanyUrl = company.generateWantedUrl();
        String wantedJson = get(wantedCompanyUrl);

        // TODO 추후에 url 생성 관련하여 객체화 시킬 수 있을 듯
        String theVcUrl = company.generateTheVcUrl();
        String theVcJson = get(theVcUrl);

        return CompanyDto.of(company).update(wantedJson, theVcJson);
    }
}
