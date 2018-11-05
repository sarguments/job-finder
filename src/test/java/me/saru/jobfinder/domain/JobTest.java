package me.saru.jobfinder.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JobTest {
    @Test
    public void init() {
        Job job = new Job("img", 1, "position",
                new Company(1, "companyName"));
        assertThat(job.getImg()).isEqualTo("img");
    }

    @Test
    public void urlGenerateTest() {
        Job job = new Job("img", 1, "position",
                new Company(1, "companyName"));
        assertThat(job.generateUrl()).isEqualTo("https://www.wanted.co.kr/api/v1/jobs/1");
    }
}
