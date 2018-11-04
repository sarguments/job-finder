package me.saru.jobfinder.scraping;

public class HtmlCssTheme {
    private final String title;
    private final String url;
    private final String imageUrl;
    private final int downloads;

    public HtmlCssTheme(String title, String url, String imageUrl, int downloads) {
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
        this.downloads = downloads;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getDownloads() {
        return downloads;
    }
}