package com.bnf.xc.prepayment.core.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfiguration {

    @Value("\${server.port}")
    lateinit var port: String

    @Bean
    fun customOpenAPI(): OpenAPI {
        val authorization = SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer")
        return OpenAPI().apply {
            addServersItem(Server().apply {
                url = "http://localhost:$port"
                description = "로컬서버"
            })
            addServersItem(Server().apply {
                url = "http://172.16.123.128:30553"
                description = "개발서버"
            })
            components = Components().addSecuritySchemes("Authorization", authorization)
            info = Info().apply {
                title = "충전금관리 Core"
                version = null
            }
        }
    }
}