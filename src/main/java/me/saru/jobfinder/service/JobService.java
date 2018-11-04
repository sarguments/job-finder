package me.saru.jobfinder.service;

import com.jayway.jsonpath.JsonPath;
import me.saru.jobfinder.domain.Company;
import me.saru.jobfinder.domain.Job;
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

        JSONArray jsonArray = JsonPath.read(json, "$.data.jobs.data[*]");
        for (Object aJsonArray : jsonArray) {
            Map<String, Object> jobs = (Map<String, Object>) aJsonArray;

            Company company = new Company((Integer) jobs.get("company_id"), (String) jobs.get("company_name"));
            companyRepository.save(company);

            jobRepository.save(new Job((String) jobs.get("logo_thumb_img"),
                    (String) jobs.get("company_name"),
                    (Integer) jobs.get("id"),
                    (String) jobs.get("position"),
                    company));
        }

        // TODO getSize
        return jobRepository.findAll().size();
    }
}
