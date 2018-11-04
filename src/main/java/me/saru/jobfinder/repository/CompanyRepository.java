package me.saru.jobfinder.repository;

import me.saru.jobfinder.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
