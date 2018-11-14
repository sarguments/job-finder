package me.saru.jobfinder.domain;

import me.saru.jobfinder.service.ApiScrapper;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiScrapperTest {
    private ApiScrapper apiScrapper = new ApiScrapper(new RestTemplate());

    @Test
    public void init() {
        String result = apiScrapper.get("https://google.com");
        assertThat(result).isNotNull();
    }
}
