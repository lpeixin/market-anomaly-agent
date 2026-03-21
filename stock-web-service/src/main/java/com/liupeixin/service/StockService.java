package com.liupeixin.service;

import com.liupeixin.entity.USStockRss;

import java.time.LocalDateTime;

public interface StockService {

    public void saveStockNews(USStockRss usStockRss);

    public Boolean isStockNewsExist(String stockCode, String link);

    public Long getStockUnusualCounts(USStockRss usStockRss, LocalDateTime startDate, LocalDateTime endDate);
}
