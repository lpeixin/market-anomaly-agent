package com.liupeixin;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@EnableScheduling
public class StockScheduler {

    @Scheduled(cron = "0/20 * * * * ?")
    public void getStockInfo() {
        System.out.println("running every 20 seconds... " + LocalDateTime.now());
    }
}
