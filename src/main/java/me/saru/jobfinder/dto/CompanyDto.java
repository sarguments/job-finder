package me.saru.jobfinder.dto;

import me.saru.jobfinder.domain.Company;

// TODO url 생성을 맵으로?
public class CompanyDto {
    private String theVcUrl;
    private String info;
    private String industryName;
    private String totalLocation;
    private String name;
    private int companyId;
    private String rocketUrl;

    private CompanyDto(Company company) {
        this.companyId = company.getCompanyId();
        this.name = company.getName();
    }

    // TODO 과도한 of?
    public static CompanyDto of(Company company) {
        return new CompanyDto(company);
    }

    public CompanyDto update(CompanyDetailDto companyDetailDto, String theVcUrl, String rocketUrl) {
        this.totalLocation = companyDetailDto.getTotalLocation();
        this.industryName = companyDetailDto.getIndustryName();
        this.info = companyDetailDto.getInfo();

        if (!checkNotFound(theVcUrl)) {
            this.theVcUrl = theVcUrl;
        }

        if (!checkNotFound(rocketUrl)) {
            this.rocketUrl = rocketUrl;
        }

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

    public String getRocketUrl() {
        return rocketUrl;
    }

    private boolean checkNotFound(String param) {
        return param.equals("NOT_FOUND");

    }
}
