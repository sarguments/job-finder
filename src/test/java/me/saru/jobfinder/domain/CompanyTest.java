package me.saru.jobfinder.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CompanyTest {
    @Test
    public void urlGenerateTest() {
        Company company = new Company(2, "company");
        assertThat(company.generateWantedUrl()).isEqualTo("https://www.wanted.co.kr/api/v1/companies/2");
        assertThat(company.generateTheVcUrl()).isEqualTo("https://thevc.kr/search?word=company");
        assertThat(company.generateRocketUrl()).isEqualTo("https://www.rocketpunch.com/suggest?q=company");
    }
}
