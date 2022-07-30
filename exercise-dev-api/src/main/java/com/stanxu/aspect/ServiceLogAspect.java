package com.stanxu.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);

    @Around("execution(* com.stanxu.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable{
        logger.info("======= service started ======",joinPoint.getTarget().getClass(),joinPoint.getSignature().getName());

        long begin = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();

        long takeTime = end - begin;

        if (takeTime > 3000){
            logger.error("==== service ended, toke:{} ms ====",takeTime);
        }else if (takeTime >2000){
            logger.warn("==== service ended, toke:{} ms ====",takeTime);
        }else {
            logger.info("==== service ended, token:{} ms ====",takeTime);
        }

        return result;
    }
}
