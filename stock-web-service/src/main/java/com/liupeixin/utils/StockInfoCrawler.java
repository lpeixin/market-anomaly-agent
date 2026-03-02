package com.liupeixin.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class StockInfoCrawler {

    public static void main(String[] args) throws Exception {
        String url = "https://www.stocktitan.net/news/live.html";
        String targetTitle =
                "Inverite Announces Financial Results for Nine and Three-Months Ending December 31, 2025";
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(10_000)
                .get();
        List<String> tags = extractTagsByTitle(doc, targetTitle);
        System.out.println(tags);
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
