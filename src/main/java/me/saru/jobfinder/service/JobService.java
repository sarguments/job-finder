package me.saru.jobfinder.service;

import me.saru.jobfinder.domain.Company;
import me.saru.jobfinder.domain.Job;
import me.saru.jobfinder.dto.JobDto;
import me.saru.jobfinder.repository.CompanyRepository;
import me.saru.jobfinder.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {
    private CompanyRepository companyRepository;
    private JobRepository jobRepository;

    @Autowired
    public JobService(CompanyRepository companyRepository, JobRepository jobRepository) {
        this.companyRepository = companyRepository;
        this.jobRepository = jobRepository;
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
}
