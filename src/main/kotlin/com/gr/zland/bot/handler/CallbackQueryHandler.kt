package com.gr.zland.bot.handler

import com.gr.zland.bot.service.MessageService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.CallbackQuery

@Component
class CallbackQueryHandler(
    private val messageService: MessageService
) {
    fun handle(callbackQuery: CallbackQuery) {
        val callbackData = callbackQuery.data
        val chatId = callbackQuery.message.chatId

        if (callbackData == "send_pdf_tastes") {
            messageService.sendFileWithTastes(chatId)
        }
    }
}
