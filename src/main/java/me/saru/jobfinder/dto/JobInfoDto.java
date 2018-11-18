package me.saru.jobfinder.dto;

public class JobInfoDto {
    private String category;
    private Double replyRate;
    private String status;
    private String industryName;
    private int annualFrom;
    private int annualTo;
    private String jd;
    private String shortLink;
    private int companyId;

    public JobInfoDto() {
    }

    public JobInfoDto(Builder builder) {
        this.category = builder.category;
        this.replyRate = builder.replyRate;
        this.status = builder.status;
        this.industryName = builder.industryName;
        this.annualFrom = builder.annualFrom;
        this.annualTo = builder.annualTo;
        this.jd = builder.jd;
        this.shortLink = builder.shortLink;
        this.companyId = builder.companyId;
    }

    public String getCategory() {
        return category;
    }

    public Double getReplyRate() {
        return replyRate;
    }

    public String getStatus() {
        return status;
    }

    public String getIndustryName() {
        return industryName;
    }

    public int getAnnualFrom() {
        return annualFrom;
    }

    public int getAnnualTo() {
        return annualTo;
    }

    public String getJd() {
        return jd;
    }

    public String getShortLink() {
        return shortLink;
    }

    public int getCompanyId() {
        return companyId;
    }

    public static class Builder {
        private String category;
        private Double replyRate;
        private String status;
        private String industryName;
        private int annualFrom;
        private int annualTo;
        private String jd;
        private String shortLink;
        private int companyId;

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder replyRate(Double replyRate) {
            this.replyRate = replyRate;
            return this;
        }


        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder industryName(String industryName) {
            this.industryName = industryName;
            return this;
        }

        public Builder annualFrom(int annualFrom) {
            this.annualFrom = annualFrom;
            return this;
        }

        public Builder annualTo(int annualTo) {
            this.annualTo = annualTo;
            return this;
        }

        public Builder jd(String jd) {
            this.jd = jd;
            return this;
        }

        public Builder shortLink(String shortLink) {
            this.shortLink = shortLink;
            return this;
        }

        public Builder companyId(int companyId) {
            this.companyId = companyId;
            return this;
        }

        public JobInfoDto build() {
            return new JobInfoDto(this);
        }
    }
}
