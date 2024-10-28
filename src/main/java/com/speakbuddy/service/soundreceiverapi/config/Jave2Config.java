package com.speakbuddy.service.soundreceiverapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ws.schild.jave.Encoder;

@Configuration
public class Jave2Config {

    @Bean
    public Encoder initializeEncoder() {
        return new Encoder();
    }
}
