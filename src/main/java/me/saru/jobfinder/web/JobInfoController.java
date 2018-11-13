package me.saru.jobfinder.web;

import me.saru.jobfinder.domain.Company;
import me.saru.jobfinder.domain.Job;
import me.saru.jobfinder.domain.TotalJobInfo;
import me.saru.jobfinder.dto.CompanyDto;
import me.saru.jobfinder.dto.JobDto;
import me.saru.jobfinder.dto.JobInfoDto;
import me.saru.jobfinder.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

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
    public TotalJobInfo jobSave() {
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

        return TotalJobInfo.getInstance();
    }

    private void updateProcess() {
        String next = TotalJobInfo.getInstance().getNext();
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
    public TotalJobInfo jobUpdateNumber(@RequestParam int number) {
        for (int i = 0; i < number; i++) {
            updateProcess();
        }

        return TotalJobInfo.getInstance();
    }

    @GetMapping("/jobs/{jobId}")
    public JobInfoDto jobInfo(@PathVariable int jobId) {
        String uri = "https://www.wanted.co.kr/api/v1/jobs/" + jobId;
        String json = restTemplate.getForObject(uri, String.class);

        // TODO dto에서 바로 이렇게 해도 되나?
        return JobInfoDto.of(json);
    }

    @GetMapping("/show")
    public List<JobDto> jobInfos() {
        return jobService.findAllJob();
    }

    // TODO 컨트롤러 따로 나누자
    @GetMapping("/companies/{companyId}/jobs")
    public List<JobDto> jobListByCompany(@PathVariable int companyId) {
        List<Job> jobs = jobService.findAllJobByCompanyId(companyId);
        return jobService.jobsToDtos(jobs);
    }

    @GetMapping("/companies/{companyId}")
    public CompanyDto jobInfoByCompany(@PathVariable int companyId) {
        Company company = jobService.findByCompanyId(companyId);

        // TODO 서비스에서 호출해서 정보 받아온다음 dto에 넣어서 리턴
        return CompanyDto.of(company);
    }
}
