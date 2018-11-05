package me.saru.jobfinder.repository;

import me.saru.jobfinder.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findAllByCompanyId(Long id);

    Job findByJobId(int jobId);
}
