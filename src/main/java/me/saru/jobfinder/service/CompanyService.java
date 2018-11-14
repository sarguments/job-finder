package me.saru.jobfinder.service;

import me.saru.jobfinder.domain.Company;
import me.saru.jobfinder.repository.CompanyRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    // TODO company?
    public Company saveCompany(Company company) {
        Company returnedCompany = findCompany(company);
        if (returnedCompany != null) {
            return new Company.DuplicateCompany();
        }

        return companyRepository.save(company);
    }

    public Company findCompany(Company company) {
        return companyRepository.findByCompanyId(company.getCompanyId());
    }

    public Company findByCompanyId(int companyId) {
        return companyRepository.findByCompanyId(companyId);
    }

}
