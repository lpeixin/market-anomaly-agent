package com.liupeixin.utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jspecify.annotations.NonNull;

import java.util.*;

public class StockInfoCrawler {

    private static final String[] USER_AGENTS = {
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:132.0) Gecko/20100101 Firefox/132.0",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 14.2; rv:132.0) Gecko/20100101 Firefox/132.0",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Edg/131.0.0.0 Safari/537.36"
    };

    private static Connection session = null;

    private static final String URL = "https://www.stocktitan.net/news/live.html";

    public static void main(String[] args) throws Exception {
        String targetTitle =
                "Inverite Announces Financial Results for Nine and Three-Months Ending December 31, 2025";
        List<String> tags = getTags(targetTitle);
        System.out.println(tags);
    }

    public static Document getWebPage() throws Exception {
        if (session == null) {
            // initialize session in first time fetching
            session = Jsoup.newSession()
                    .timeout(15_000)
                    .maxBodySize(5 * 1024 * 1024)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .followRedirects(true);
        }
        String ua = USER_AGENTS[new Random().nextInt(USER_AGENTS.length)];

        Map<String, String> headers = getStringMap(ua);

        return session
                .url(URL)
                .headers(headers)
                .referrer("https://www.google.com/")
                .userAgent(ua)
                .get();
        // return Jsoup.connect(URL).get();
    }

    public static List<String> getTagsFromWebPage(String title, Document webPage) throws Exception {
        return extractTagsByTitle(webPage, title);
    }

    public static List<String> getTags(String title) throws Exception {
        Document doc = Jsoup.connect(URL)
                .userAgent("Mozilla/5.0")
                .timeout(10_000)
                .get();
        return extractTagsByTitle(doc, title);
    }

    private static @NonNull Map<String, String> getStringMap(String ua) {
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", ua);
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8");
        headers.put("Accept-Language", "en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7");
        headers.put("Accept-Encoding", "gzip, deflate");
        headers.put("Upgrade-Insecure-Requests", "1");
        headers.put("Sec-Fetch-Dest", "document");
        headers.put("Sec-Fetch-Mode", "navigate");
        headers.put("Sec-Fetch-Site", "none");
        headers.put("Sec-Fetch-User", "?1");
        return headers;
    }

    /**
     * Extract tags array by news title
     */
    private static List<String> extractTagsByTitle(Document doc, String title) {
        List<String> result = new ArrayList<>();
        Elements titleElements = doc.select("a.feed-link");
        for (Element titleEl : titleElements) {
            if (title.equals(titleEl.text())) {
                Element newsRow = titleEl.closest("div.news-row");
                if (newsRow == null) {
                    continue;
                }
                Elements tagElements = newsRow.select("div[name=tags] span.badge");
                for (Element tag : tagElements) {
                    result.add(tag.text().trim());
                }
                break;
            }
        }
        return result;
    }
}
