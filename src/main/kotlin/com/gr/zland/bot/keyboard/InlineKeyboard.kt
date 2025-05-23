package com.gr.zland.bot.keyboard

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

@Component
class InlineKeyboard {
    fun createTastesKeyboard(): InlineKeyboardMarkup {
        val button = InlineKeyboardButton().apply {
            text = "Подробнее"
            callbackData = "send_pdf_tastes"
        }
        return InlineKeyboardMarkup().apply {
            keyboard = listOf(listOf(button))
        }
    }
}
