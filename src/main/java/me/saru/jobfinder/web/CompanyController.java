package me.saru.jobfinder.web;

import me.saru.jobfinder.domain.Company;
import me.saru.jobfinder.domain.Job;
import me.saru.jobfinder.dto.CompanyDto;
import me.saru.jobfinder.dto.JobDto;
import me.saru.jobfinder.service.CompanyService;
import me.saru.jobfinder.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CompanyController {
    private JobService jobService;
    private CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService, JobService jobService) {
        this.companyService = companyService;
        this.jobService = jobService;
    }

    @GetMapping("/companies/{companyId}/jobs")
    public List<JobDto> jobListByCompany(@PathVariable int companyId) {
        List<Job> jobs = jobService.findAllJobByCompanyId(companyId);
        return jobService.jobsToDtos(jobs);
    }

    @GetMapping("/companies/{companyId}")
    public CompanyDto jobInfoByCompany(@PathVariable int companyId) {
        Company company = companyService.findByCompanyId(companyId);
        return companyService.fetchCompanyInfo(company);
    }
}
