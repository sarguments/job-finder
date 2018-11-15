package me.saru.jobfinder.service;

import me.saru.jobfinder.domain.Company;
import me.saru.jobfinder.dto.CompanyDto;
import me.saru.jobfinder.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;
    private ApiScrapper apiScrapper;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, ApiScrapper apiScrapper) {
        this.companyRepository = companyRepository;
        this.apiScrapper = apiScrapper;
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

    public CompanyDto fetchCompanyInfo(Company company) {
        // TODO 인터페이스 수정
        String wantedCompanyUrl = company.generateWantedUrl();
        String wantedJson = apiScrapper.get(wantedCompanyUrl);

        // TODO 추후에 url 생성 관련하여 객체화 시킬 수 있을 듯
        String theVcUrl = company.generateTheVcUrl();
        String theVcJson = apiScrapper.get(theVcUrl);

        return CompanyDto.of(company).update(wantedJson, theVcJson);
    }
}
