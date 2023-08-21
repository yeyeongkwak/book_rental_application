package com.bnf.xc.prepayment.core.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebConfig {
    @Bean
    fun webClientBuilder(): WebClient.Builder {
        val size = 16 * 1024 * 1024
        val strategies = ExchangeStrategies.builder()
            .codecs { codecs: ClientCodecConfigurer -> codecs.defaultCodecs().maxInMemorySize(size) }
            .build()
        return WebClient.builder()
            .exchangeStrategies(strategies)
    }
}