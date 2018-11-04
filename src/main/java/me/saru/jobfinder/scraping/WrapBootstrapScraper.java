package me.saru.jobfinder.scraping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jooq.lambda.Seq;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class WrapBootstrapScraper {
    private static final Logger log = LoggerFactory.getLogger(WrapBootstrapScraper.class);

    private static final String WRAP_BOOTSTRAP_HOST = "https://wrapbootstrap.com";
    private static final String POPULAR_THEMES_URL =
            "https://wrapbootstrap.com/themes/page.1/sort.sales/order.desc";
    private static final OkHttpClient client = HttpClient.globalClient();

    public static List<HtmlCssTheme> popularThemes() {
        HttpUrl url = HttpUrl.parse(POPULAR_THEMES_URL);
        Request request = new Request.Builder().url(url).get().build();

        // Retry if the request is not successful code >= 200 && code < 300
        String html = Retry.retryUntilSuccessfulWithBackoff(
                () -> client.newCall(request).execute()
        );

        // Select all the elements with the given CSS selector.
        Elements elements = Jsoup.parse(html).select("#themes .item");

        return Seq.seq(elements)
                .map(WrapBootstrapScraper::themeFromElement)
                .toList();
    }

    /*
     * Parse out the data from each Element
     */
    private static HtmlCssTheme themeFromElement(Element element) {
        Element titleElement = element.select(".item_head h2 a").first();
        String title = titleElement.text();
        String url = HttpUrl.parse(WRAP_BOOTSTRAP_HOST + titleElement.attr("href"))
                .newBuilder()
                .build().toString();
        String imageUrl = element.select(".image img").attr("src");
        int downloads = Optional.of(element.select(".item_foot .purchases").text())
                .filter(val -> !Strings.isNullOrEmpty(val))
                .map(Integer::parseInt)
                .orElse(0);
        return new HtmlCssTheme(title, url, imageUrl, downloads);
    }

    /*
     * Main methods everywhere! Very convenient for quick ad hoc
     * testing without spinning up an entire application.
     */
    public static void main(String[] args) throws JsonProcessingException {
        List<HtmlCssTheme> themes = popularThemes();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(themes);
        log.debug(json);
    }
}