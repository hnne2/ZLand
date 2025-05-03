package com.gr.zland.bot.service

import com.gr.zland.bot.keyboard.InlineKeyboard
import com.gr.zland.bot.keyboard.MenuKeyboard
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendDocument
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import java.io.File

@Service
class MessageService(
    private val bot: TelegramLongPollingBot,
    private val menuKeyboard: MenuKeyboard,
    private val inlineKeyboard: InlineKeyboard
) {
    private val photoFile = File("/opt/files/firstPost.jpg")
    private val pdfFile = File("/opt/files/book.pdf")

    fun sendWelcomeMessage(chatId: Long) {
        val message = SendMessage().apply {
            this.chatId = chatId.toString()
            text = "Добро пожаловать в Zland! Выберите опцию:"
            replyMarkup = menuKeyboard.create()
        }
        execute(message)
    }

    fun sendMessage(chatId: Long, text: String) {
        val message = SendMessage().apply {
            this.chatId = chatId.toString()
            this.text = text
            replyMarkup = menuKeyboard.create()
        }
        execute(message)
    }

    fun sendProductInfo(chatId: Long) {
        val photo = SendPhoto().apply {
            this.chatId = chatId.toString()
            photo = InputFile(photoFile)
            caption = """
                MIX IT UP VAPE IT OUT
                Vaprig sivica Kit for Zidon minipots
                50+ flavors, over 125K combinations
                Представляем вам Zland Mini - устройство, открывающее безграничный мир вкусов!
                Zland Mini - это не просто девайс, это ключ к созданию своей собственной, уникальной вкусовой вселенной.
                Бесконечное разнообразие вкусов с Zland: создай свой идеальный микс!
            """.trimIndent()
            replyMarkup = inlineKeyboard.createProductInfoKeyboard()
        }
        execute(photo)
    }

    fun sendFileWithTastes(chatId: Long) {
        val photo = SendPhoto().apply {
            this.chatId = chatId.toString()
            photo = InputFile(photoFile)
            caption = "Познакомьтесь с топ-15 миксовых сочетаний 👇"
            replyMarkup = inlineKeyboard.createTastesKeyboard()
        }
        execute(photo)
    }

    fun sendPdfWithTastes(chatId: Long) {
        val document = SendDocument().apply {
            this.chatId = chatId.toString()
            caption = "Вот PDF с топ-15 сочетаниями 🍹"
            document = InputFile(pdfFile)
        }
        execute(document)
    }

    fun requestLocation(chatId: Long) {
        val message = SendMessage().apply {
            this.chatId = chatId.toString()
            text = "Пожалуйста, отправьте вашу геолокацию 📍"
            replyMarkup = createLocationKeyboard()
        }
        execute(message)
    }

    private fun createLocationKeyboard(): ReplyKeyboardMarkup {
        val locationButton = KeyboardButton("📍 Отправить геолокацию").apply {
            requestLocation = true
        }
        val keyboardRow = KeyboardRow().apply { add(locationButton) }
        return ReplyKeyboardMarkup().apply {
            keyboard = listOf(keyboardRow)
            resizeKeyboard = true
            oneTimeKeyboard = true
        }
    }

    private fun execute(method: Any) {
        try {
            when (method) {
                is SendMessage -> bot.execute(method)
                is SendPhoto -> bot.execute(method)
                is SendDocument -> bot.execute(method)
            }
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }
    fun sendMiniAppForRaffle(chatId: Long, miniAppUrl: String) {
        val message = SendMessage().apply {
            this.chatId = chatId.toString();
            text = "Участвуй в розыгрыше призов! Нажми, чтобы открыть! 🎁";
            replyMarkup = InlineKeyboardMarkup().apply {
                keyboard = listOf(
                    listOf(
                        InlineKeyboardButton().apply {
                            text = "Открыть розыгрыш";
                            webApp = WebAppInfo(miniAppUrl);
                        }
                    )
                );
            };
        };
        execute(message);
    }
}
