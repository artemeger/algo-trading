package de.y3om11.algotrading.adapter.cucumber.config;

import de.y3om11.algotrader.domain.gateway.BarSeriesSerializer;
import de.y3om11.algotrading.adapter.serialization.BarSeriesSerializerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("de.y3om11.algotrading.adapter.cucumber")
public class ContextConfig {

    @Bean
    public BarSeriesSerializer getBarSeriesSerializer(){
        return new BarSeriesSerializerImpl();
    }
}
