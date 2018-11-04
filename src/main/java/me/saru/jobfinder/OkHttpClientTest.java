package me.saru.jobfinder;

import me.saru.jobfinder.scraping.HttpClient;
import me.saru.jobfinder.scraping.Retry;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OkHttpClientTest {
    private static final Logger log = LoggerFactory.getLogger(OkHttpClientTest.class);

    private static final OkHttpClient client = HttpClient.globalClient();

    public static void okHttp() {
        HttpUrl url = HttpUrl.parse("https://www.wanted.co.kr/wdlist/518/872?referer_id=352681");
        Request request = new Request.Builder().url(url).get().build();
        String html = Retry.retryUntilSuccessfulWithBackoff(
                () -> client.newCall(request).execute()
        );

        Element element = Jsoup.parse(html).body();
        log.debug(element.text());
    }

    public static void main(String[] args) {
        okHttp();
    }
}
