package me.saru.jobfinder.scraping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import org.jooq.lambda.Seq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class Themes {
    private static final Logger log = LoggerFactory.getLogger(Themes.class);

    // Sort by downloads desc then by name.
    private static final Comparator<HtmlCssTheme> popularSort =
            Comparator.comparing(HtmlCssTheme::getDownloads).reversed()
                    .thenComparing(HtmlCssTheme::getTitle);

    // A list of all the theme websites we currently support.
    private static List<Supplier<List<HtmlCssTheme>>> suppliers = Lists.newArrayList(
            TemplateMonsterScraper::popularThemes,
            WrapBootstrapScraper::popularThemes
    );

    /*
     *  Fetch all themes and cache for 4 hours. It takes a little time
     *  to scrape all the sites. We also want to be nice and not spam the sites.
     */
    private static final Supplier<List<HtmlCssTheme>> themesSupplier =
            Suppliers.memoizeWithExpiration(Themes::fetchPopularThemes, 4L, TimeUnit.HOURS);

    private Themes() {
    }

    // Fetch all themes and sort them together.
    private static List<HtmlCssTheme> fetchPopularThemes() {
        return Seq.seq(suppliers)
                .map(sup -> {

                    /*
                     *  If one fails we don't want them all to fail.
                     *  This can be handled better but good enough for now.
                     */
                    try {
                        return sup.get();
                    } catch (Exception ex) {
                        log.warn("Error fetching themes", ex);
                        return Lists.<HtmlCssTheme>newArrayList();
                    }
                })
                .flatMap(List::stream)
                .sorted(popularSort)
                .toList();
    }

    public static List<HtmlCssTheme> getPopularThemes(int num) {
        return Seq.seq(themesSupplier.get()).limit(num).toList();
    }

    public static void main(String[] args) throws JsonProcessingException {
        List<HtmlCssTheme> themes = getPopularThemes(50);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(themes);
        log.debug(json);
    }
}
