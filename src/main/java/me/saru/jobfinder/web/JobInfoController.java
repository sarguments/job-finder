package me.saru.jobfinder.web;

import me.saru.jobfinder.domain.Company;
import me.saru.jobfinder.domain.Job;
import me.saru.jobfinder.domain.TotalJobInfo;
import me.saru.jobfinder.dto.CompanyDto;
import me.saru.jobfinder.dto.JobDto;
import me.saru.jobfinder.dto.JobInfoDto;
import me.saru.jobfinder.service.ApiScrapper;
import me.saru.jobfinder.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JobInfoController {
    private JobService jobService;
    private ApiScrapper apiScrapper;

    @Autowired
    public JobInfoController(JobService jobService, ApiScrapper apiScrapper) {
        this.jobService = jobService;
        this.apiScrapper = apiScrapper;
    }

    @GetMapping("/info")
    public TotalJobInfo jobSave() {
        String json = apiScrapper.fetchWantedJson();

        // TODO size test
        // TODO 끝일 경우 -1
        jobService.saveJobAndCompany(json);

        return TotalJobInfo.getInstance();
    }

    private void updateProcess() {
        String next = TotalJobInfo.getInstance().getNext();
        String json = apiScrapper.fetchNextJob(next);

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
        String json = apiScrapper.fetchJobs(jobId);

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
        return jobService.fetchCompanyInfo(company);
    }
}
