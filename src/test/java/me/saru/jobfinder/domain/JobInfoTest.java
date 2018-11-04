package me.saru.jobfinder.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JobInfoTest {
    @Test
    public void init() {
        JobInfo jobInfo = new JobInfo(20, "next");
        assertThat(jobInfo.getTotal()).isEqualTo(20);
    }
}
