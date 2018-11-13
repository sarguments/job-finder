package me.saru.jobfinder.service;

import me.saru.jobfinder.domain.Company;
import me.saru.jobfinder.domain.Job;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@RunWith(MockitoJUnitRunner.class)
public class JobServiceTest {
    //    @Mock
    @Autowired
    private JobService jobService;

    @Test
    public void duplicateCompany() {
        Company company = new Company(3, "company3");
        Company returnedCompany = jobService.saveCompany(company);
        assertThat(returnedCompany.isDuplicate()).isTrue();
    }

    @Test
    public void findAllJobByCompany() {
        List<Job> jobs = jobService.findAllJobByCompanyId(15);
        assertThat(jobs.size()).isEqualTo(2);
    }

    @Test
    public void findByJobId() {
        Job job = jobService.findByJobId(2);
        assertThat(job.getImg()).isEqualTo("img2");
    }
}
