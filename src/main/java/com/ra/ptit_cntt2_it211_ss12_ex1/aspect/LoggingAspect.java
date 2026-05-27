package com.ra.ptit_cntt2_it211_ss12_ex1.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Bước 1: @Before - áp dụng cho mọi method trong BookController
    @Before("execution(* com.ra.ptit_cntt2_it211_ss12_ex1.controller.BookController.*(..))")
    public void logBeforeController(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        logger.info("[AOP @Before] Method '{}' đang được gọi. Tham số đầu vào: {}", methodName, args);
    }

    // Bước 2: @AfterReturning - áp dụng cho mọi method trong BookService, log kết quả trả về
    @AfterReturning(
            pointcut = "execution(* com.ra.ptit_cntt2_it211_ss12_ex1.service.BookService.*(..))",
            returning = "result"
    )
    public void logAfterServiceReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("[AOP @AfterReturning] Service Method '{}' thực hiện thành công. Kết quả: {}", methodName, result);
    }

    // Bước 3: @Around - áp dụng cho mọi method trong BookController, đo thời gian chạy
    @Around("execution(* com.ra.ptit_cntt2_it211_ss12_ex1.controller.BookController.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = proceedingJoinPoint.getSignature().getName();

        try {
            return proceedingJoinPoint.proceed();
        } finally {
            long executionTime = System.currentTimeMillis() - startTime;
            logger.info("[AOP @Around] Method '{}' thực thi hết: {} ms", methodName, executionTime);
        }
    }
}