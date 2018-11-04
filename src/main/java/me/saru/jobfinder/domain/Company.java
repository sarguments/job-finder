package me.saru.jobfinder.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Company {
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


}
