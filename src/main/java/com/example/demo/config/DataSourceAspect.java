package com.example.demo.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Component
public class DataSourceAspect {

    @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void routeDataSource(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Transactional transactional = signature.getMethod().getAnnotation(Transactional.class);

        if (transactional.readOnly()) {
            DataSourceContextHolder.set(DataSourceContextHolder.DataSourceType.READER);
        } else {
            DataSourceContextHolder.set(DataSourceContextHolder.DataSourceType.MASTER);
        }
    }

    @After("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void clearContext() {
        DataSourceContextHolder.clear();
    }
}

