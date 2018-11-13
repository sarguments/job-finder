package me.saru.jobfinder.domain;

import me.saru.jobfinder.dto.JobDto;
import me.saru.jobfinder.support.UrlGeneratable;

import javax.persistence.*;

@Entity
public class Job implements UrlGeneratable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String img;
    // TODO id를 다른 이름으로 바꿔야 할지?
    private int jobId;
    private String position;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_job_to_company"))
    private Company company;

    public Job() {
    }

    public Job(String img, int jobId, String position) {
        this.img = img;
        this.jobId = jobId;
        this.position = position;
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

    @Override
    public String generateUrl() {
        return "https://www.wanted.co.kr/api/v1/jobs/" + jobId;
    }

    public void setCompany(Company company) {
        this.company = company;

        if (!company.getJobs().contains(this)) {
            company.getJobs().add(this);
        }
    }

    public JobDto toDto() {
        return new JobDto(this);
    }
}
