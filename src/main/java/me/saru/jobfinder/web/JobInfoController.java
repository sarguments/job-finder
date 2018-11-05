package me.saru.jobfinder.web;

import me.saru.jobfinder.domain.JobInfo;
import me.saru.jobfinder.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class JobInfoController {
    private JobService jobService;
    // TODO new?
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public JobInfoController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/info")
    public JobInfo jobCount() {
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
        String json = restTemplate.getForObject(uri, String.class);

        // TODO size test
        // TODO 끝일 경우 -1
        jobService.saveJobAndCompany(json);

        return JobInfo.getInstance();
    }

    // TOOD 먼저 호출할 경우 예외처리
    // TODO get?
    @GetMapping("/update")
    public JobInfo jobUpdate() {
        updateProcess();

        return JobInfo.getInstance();
    }

    private void updateProcess() {
        String next = JobInfo.getInstance().getNext();
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("https").host("www.wanted.co.kr")
                .path(next)
                .build();

        String uri = uriComponents.toUriString();
        String json = restTemplate.getForObject(uri, String.class);

        // TODO size test
        // TODO 끝일 경우 -1
        jobService.saveJobAndCompany(json);
    }

    @GetMapping(value = "/update", params = "number")
    public JobInfo jobUpdateNumber(@RequestParam int number) {
        for (int i = 0; i < number; i++) {
            updateProcess();
        }

        return JobInfo.getInstance();
    }
}
