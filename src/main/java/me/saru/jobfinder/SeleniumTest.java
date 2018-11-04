package me.saru.jobfinder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeleniumTest {
    private static final Logger log = LoggerFactory.getLogger(SeleniumTest.class);

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "/usr/lib/chromium-browser/chromedriver");
        WebDriver driver = new ChromeDriver();

        // And now use this to visit Google
        driver.get("https://www.wanted.co.kr/wdlist/518/872?referer_id=352681");
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.google.com");

        // Check the title of the page
        System.out.println("Page title is: " + driver.getTitle());

        // Google's search is rendered dynamically with JavaScript.
        // Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10))
                .until((ExpectedCondition<Boolean>) d -> d.getTitle()
                        .toLowerCase()
                        .startsWith("'개발"));

        Document doc = Jsoup.parse(driver.getPageSource());
        Elements elements = doc.getElementsByClass("jobList");
        log.debug(elements.text());

        //Close the browser
        driver.quit();
    }
}
