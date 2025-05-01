package com.gr.zland.bot

import com.gr.zland.bot.config.BotToken
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class MyTelegramBot(
    private val botConfig: BotToken,
    private val context: ApplicationContext
) : TelegramLongPollingBot() {
    init {
        println(("MyTelegramBot initialized with token: ${botConfig.botToken}, username: ${botConfig.botUsername}"))
    }
    override fun getBotToken(): String = botConfig.botToken
    override fun getBotUsername(): String = botConfig.botUsername

    override fun onUpdateReceived(update: Update) {
        println("111")
        val dispatcher = context.getBean(UpdateDispatcher::class.java)
        dispatcher.dispatch(update)
    }
}
