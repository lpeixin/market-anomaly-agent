package com.liupeixin.service.impl;

import com.liupeixin.constants.StockTag;
import com.liupeixin.entity.USStockRss;
import com.liupeixin.service.RssService;
import com.liupeixin.service.StockService;
import com.liupeixin.utils.DateConverter;
import com.liupeixin.utils.StockInfoCrawler;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import jakarta.annotation.Resource;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RssServiceImpl implements RssService {

    @Resource
    private StockService stockService;

    public static final String RSS_URL = "https://www.stocktitan.net/rss";

    @Override
    public void displayRssFeed() throws Exception {
        List<SyndEntry> rssList = this.fetchRssFeed(RSS_URL);
        Document webPage = StockInfoCrawler.getWebPage();
        if (rssList == null || rssList.isEmpty()) {
            System.out.println("RSS is empty.");
            return;
        }
        for (SyndEntry entry : rssList) {
            USStockRss stockRss = new USStockRss();
            String title = entry.getTitle();
            String actualTitle = getStockTitle(title);
            stockRss.setTitle(actualTitle);
            // TODO translate title to CN
            stockRss.setTitleCn("");
            stockRss.setLink(entry.getLink());
            LocalDateTime gmtDateTime = DateConverter.convertGmt(entry.getPublishedDate());
            stockRss.setPubDateGmt(gmtDateTime);
            LocalDateTime cnDateTime = DateConverter.convertGmtToCn(entry.getPublishedDate());
            stockRss.setPubDateCn(cnDateTime);
            String stockCode = getStockCode(title);
            stockRss.setStockCode(stockCode);
            try {
                List<String> tagList = StockInfoCrawler.getTagsFromWebPage(actualTitle, webPage);
                stockRss.setTags(tagList.toString());
            } catch (Exception e) {
                stockRss.setTags("");
            }

            if (stockService.isStockNewsExist(stockCode, stockRss.getLink())) {
                System.out.println("Stock " + stockCode + " is exist. Skip saving step.");
                continue;
            }

            System.out.println(stockRss.toString());
            stockService.saveStockNews(stockRss);
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

    private String getTagsCn(List<String> list) {
        StringBuilder tagStr = new StringBuilder();
        for (String str : list) {
            tagStr.append(StockTag.getTagValue(str)).append(",");
        }
        if (!tagStr.isEmpty()) {
            tagStr.deleteCharAt(tagStr.length() - 1);
        }
        return tagStr.toString();
    }
}
