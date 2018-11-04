package me.saru.jobfinder.repository;

import me.saru.jobfinder.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
