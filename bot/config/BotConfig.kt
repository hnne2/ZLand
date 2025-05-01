package com.gr.ZLand.bot.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class BotConfig(
    @Value("\${telegram.bot.token}")
    val botToken: String,
    @Value("\${telegram.bot.username}")
    val botUsername: String
)
