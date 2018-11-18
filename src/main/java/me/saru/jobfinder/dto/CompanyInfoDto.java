package me.saru.jobfinder.dto;

public class CompanyInfoDto {
    private String totalLocation;
    private String industryName;
    private String info;

    public CompanyInfoDto() {
    }

    public CompanyInfoDto(String totalLocation, String industryName, String info) {
        this.totalLocation = totalLocation;
        this.industryName = industryName;
        this.info = info;
    }

    public String getTotalLocation() {
        return totalLocation;
    }

    public String getIndustryName() {
        return industryName;
    }

    public String getInfo() {
        return info;
    }
}
