package me.saru.jobfinder.dto;

import me.saru.jobfinder.domain.Company;

// TODO url 생성을 맵으로?
public class CompanyDto {

    private String theVcUrl;
    private String wantedCompanyUrl;
    private String name;
    private int companyId;

    private CompanyDto(Company company) {
        this.companyId = company.getCompanyId();
        this.name = company.getName();

        this.wantedCompanyUrl = company.generateUrl();
        //name, industry_name, location, info

        // TODO 추후에 url 생성 관련하여 객체화 시킬 수 있을 듯
        this.theVcUrl = "https://thevc.kr/search?word=" + name;
        //c[0].name_url
    }

    // TODO 과도한 of?
    public static CompanyDto of(Company company) {
        return new CompanyDto(company);
    }

    public String getTheVcUrl() {
        return theVcUrl;
    }

    public String getWantedCompanyUrl() {
        return wantedCompanyUrl;
    }

    public String getName() {
        return name;
    }

    public int getCompanyId() {
        return companyId;
    }
}
