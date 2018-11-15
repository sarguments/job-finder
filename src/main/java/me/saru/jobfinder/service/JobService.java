package me.saru.jobfinder.service;

import me.saru.jobfinder.domain.Company;
import me.saru.jobfinder.domain.Job;
import me.saru.jobfinder.dto.JobDto;
import me.saru.jobfinder.repository.CompanyRepository;
import me.saru.jobfinder.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {
    private CompanyRepository companyRepository;
    private JobRepository jobRepository;
    private ApiScrapper apiScrapper;

    @Autowired
    public JobService(CompanyRepository companyRepository, JobRepository jobRepository,
                      ApiScrapper apiScrapper) {
        this.companyRepository = companyRepository;
        this.jobRepository = jobRepository;
        this.apiScrapper = apiScrapper;
    }

    public List<Job> findAllJobByCompanyId(int companyId) {
        Company company = companyRepository.findByCompanyId(companyId);
        return jobRepository.findAllByCompanyId(company.getId());
    }

    public Job findByJobId(int jobId) {
        return jobRepository.findByJobId(jobId);
    }

    public List<JobDto> findAllJob() {
        List<Job> jobs = jobRepository.findAll();
        return jobsToDtos(jobs);
    }

    public List<JobDto> jobsToDtos(List<Job> jobs) {
        List<JobDto> jobDtos = new ArrayList<>();
        for (Job job : jobs) {
            jobDtos.add(new JobDto(job));
        }

        return jobDtos;
    }

    public Job saveJob(Job job) {
        return jobRepository.save(job);
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
        return apiScrapper.get(uri);
    }

    public String fetchNextJob(String next) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("https").host("www.wanted.co.kr")
                .path(next)
                .build();

        String uri = uriComponents.toUriString();
        return apiScrapper.get(uri);
    }

    public String fetchJobs(int jobId) {
        String uri = "https://www.wanted.co.kr/api/v1/jobs/" + jobId;
        return apiScrapper.get(uri);
    }
}
