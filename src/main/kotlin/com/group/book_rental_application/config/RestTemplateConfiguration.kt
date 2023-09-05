package com.bnf.xc.prepayment.core.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateConfiguration {
    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplateBuilder()
            .messageConverters(
                MappingJackson2HttpMessageConverter(
                    ObjectMapper()
                        .registerModule(
                            KotlinModule.Builder()
                                .withReflectionCacheSize(512)
                                .configure(KotlinFeature.NullToEmptyCollection, false)
                                .configure(KotlinFeature.NullToEmptyMap, false)
                                .configure(KotlinFeature.NullIsSameAsDefault, enabled = true)
                                .configure(KotlinFeature.SingletonSupport, false)
                                .configure(KotlinFeature.StrictNullChecks, false)
                                .build()
                        )
                        .registerModule(JavaTimeModule())
                )
            )
            .build()
    }
}