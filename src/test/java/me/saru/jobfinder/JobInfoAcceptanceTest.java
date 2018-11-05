package me.saru.jobfinder;

import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class JobInfoAcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(JobInfoAcceptanceTest.class);

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void jobInfoTest() {
        when()
                .get("/info")
                .then()
                .statusCode(200)
                .body("total", greaterThan(300));
    }

    @Test
    public void jobUpdateTest() {
        ResponseEntity<String> entity = restTemplate.getForEntity("/info", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        String next = JsonPath.read(entity.getBody(), "$.next");

        when()
                .get("/update")
                .then()
                .statusCode(200)
                .body("next", not(next));
    }
}
