package com.liupeixin;

import cn.hutool.core.date.StopWatch;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TimeTest {

    @Test
    public void testTime() throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("task 1....");
        Thread.sleep(1001);
        stopWatch.stop();
        stopWatch.start("task 2....");
        Thread.sleep(800);
        stopWatch.stop();
        stopWatch.start("task 3....");
        Thread.sleep(1100);
        stopWatch.stop();

        System.out.println(stopWatch.prettyPrint());
        System.out.println(stopWatch.shortSummary());
        System.out.println(stopWatch.getTotalTimeMillis());
        System.out.println(stopWatch.getTaskCount());
    }

}
