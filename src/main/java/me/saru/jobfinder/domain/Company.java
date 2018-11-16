package me.saru.jobfinder.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// TODO founded_year 추가 안되는 문제
@Entity
public class Company {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private int companyId;
    private String name;

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    private List<Job> jobs = new ArrayList<>();

    public Company() {
    }

    public Company(int companyId, String name) {
        this.companyId = companyId;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCompanyId() {
        return companyId;
    }

    // TODO set을 쓰는건 다시 고려
    public void addJob(Job job) {
        this.jobs.add(job);
        if (job.getCompany() != this) {
            job.setCompany(this);
        }
    }

    @JsonIgnore
    public boolean isDuplicate() {
        return false;
    }

    // TODO get?
    public List<Job> getJobs() {
        return jobs;
    }

    public String generateWantedUrl() {
        return "https://www.wanted.co.kr/api/v1/companies/" + companyId;
    }

    public String generateTheVcUrl() {
        return "https://thevc.kr/search?word=" + name;
    }

    public String generateRocketUrl() {
        return "https://www.rocketpunch.com/suggest?q=" + name;
    }

    public static class DuplicateCompany extends Company {

        @Override
        public boolean isDuplicate() {
            return true;
        }
    }
}
