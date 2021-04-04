package de.y3om11.algotrading.application.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class AppConfig {

    @Bean
    @Qualifier("threadPoolExecutor")
    public ThreadPoolExecutor getThreadPoolExecutor(){
        return (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
    }

    @Bean
    @Qualifier("scheduledThreadPoolExecutor")
    public ScheduledExecutorService getScheduledPoolExecutor(){
        return Executors.newScheduledThreadPool(10);
    }

}
