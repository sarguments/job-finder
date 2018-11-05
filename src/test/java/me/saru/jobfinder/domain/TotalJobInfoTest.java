package me.saru.jobfinder.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TotalJobInfoTest {
    @Test
    public void init() {
        TotalJobInfo totalJobInfo = TotalJobInfo.getInstance();
        totalJobInfo.updateInfo(20, "next");
        assertThat(totalJobInfo.getTotal()).isEqualTo(20);
    }
}
