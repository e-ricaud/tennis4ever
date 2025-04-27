package com.ericaud.tennis4ever.service.conf;

import com.ericaud.tennis4ever.service.ScoringService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationServiceConfig {

    @Bean
    public ScoringService scoringService() {
        return new ScoringService();
    }

}
