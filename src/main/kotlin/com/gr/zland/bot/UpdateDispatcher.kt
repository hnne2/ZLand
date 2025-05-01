package com.gr.zland.bot

import com.gr.zland.bot.handler.CallbackQueryHandler
import com.gr.zland.bot.handler.LocationHandler
import com.gr.zland.bot.handler.MessageHandler
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class UpdateDispatcher(
    private val messageHandler: MessageHandler,
    private val callbackQueryHandler: CallbackQueryHandler,
    private val locationHandler: LocationHandler
) {
    fun dispatch(update: Update) {
        when {
            update.hasCallbackQuery() -> callbackQueryHandler.handle(update.callbackQuery)
            update.hasMessage() && update.message.hasLocation() -> locationHandler.handle(update.message)
            update.hasMessage() && update.message.hasText() -> messageHandler.handle(update.message)
        }
    }
}
