package me.saru.jobfinder;

import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.junit.runner.RunWith;
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
                .body("[0].company.companyId", is(15));
    }

    @Test
    public void jobInfoByCompanyTest() {
        when()
                .get("/companies/15/jobs")
                .then()
                .statusCode(200)
                .log().all()
                .body("[0].company.companyId", is(15));
    }

    @Test
    public void companyInfoTest() {
        when()
                .get("/companies/15")
                .then()
                .statusCode(200)
                .log().all()
                .body("totalLocation", is("서울특별시 강남구 강남대로 364(역삼동), 미왕빌딩 15층"))
                .body("industryName", is("여행, 호텔, 항공"))
                .body("info", startsWith("마이리얼트립은 한국 최고의 자유여행 플랫폼 회사로서"));
    }

    @Test
    public void companyInfoTheVcTest() {
        when()
                .get("/companies/15")
                .then()
                .statusCode(200)
                .log().all()
                .body("theVcUrl", is("https://thevc.kr/MyRealTrip"));
    }

    @Test
    public void companyInfoRocketTest() {
        when()
                .get("/companies/15")
                .then()
                .statusCode(200)
                .log().all()
                .body("rocketUrl", is("https://www.rocketpunch.com/companies/myrealtrip"));
    }
}
