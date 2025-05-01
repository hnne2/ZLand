package com.gr.zland.bot.handler

import com.gr.zland.bot.keyboard.MenuKeyboard
import com.gr.zland.bot.service.MessageService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message

@Component
class MessageHandler(
    private val messageService: MessageService,
    private val menuKeyboard: MenuKeyboard
) {
    fun handle(message: Message) {
        val chatId = message.chatId
        val messageText = message.text

        when (messageText) {
            "/start" -> messageService.sendWelcomeMessage(chatId)
            "🧬 О продукте Zland" -> messageService.sendProductInfo(chatId)
            "🔥 ТОП-15 миксов" -> messageService.sendFileWithTastes(chatId)
            "🍓 Вкусы" -> messageService.sendMessage(chatId, "Выберите категорию вкусов!")
            "🎁 Розыгрыш" -> messageService.sendMessage(chatId, "Участвуй в розыгрыше призов!")
            "📍 Где купить?" -> messageService.requestLocation(chatId)
            "💬 Чат поддержки" -> messageService.sendMessage(chatId, "Свяжитесь с поддержкой: @ZlandSupport")
            "🤝 Партнерство" -> messageService.sendMessage(chatId, "Узнайте об условиях партнерства!")
            else -> messageService.sendMessage(chatId, "Ты написал: $messageText")
        }
    }
}
