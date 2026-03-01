package com.liupeixin.service.impl;

import com.liupeixin.entity.USStockRss;
import com.liupeixin.service.RssService;
import com.liupeixin.utils.DateConverter;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RssServiceImpl implements RssService {

    public static final String RSS_URL = "https://www.stocktitan.net/rss";

    @Override
    public void displayRssFeed() throws Exception {
        List<SyndEntry> rssList = this.fetchRssFeed(RSS_URL);
        if (rssList == null || rssList.isEmpty()) {
            System.out.println("RSS is empty.");
            return;
        }
        for (SyndEntry entry : rssList) {
            USStockRss stockRss = new USStockRss();
            String title = entry.getTitle();
            stockRss.setTitle(getStockTitle(title));
            stockRss.setLink(entry.getLink());
            LocalDateTime gmtDateTime = DateConverter.convertGmt(entry.getPublishedDate());
            stockRss.setPubDateGmt(gmtDateTime);
            LocalDateTime cnDateTime = DateConverter.convertGmtToCn(entry.getPublishedDate());
            stockRss.setPubDateCn(cnDateTime);
            stockRss.setStockCode(getStockCode(title));
            stockRss.setTags("");
            stockRss.setTitleCn("");
            System.out.println(stockRss.toString());
        }
    }

    @Override
    public List<SyndEntry> fetchRssFeed(String rssUrl) throws Exception {
        URL url = new URL(rssUrl);
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(url));
        return feed.getEntries();
    }

    private String getStockTitle(String title) {
        String[] arr = title.split("\\|");
        return arr[1].trim();
    }

    private String getStockCode(String title) {
        String[] arr = title.split("\\|");
        return arr[arr.length - 1].trim().split("Stock News")[0].trim();
    }
}
