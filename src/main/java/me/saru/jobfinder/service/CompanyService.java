package me.saru.jobfinder.service;

import com.jayway.jsonpath.JsonPath;
import me.saru.jobfinder.domain.Company;
import me.saru.jobfinder.dto.CompanyDetailDto;
import me.saru.jobfinder.dto.CompanyDto;
import me.saru.jobfinder.repository.CompanyRepository;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CompanyService {
    private static final String NOT_FOUND = "NOT_FOUND";

    private CompanyRepository companyRepository;
    private ApiScrapper apiScrapper;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, ApiScrapper apiScrapper) {
        this.companyRepository = companyRepository;
        this.apiScrapper = apiScrapper;
    }

    // TODO company?
    public Company saveCompany(Company company) {
        Company returnedCompany = findCompany(company);
        if (returnedCompany != null) {
            return new Company.DuplicateCompany();
        }

        return companyRepository.save(company);
    }

    public Company findCompany(Company company) {
        return companyRepository.findByCompanyId(company.getCompanyId());
    }

    public Company findByCompanyId(int companyId) {
        return companyRepository.findByCompanyId(companyId);
    }

    public CompanyDto fetchCompanyInfo(Company company) {
        // TODO 인터페이스 수정
        String wantedCompanyUrl = company.generateWantedUrl();
        String wantedJson = apiScrapper.get(wantedCompanyUrl);

        // TODO 추후에 url 생성 관련하여 객체화 시킬 수 있을 듯
        String theVcUrl = company.generateTheVcUrl();
        String theVcJson = apiScrapper.get(theVcUrl);

        String rocketUrl = company.generateRocketUrl();
        String rocketJson = apiScrapper.get(rocketUrl);

        return CompanyDto.of(company)
                .update(extractWantedJson(wantedJson),
                        extractTheVcUrl(theVcJson),
                        extractRocketUrl(rocketJson));
    }

    private CompanyDetailDto extractWantedJson(String wantedJson) {
        String totalLocation = JsonPath.read(wantedJson, "$.total_location");
        String industryName = JsonPath.read(wantedJson, "$.industry_name");
        String info = JsonPath.read(wantedJson, "$.info");

        return new CompanyDetailDto(totalLocation, industryName, info);
    }

    private String extractTheVcUrl(String theVcJson) {
        JSONArray jsonArray = JsonPath.read(theVcJson, "$.c[*]");
        if (jsonArray.isEmpty()) {
            return NOT_FOUND;
        }

        return "https://thevc.kr/" + JsonPath.read(theVcJson, "$.c[0].name_url");
    }

    private String extractRocketUrl(String rocketJson) {
        JSONArray jsonArray = JsonPath.read(rocketJson,
                "$.results[*]");
        for (Object aJsonArray : jsonArray) {
            Map<String, Object> jobs = (Map<String, Object>) aJsonArray;
            if (jobs.get("object_type").equals("company")) {
                return "https://www.rocketpunch.com" + jobs.get("link");
            }
        }

        return NOT_FOUND;
    }
}
