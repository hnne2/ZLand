package com.gr.zland.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Bean
fun corsConfigurationSource(): CorsConfigurationSource {
    val config = CorsConfiguration().apply {
        allowCredentials = true
        addAllowedOrigin("http://localhost:3000")
        addAllowedHeader("*")
        addAllowedMethod("*")
    }
    val source = UrlBasedCorsConfigurationSource()
    source.registerCorsConfiguration("/**", config)
    return source
}