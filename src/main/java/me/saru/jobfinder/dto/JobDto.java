package me.saru.jobfinder.dto;

import me.saru.jobfinder.domain.Company;
import me.saru.jobfinder.domain.Job;

public class JobDto {
    private Long id;
    private String img;
    private int jobId;
    private String position;
    private Company company;

    public JobDto(Job job) {
        this.id = job.getId();
        this.img = job.getImg();
        this.jobId = job.getJobId();
        this.position = job.getPosition();
        this.company = job.getCompany();
    }

    public Long getId() {
        return id;
    }

    public String getImg() {
        return img;
    }

    public int getJobId() {
        return jobId;
    }

    public String getPosition() {
        return position;
    }

    public Company getCompany() {
        return company;
    }
}
