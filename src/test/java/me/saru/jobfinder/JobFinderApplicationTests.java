package me.saru.jobfinder;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class JobFinderApplicationTests {
    private static final int PAGE = 3;
    private static final String WANTED = "www.wanted.co.kr";
    private static final Logger log = LoggerFactory.getLogger(JobFinderApplicationTests.class);
    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void contextLoads() throws IOException {
        // TODO resttemplate?

        /*
        @RequestMapping(method = RequestMethod.POST)
        public ResponseEntity createCustomer(UriComponentsBuilder builder) {
            // implementation
        }
         */

        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("https").host("www.wanted.co.kr")
                .path("/api/v3/search")
                .queryParam("company_sort", "+name")
                .queryParam("locale", "ko-kr")
                .queryParam("country", "KR")
                .queryParam("tag_type_id_distinct", "True")
                .queryParam("years", "-1")
                .queryParam("source", "")
                .queryParam("limit", "20")
                .queryParam("offset", "0")
                .queryParam("job_sort", "-confirm_time")
                .queryParam("query", "")
                .queryParam("langs", "[u'ko',+u'en']")
                .queryParam("tag_type_id", "{tag-tyOpe-id}")
                .buildAndExpand("872").encode();

        String uri = uriComponents.toUriString();
        int page = 1;

        for (int i = 0; i < PAGE; i++) {
            String json = restTemplate.getForObject(uri, String.class);
            int totalSize = JsonPath.read(json, "$.data.jobs.total");
            System.out.println("total : " + totalSize);

            String nextPath = JsonPath.read(json, "$.links.next");
            if (nextPath == null) {
                break;
            }

            uri = UriComponentsBuilder.newInstance().scheme("https").host(WANTED)
                    .path(nextPath)
                    .build()
                    .toUriString();

            JSONArray jsonArray = JsonPath.read(json, "$.data.jobs.data[*]");
            for (Object aJsonArray : jsonArray) {
                Map<String, Object> jobs = (Map<String, Object>) aJsonArray;
                System.out.println(jobs.get("logo_thumb_img"));
                System.out.println("job id : " + jobs.get("id"));
                System.out.println("company id : " + jobs.get("company_id"));
                System.out.println(jobs.get("company_name"));
                System.out.println(jobs.get("position"));
                System.out.println("---------------------------------------------------");
            }

            System.out.println("## page : " + page++);
        }

    }
}
