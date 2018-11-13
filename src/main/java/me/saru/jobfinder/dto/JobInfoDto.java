package me.saru.jobfinder.dto;

import com.jayway.jsonpath.JsonPath;

// TODO Dto가 아니라 JobInfo 객체에서 파싱을 하자
public class JobInfoDto {
    private String category;
    private Double replyRate;
    private String status;
    private String industryName;
    private int annualFrom;
    private int annualTo;
    private String jd;
    private String shortLink;

    public JobInfoDto() {
    }

    public static JobInfoDto of(String json) {
        String category = JsonPath.read(json, "$.category");
        Double replyRate = JsonPath.read(json, "$.reply_rate");
        String status = JsonPath.read(json, "$.status");
        String industryName = JsonPath.read(json, "$.industry_name");
        int annualFrom = JsonPath.read(json, "$.annual_from");
        int annualTo = JsonPath.read(json, "$.annual_to");
        String jd = JsonPath.read(json, "$.jd");
        String shortLink = JsonPath.read(json, "$.short_link");

        return new JobInfoDto().setCategory(category)
                .setReplyRate(replyRate)
                .setStatus(status)
                .setIndustryName(industryName)
                .setAnnualFrom(annualFrom)
                .setAnnualTo(annualTo)
                .setJd(jd)
                .setShortLink(shortLink);
    }

    public String getCategory() {
        return category;
    }

    public JobInfoDto setCategory(String category) {
        this.category = category;
        return this;
    }

    public Double getReplyRate() {
        return replyRate;
    }

    public JobInfoDto setReplyRate(Double replyRate) {
        this.replyRate = replyRate;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public JobInfoDto setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getIndustryName() {
        return industryName;
    }

    public JobInfoDto setIndustryName(String industryName) {
        this.industryName = industryName;
        return this;
    }

    public int getAnnualFrom() {
        return annualFrom;
    }

    public JobInfoDto setAnnualFrom(int annualFrom) {
        this.annualFrom = annualFrom;
        return this;
    }

    public int getAnnualTo() {
        return annualTo;
    }

    public JobInfoDto setAnnualTo(int annualTo) {
        this.annualTo = annualTo;
        return this;
    }

    public String getJd() {
        return jd;
    }

    public JobInfoDto setJd(String jd) {
        this.jd = jd;
        return this;
    }

    public String getShortLink() {
        return shortLink;
    }

    public JobInfoDto setShortLink(String shortLink) {
        this.shortLink = shortLink;
        return this;
    }
}
