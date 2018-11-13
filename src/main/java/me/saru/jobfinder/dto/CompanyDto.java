package me.saru.jobfinder.dto;

import com.jayway.jsonpath.JsonPath;
import me.saru.jobfinder.domain.Company;

// TODO url 생성을 맵으로?
public class CompanyDto {
    private String theVcUrl;
    private String info;
    private String industryName;
    private String totalLocation;
    private String name;
    private int companyId;

    private CompanyDto(Company company) {
        this.companyId = company.getCompanyId();
        this.name = company.getName();
    }

    // TODO 과도한 of?
    public static CompanyDto of(Company company) {
        return new CompanyDto(company);
    }

    public CompanyDto update(String wantedJson, String theVcJson) {
        this.totalLocation = JsonPath.read(wantedJson, "$.total_location");
        this.industryName = JsonPath.read(wantedJson, "$.industry_name");
        this.info = JsonPath.read(wantedJson, "$.info");
        this.theVcUrl = "https://thevc.kr/" + JsonPath.read(theVcJson, "$.c[0].name_url");

        return this;
    }

    public String getName() {
        return name;
    }

    public int getCompanyId() {
        return companyId;
    }

    public String getTheVcUrl() {
        return theVcUrl;
    }

    public String getInfo() {
        return info;
    }

    public String getIndustryName() {
        return industryName;
    }

    public String getTotalLocation() {
        return totalLocation;
    }
}
