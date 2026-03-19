package com.liupeixin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

    @Override
    public Boolean isStockNewsExist(String stockCode, String link) {
        QueryWrapper<USStockRss> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("stock_code", stockCode);
        queryWrapper.eq("link", link);
        return usStockRssMapper.selectCount(queryWrapper) > 0;
    }
}
