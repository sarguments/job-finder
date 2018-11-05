package me.saru.jobfinder.domain;

import me.saru.jobfinder.support.UrlGeneratable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Company implements UrlGeneratable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private int companyId;
    private String name;

    public Company() {
    }

    public Company(int companyId, String name) {
        this.companyId = companyId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCompanyId() {
        return companyId;
    }


    @Override
    public String generateUrl() {
        return "https://www.wanted.co.kr/api/v1/companies/" + companyId;
    }
}
