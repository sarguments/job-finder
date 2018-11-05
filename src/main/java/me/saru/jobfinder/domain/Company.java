package me.saru.jobfinder.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import me.saru.jobfinder.support.UrlGeneratable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Company implements UrlGeneratable {
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


    public void addJob(Job job) {
        this.jobs.add(job);
        if (job.getCompany() != this) {
            job.setCompany(this);
        }
    }

    @Override
    public String generateUrl() {
        return "https://www.wanted.co.kr/api/v1/companies/" + companyId;
    }

    public boolean isDuplicate() {
        return false;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public static class DuplicateCompany extends Company {
        @Override
        public boolean isDuplicate() {
            return true;
        }
    }
}
