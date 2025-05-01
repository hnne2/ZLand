package com.gr.zland.bot.config
import com.gr.zland.bot.MyTelegramBot
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Configuration
class BotConfig {
    @Bean
    @Throws(TelegramApiException::class)
    fun telegramBotsApi(myTelegramBot: MyTelegramBot?): TelegramBotsApi {
        val telegramBotsApi = TelegramBotsApi(DefaultBotSession::class.java)
        telegramBotsApi.registerBot(myTelegramBot)
        return telegramBotsApi
    }
}
