package me.saru.jobfinder.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JobInfoServiceTest {
    @Mock
    private JobService jobService;

    @Test
    public void getTotalSize() {
        when(jobService.getTotalSize()).thenReturn(20);
        int totalSize = jobService.getTotalSize();
        assertThat(totalSize).isEqualTo(20);
    }
}
