package com.portfolio.portfolio_project.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;


@Configuration
public class CustomMetrics {
    
    @Bean
    public CountedAspect timedAspect(MeterRegistry registry) {
        return new CountedAspect(registry);
    }
}
