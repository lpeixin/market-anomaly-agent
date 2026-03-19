package com.liupeixin.service;

import com.liupeixin.entity.USStockRss;

public interface StockService {

    public void saveStockNews(USStockRss usStockRss);

    public Boolean isStockNewsExist(String stockCode, String link);
}
