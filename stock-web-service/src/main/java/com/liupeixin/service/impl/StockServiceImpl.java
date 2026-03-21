package com.liupeixin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liupeixin.entity.USStockRss;
import com.liupeixin.mapper.USStockRssMapper;
import com.liupeixin.service.StockService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @Override
    public Long getStockUnusualCounts(USStockRss usStockRss, LocalDateTime startDate, LocalDateTime endDate) {
        String stockCode = usStockRss.getStockCode();
        QueryWrapper<USStockRss> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("stock_code", stockCode);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startTime = formatter.format(startDate);
        String endTime = formatter.format(endDate);
        queryWrapper.ge("pub_date_gmt", startTime);
        queryWrapper.le("pub_date_gmt", endTime);
        return usStockRssMapper.selectCount(queryWrapper);
    }
}
