package me.saru.jobfinder.service;

import com.jayway.jsonpath.JsonPath;
import me.saru.jobfinder.domain.Company;
import me.saru.jobfinder.domain.Job;
import me.saru.jobfinder.domain.TotalJobInfo;
import me.saru.jobfinder.dto.CompanyDto;
import me.saru.jobfinder.dto.JobDto;
import me.saru.jobfinder.repository.CompanyRepository;
import me.saru.jobfinder.repository.JobRepository;
import net.minidev.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class JobService {
    private static final Logger log = LoggerFactory.getLogger(JobService.class);
    private ApiScrapper apiScrapper;

    private JobRepository jobRepository;
    private CompanyRepository companyRepository;

    @Autowired
    public JobService(ApiScrapper apiScrapper, JobRepository jobRepository, CompanyRepository companyRepository) {
        this.apiScrapper = apiScrapper;
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
    }

    public int saveJobAndCompany(String json) {
        int totalSize = JsonPath.read(json, "$.data.jobs.total");

        String next = JsonPath.read(json, "$.links.next");
        if (next == null) {
            return -1;
        }

        // TODO 잡의 경우도 중복 체크 해야함
        TotalJobInfo totalJobInfo = TotalJobInfo.getInstance();
        totalJobInfo.updateInfo(totalSize, next);

        JSONArray jsonArray = JsonPath.read(json, "$.data.jobs.data[*]");
        saveJobAndCompanyProcess(jsonArray);

        return jobRepository.findAll().size();
    }

    private void saveJobAndCompanyProcess(JSONArray jsonArray) {
        for (Object aJsonArray : jsonArray) {
            Map<String, Object> jobs = (Map<String, Object>) aJsonArray;
            log.info("company_name : {}", jobs.get("company_name"));
            log.info("position : {}", jobs.get("position"));
            log.info("---------------------------------------");

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

    public Company findByCompanyId(int companyId) {
        return companyRepository.findByCompanyId(companyId);
    }

    public CompanyDto fetchCompanyInfo(Company company) {
        // TODO 인터페이스 수정
        String wantedCompanyUrl = company.generateUrl();
        String wantedJson = apiScrapper.get(wantedCompanyUrl);

        // TODO 추후에 url 생성 관련하여 객체화 시킬 수 있을 듯
        String theVcUrl = company.generateTheVcUrl();
        String theVcJson = apiScrapper.get(theVcUrl);

        return CompanyDto.of(company).update(wantedJson, theVcJson);
    }
}
