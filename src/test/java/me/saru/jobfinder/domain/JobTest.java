package me.saru.jobfinder.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JobTest {
    @Test
    public void init() {
        Job job = new Job("img", "jobName", 1, "position",
                new Company(1, "companyName"));
        assertThat(job.getImg()).isEqualTo("img");
    }
}
