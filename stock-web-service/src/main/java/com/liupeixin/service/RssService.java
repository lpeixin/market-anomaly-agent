package com.liupeixin.service;

import com.rometools.rome.feed.synd.SyndEntry;

import java.util.List;

public interface RssService {

    public List<SyndEntry> fetchRssFeed(String rssUrl) throws Exception;

}
