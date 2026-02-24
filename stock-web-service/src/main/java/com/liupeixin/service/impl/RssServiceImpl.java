package com.liupeixin.service.impl;

import com.liupeixin.service.RssService;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;

@Service
public class RssServiceImpl implements RssService {

    public static final String RSS_URL = "https://www.stocktitan.net/rss";

    @Override
    public void displayRssFeed() throws Exception {
        this.fetchRssFeed(RSS_URL).forEach(System.out::println);
    }

    @Override
    public List<SyndEntry> fetchRssFeed(String rssUrl) throws Exception {
        URL url = new URL(rssUrl);
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(url));
        return feed.getEntries();
    }
}
