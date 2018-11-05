package me.saru.jobfinder.service;

import com.jayway.jsonpath.JsonPath;
import me.saru.jobfinder.domain.Company;
import me.saru.jobfinder.domain.Job;
import me.saru.jobfinder.domain.JobInfo;
import me.saru.jobfinder.repository.CompanyRepository;
import me.saru.jobfinder.repository.JobRepository;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JobService {
    private JobRepository jobRepository;
    private CompanyRepository companyRepository;

    public JobService() {
    }

    @Autowired
    public JobService(JobRepository jobRepository, CompanyRepository companyRepository) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
    }

    public int getTotalSize() {
        return jobRepository.findAll().size();
    }

    public int saveJobs(String json) {
        int totalSize = JsonPath.read(json, "$.data.jobs.total");
        System.out.println("total : " + totalSize);

        String next = JsonPath.read(json, "$.links.next");
        if (next == null) {
            return -1;
        }

        // TODO 잡의 경우도 중복 체크 해야함
        JobInfo jobInfo = JobInfo.getInstance();
        jobInfo.updateInfo(totalSize, next);

        JSONArray jsonArray = JsonPath.read(json, "$.data.jobs.data[*]");
        for (Object aJsonArray : jsonArray) {
            Map<String, Object> jobs = (Map<String, Object>) aJsonArray;

            Company company = new Company((Integer) jobs.get("company_id"), (String) jobs.get("company_name"));
            Company returnedCompany = companyRepository.findByCompanyId(company.getCompanyId());
            if (returnedCompany == null) {
                returnedCompany = companyRepository.save(company);
            }

            jobRepository.save(new Job((String) jobs.get("logo_thumb_img"),
                    (Integer) jobs.get("id"),
                    (String) jobs.get("position"),
                    returnedCompany));
        }

        return jobRepository.findAll().size();
    }

    // TODO company?
    public Company saveCompany(Company company) {
        Company returnedCompany = companyRepository.findByCompanyId(company.getCompanyId());
        if (returnedCompany != null) {
            // TODO 추후 커스텀 에러로 변경, advice 적용?
            throw new IllegalArgumentException();
        }

        return companyRepository.save(company);
    }

    public Job saveJobs(Job job) {
        return jobRepository.save(job);
    }
}
