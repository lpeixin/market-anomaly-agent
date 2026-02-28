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
            stockRss.setTitle(entry.getTitle());
            stockRss.setLink(entry.getLink());
            LocalDateTime gmtDateTime = DateConverter.convertGmt(entry.getPublishedDate());
            stockRss.setPubDateGmt(gmtDateTime);
            LocalDateTime cnDateTime = DateConverter.convertGmtToCn(entry.getUpdatedDate());
            stockRss.setPubDateCn(cnDateTime);
        }
    }

    @Override
    public List<SyndEntry> fetchRssFeed(String rssUrl) throws Exception {
        URL url = new URL(rssUrl);
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(url));
        return feed.getEntries();
    }
}
