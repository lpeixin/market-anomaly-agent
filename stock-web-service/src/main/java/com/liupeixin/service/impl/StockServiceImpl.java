package com.liupeixin.service.impl;

import com.liupeixin.entity.USStockRss;
import com.liupeixin.mapper.USStockRssMapper;
import com.liupeixin.service.StockService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {

    @Resource
    private USStockRssMapper usStockRssMapper;

    @Override
    public void saveStockNews(USStockRss usStockRss) {
        usStockRssMapper.insert(usStockRss);
    }
}
