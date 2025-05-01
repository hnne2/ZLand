package com.gr.zland.bot.keyboard

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

@Component
class MenuKeyboard {
    fun create(): ReplyKeyboardMarkup {
        val keyboardMarkup = ReplyKeyboardMarkup().apply {
            resizeKeyboard = true
            selective = false
            oneTimeKeyboard = false
        }

        val keyboard = mutableListOf(
            KeyboardRow().apply {
                add("🧬 О продукте Zland")
                add("🔥 ТОП-15 миксов")
            },
            KeyboardRow().apply {
                add("🍓 Вкусы")
                add("🎁 Розыгрыш")
            },
            KeyboardRow().apply {
                add("📍 Где купить?")
                add("💬 Чат поддержки")
                add("🤝 Партнерство")
            }
        )

        keyboardMarkup.keyboard = keyboard
        return keyboardMarkup
    }
}
