package com.liupeixin;

import com.liupeixin.entity.USStockRss;
import com.liupeixin.utils.DateConverter;
import com.liupeixin.utils.StockInfoCrawler;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class RssTest {

    public static final String RSS_URL = "https://www.stocktitan.net/rss";

    public List<SyndEntry> fetchRssFeed(String rssUrl) throws Exception {
        URL url = new URL(rssUrl);
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(url));
        return feed.getEntries();
    }

    @Test
    public void testFetchRssFeed() throws Exception {
        this.fetchRssFeed(RSS_URL).forEach(System.out::println);
    }

    @Test
    public void testExtractTag() throws Exception {
        List<SyndEntry> rssList = this.fetchRssFeed(RSS_URL);
        Document webPage = StockInfoCrawler.getWebPage();
        if (rssList == null || rssList.isEmpty()) {
            System.out.println("RSS is empty.");
            return;
        }
        for (SyndEntry entry : rssList) {
            String title = entry.getTitle();
            String[] arr = title.split("\\|");
            String actualTitle = arr[0].trim();
            try {
                List<String> tagList = StockInfoCrawler.getTagsFromWebPage(actualTitle, webPage);
                System.out.println(tagList.toString());
            } catch (Exception e) {
                System.out.println("Tag is Empty ====================");
            }
        }
    }
}
