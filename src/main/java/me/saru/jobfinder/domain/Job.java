package me.saru.jobfinder.domain;

import javax.persistence.*;

@Entity
public class Job {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String img;
    private int jobId;
    private String position;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_job_to_company"))
    private Company company;

    public Job() {
    }

    public Job(String img, int jobId, String position, Company company) {
        this.img = img;
        this.jobId = jobId;
        this.position = position;
        this.company = company;
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
