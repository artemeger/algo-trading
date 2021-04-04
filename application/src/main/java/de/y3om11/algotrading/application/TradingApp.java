package de.y3om11.algotrading.application;

import de.y3om11.algotrading.domain.ApplicationFacade;
import de.y3om11.algotrading.domain.constants.MarketPair;
import de.y3om11.algotrading.domain.constants.TimeInterval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.ta4j.core.num.PrecisionNum;

@SpringBootApplication
@ComponentScan(basePackages = "de.y3om11.algotrading")
public class TradingApp {

    final static Logger log = LoggerFactory.getLogger(TradingApp.class);

    private final ApplicationFacade applicationFacade;

    @Autowired
    public TradingApp(ApplicationFacade applicationFacade) {
        this.applicationFacade = applicationFacade;
    }

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
    public void startup(final ApplicationEvent event) {
        log.info("Trading app startup " + event.getTimestamp());
        final var tradeId = applicationFacade.createTradeSession(MarketPair.ETH_BTC, TimeInterval.ONE_MINUTE,
                120, "SimpleSMA15", PrecisionNum.valueOf(50));
        final var startSuccess = applicationFacade.runTradingSession(tradeId);
        log.info("Trading start executed: " + startSuccess);
    }
}
