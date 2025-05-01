package com.gr.zland.bot

import com.gr.ZLand.bot.config.BotConfig
import com.gr.ZLand.bot.handler.CallbackQueryHandler
import com.gr.ZLand.bot.handler.LocationHandler
import com.gr.ZLand.bot.handler.MessageHandler
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class MyTelegramBot(
    private val botConfig: BotConfig,
    private val messageHandler: MessageHandler,
    private val callbackQueryHandler: CallbackQueryHandler,
    private val locationHandler: LocationHandler
) : TelegramLongPollingBot() {

    override fun getBotToken(): String = botConfig.botToken
    override fun getBotUsername(): String = botConfig.botUsername

    override fun onUpdateReceived(update: Update) {
        when {
            update.hasCallbackQuery() -> callbackQueryHandler.handle(update.callbackQuery)
            update.hasMessage() && update.message.hasLocation() -> locationHandler.handle(update.message)
            update.hasMessage() && update.message.hasText() -> messageHandler.handle(update.message)
        }
    }
}
