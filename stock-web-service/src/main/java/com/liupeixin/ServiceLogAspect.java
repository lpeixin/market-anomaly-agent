package com.liupeixin;

import cn.hutool.core.date.StopWatch;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class ServiceLogAspect {

    @Around("execution(* com.liupeixin.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object result = joinPoint.proceed();
        String pointName = joinPoint.getTarget().getClass().getName() + '.' + joinPoint.getSignature().getName();
        stopWatch.stop();

        long period = stopWatch.getTotalTimeMillis();
        if (period > 3000) {
            log.error("[{}], [{}], [{}], [{}]", pointName, period, "SLOW", joinPoint.getArgs());
        } else if (period > 2000) {
            log.warn("[{}], [{}], [{}], [{}]", pointName, period, "NORMAL", joinPoint.getArgs());
        } else {
            log.info("[{}], [{}], [{}], [{}]", pointName, period, "OK", joinPoint.getArgs());
        }
        return result;
    }
}
