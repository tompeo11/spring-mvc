package com.tom.javaspring.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

//@Aspect
//@Component
public class LoggingAspect {
    private static final Logger logger = LogManager.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.tom.javaspring.dao.*.*(..))")
    private void forDaoPackage() {}

    @Pointcut("execution(* com.tom.javaspring.controller.*.*(..))")
    private void forControllerPackage() {}

    @Pointcut("execution(* com.tom.javaspring.service.*.*(..))")
    private void forServicePackage() {}

    @Pointcut("forDaoPackage() || forControllerPackage() || forServicePackage()")
    private void forLoggingAspect() {}

    @Before("forLoggingAspect()")
    public void beforeLogging(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().toShortString();

        Object[] args = joinPoint.getArgs();

        logger.info("===> in @Before advice: calling method: " + method);

        for (Object a : args) {
            logger.info("===> argument: " + a);
        }
    }

    @AfterReturning(pointcut = "forLoggingAspect()", returning = "theResult")
    public void afterReturning(JoinPoint theJoinPoint, Object theResult) {
        String theMethod = theJoinPoint.getSignature().toShortString();
        logger.info("===> in @AfterReturning advice: calling method: " + theMethod);

        logger.info("===> Result: " + theResult);
    }
}
