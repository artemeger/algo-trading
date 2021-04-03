package de.y3om11.algotrading.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class AppConfig {

    @Bean
    public ThreadPoolExecutor getThreadPoolExecutor(){
        return (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
    }
}
