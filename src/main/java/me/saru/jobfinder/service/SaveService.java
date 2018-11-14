package me.saru.jobfinder.service;

import com.jayway.jsonpath.JsonPath;
import me.saru.jobfinder.domain.Company;
import me.saru.jobfinder.domain.Job;
import me.saru.jobfinder.domain.TotalJobInfo;
import net.minidev.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SaveService {
    private static final Logger log = LoggerFactory.getLogger(SaveService.class);
    private ApiScrapper apiScrapper;
    private CompanyService companyService;
    private JobService jobService;

    @Autowired
    public SaveService(ApiScrapper apiScrapper, CompanyService companyService, JobService jobService) {
        this.apiScrapper = apiScrapper;
        this.companyService = companyService;
        this.jobService = jobService;
    }

    public boolean saveJobAndCompany(String json) {
        int totalSize = JsonPath.read(json, "$.data.jobs.total");

        String next = JsonPath.read(json, "$.links.next");
        if (next == null) {
            return false;
        }

        // TODO 잡의 경우도 중복 체크 해야함
        TotalJobInfo totalJobInfo = TotalJobInfo.getInstance();
        totalJobInfo.updateInfo(totalSize, next);

        JSONArray jsonArray = JsonPath.read(json, "$.data.jobs.data[*]");
        saveJobAndCompanyProcess(jsonArray);

        return true;
    }

    private void saveJobAndCompanyProcess(JSONArray jsonArray) {
        for (Object aJsonArray : jsonArray) {
            Map<String, Object> jobs = (Map<String, Object>) aJsonArray;
            log.info("company_name : {}", jobs.get("company_name"));
            log.info("position : {}", jobs.get("position"));
            log.info("---------------------------------------");

            Company company = new Company((Integer) jobs.get("company_id"), (String) jobs.get("company_name"));
            Company returnedCompany = companyService.findCompany(company);
            if (returnedCompany == null) {
                returnedCompany = companyService.saveCompany(company);
            }

            Job job = new Job((String) jobs.get("logo_thumb_img"),
                    (Integer) jobs.get("id"),
                    (String) jobs.get("position"));
            job.setCompany(returnedCompany);

            jobService.saveJob(job);
        }
    }
}
