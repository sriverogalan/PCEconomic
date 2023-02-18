package me.pceconomic.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PCEconomicSetting {

    @Bean
    public PCEconomicSetting config() {
        PCEconomicSetting pceconomic = new PCEconomicSetting();

        // other setting
        return pceconomic;
    }

}