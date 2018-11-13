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
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TotalJobInfoAcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(TotalJobInfoAcceptanceTest.class);

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void jobListInitTest() {
        when()
                .get("/info")
                .then()
                .statusCode(200)
                .log().all()
                .body("total", greaterThan(300));
    }

    @Test
    public void jobUpdateTest() {
        ResponseEntity<String> entity = restTemplate.getForEntity("/info", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        String next = JsonPath.read(entity.getBody(), "$.next");

        when()
                .get("/update?number=1")
                .then()
                .statusCode(200)
                .log().all()
                .body("next", not(next));
    }

    @Test
    public void jobInfoTest() {
        when()
                .get("/jobs/11032")
                .then()
                .statusCode(200)
                .log().all()
                .body("jd", startsWith("마이리얼트립"));
    }

    @Test
    public void jobShowListTest() {
        when()
                .get("/show")
                .then()
                .statusCode(200)
                .log().all()
                .body("[0].id", is(1));
    }

    @Test
    public void jobInfoByCompanyTest() {
        when()
                .get("/companies/15/jobs")
                .then()
                .statusCode(200)
                .log().all()
                .body("[0].company.id", is(15));
    }

    @Test
    public void companyInfoTest() {
        when()
                .get("/companies/15")
                .then()
                .statusCode(200)
                .log().all()
                .body("name", is("마이리얼트립"));
    }
}
