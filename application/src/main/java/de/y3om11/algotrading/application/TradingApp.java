package de.y3om11.algotrading.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class TradingApp {

    final static Logger log = LoggerFactory.getLogger(TradingApp.class);

    public static void main(String[] args) {
        try {
            SpringApplication.run(TradingApp.class, args);
            Thread.currentThread().join();
        } catch (InterruptedException e){
            log.warn("Application thread was interrupted");
        } finally {
            log.info("Shutting down");
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startup(){
        log.info("Trading app startup");
    }
}
