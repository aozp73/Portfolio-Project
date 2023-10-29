package com.portfolio.portfolio_project.core.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import io.sentry.Sentry;

@Aspect
@Component
public class AopHandler {
    
    @Pointcut("@annotation(com.portfolio.portfolio_project.core.aop.CustomSentryMonitoring)")
    public void sentryMonitoring() {
    }

    @Before("sentryMonitoring()")
    public void sentryMonitoring(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Sentry.captureMessage(methodName + " 요청이 응답되었습니다.");
    }
}
