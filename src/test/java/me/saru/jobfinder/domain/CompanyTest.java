package me.saru.jobfinder.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CompanyTest {
    @Test
    public void urlGenerateTest() {
        Company company = new Company(2, "company");
        assertThat(company.generateUrl()).isEqualTo("https://www.wanted.co.kr/api/v1/companies/2");
    }
}
