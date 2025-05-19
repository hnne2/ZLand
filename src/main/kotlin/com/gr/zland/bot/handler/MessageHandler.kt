package com.gr.zland.bot.handler

import com.gr.zland.bot.keyboard.MenuKeyboard
import com.gr.zland.bot.service.MessageService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message

@Component
class MessageHandler(
    private val messageService: MessageService,
    private val menuKeyboard: MenuKeyboard
) {
    @Value("\${telegram.bot.domen}")
    private lateinit var telegramBotDomen: String
    fun handle(message: Message) {
        val chatId = message.chatId
        val messageText = message.text

        when (messageText) {
            "/start" -> messageService.sendWelcomeMessage(chatId)
            "🧬 О продукте Zland" -> messageService.sendProductInfo(chatId,"$telegramBotDomen/about/")
            "🔥 ТОП-15 миксов" -> messageService.sendFileWithTastes(chatId)
            "🍓 Вкусы" -> messageService.sendCatalog(chatId, "$telegramBotDomen/catalog/")
            "🎁 Розыгрыш" -> messageService.sendMiniAppForRaffle(chatId, "$telegramBotDomen/spinner/");
            "📍 Где купить?" -> messageService.requestLocation(chatId)
            "💬 Чат поддержки" -> messageService.sendMessage(chatId, "Свяжитесь с поддержкой: @ZlandSupport")
            "🤝 Партнерство" -> messageService.sendAboutToast(chatId, "$telegramBotDomen/partner/")
            "\uD83D\uDD19 Назад в главное меню" -> messageService.sendWelcomeMessage(chatId);
            else -> messageService.sendMessage(chatId, "я не понял команды: $messageText")
        }
    }
}
