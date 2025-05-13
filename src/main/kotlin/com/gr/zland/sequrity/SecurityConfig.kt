package com.gr.zland.config

import com.gr.zland.sequrity.JwtAuthenticationFilter
import com.gr.zland.sequrity.JwtUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(private val jwtUtil: JwtUtil) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors { it.configurationSource(corsConfigurationSource()) }
            .csrf { it.disable() }
            .authorizeHttpRequests { auth ->
                auth.requestMatchers(
                    "/api/auth/telegram",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/v3/api-docs",
                    "/swagger-ui.html",
                    "/webapp",
                    "/webapp/**",
                    "/api/images/**"
                ).permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

}
