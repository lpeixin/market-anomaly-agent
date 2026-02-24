package com.liupeixin;

import com.liupeixin.service.RssService;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@EnableScheduling
public class StockScheduler {

    @Resource
    private RssService rssService;

    @Scheduled(cron = "0/20 * * * * ?")
    public void getStockInfo() throws Exception {
        System.out.println("running every 20 seconds... " + LocalDateTime.now());
        rssService.displayRssFeed();
        System.out.println("============================");
    }
}
