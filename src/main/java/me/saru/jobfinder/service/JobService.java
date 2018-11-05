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

import java.util.List;
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

    public int saveJobAndCompany(String json) {
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
        saveJobAndCompanyProcess(jsonArray);

        return jobRepository.findAll().size();
    }

    private void saveJobAndCompanyProcess(JSONArray jsonArray) {
        for (Object aJsonArray : jsonArray) {
            Map<String, Object> jobs = (Map<String, Object>) aJsonArray;

            Company company = new Company((Integer) jobs.get("company_id"), (String) jobs.get("company_name"));
            Company returnedCompany = findCompany(company);
            if (returnedCompany == null) {
                returnedCompany = companyRepository.save(company);
            }

            Job job = new Job((String) jobs.get("logo_thumb_img"),
                    (Integer) jobs.get("id"),
                    (String) jobs.get("position"));
            job.setCompany(returnedCompany);

            jobRepository.save(job);
        }
    }

    // TODO company?
    public Company saveCompany(Company company) {
        Company returnedCompany = findCompany(company);
        if (returnedCompany != null) {
            return new Company.DuplicateCompany();
        }

        return companyRepository.save(company);
    }

    private Company findCompany(Company company) {
        return companyRepository.findByCompanyId(company.getCompanyId());
    }

    public List<Job> findAllJobByCompanyId(int companyId) {
        Company company = companyRepository.findByCompanyId(companyId);
        return jobRepository.findAllByCompanyId(company.getId());
    }

    public Job findByJobId(int jobId) {
        return jobRepository.findByJobId(jobId);
    }
}
