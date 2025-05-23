package com.gr.zland.bot.service

import com.gr.zland.bot.keyboard.InlineKeyboard
import com.gr.zland.bot.keyboard.MenuKeyboard
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
    private val inlineKeyboard: InlineKeyboard,
) {
    val photoFile = File("/home/zland/java/uploads/a.jpg")
    val pdfTop15 = File("/home/zland/java/uploads/Top15.pdf")
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

    fun sendProductInfo(chatId: Long,miniAppUrl: String) {
        val photo = SendPhoto().apply {
            this.chatId = chatId.toString()
            photo = InputFile(photoFile)
            caption = """
                Представляем вам Zland Mini – устройство, открывающее безграничный мир вкусов!
                Zland Mini – это не просто девайс, это ключ к созданию своей собственной, уникальной вкусовой вселенной.

                Бесконечное разнообразие вкусов с Zland: создай свой идеальный микс!
            """.trimIndent()
            replyMarkup = InlineKeyboardMarkup().apply {
                keyboard = listOf(
                    listOf(
                        InlineKeyboardButton().apply {
                            text = "Подробнее";
                            webApp = WebAppInfo(miniAppUrl);
                        }
                    )
                );
            };
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
             document = InputFile(pdfTop15)
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
    fun createLocationKeyboard(): ReplyKeyboardMarkup {
        val locationButton = KeyboardButton("📍 Отправить геолокацию").apply {
            requestLocation = true
        }

        val backButton = KeyboardButton("🔙 Назад в главное меню")

        return ReplyKeyboardMarkup().apply {
            keyboard = listOf(
                KeyboardRow().apply { add(locationButton) },
                KeyboardRow().apply { add(backButton) }
            )
            resizeKeyboard = true
            oneTimeKeyboard = false // можно true, если хочешь убрать после нажатия
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
                            text = "Участвовать";
                            webApp = WebAppInfo(miniAppUrl);
                        }
                    )
                );
            };
        };
        execute(message);
    }
    fun sendAboutToast(chatId: Long, miniAppUrl: String){
        val message = SendMessage().apply {
            this.chatId = chatId.toString();
            text = "Можете отправить заявку на партнерство";
            replyMarkup = InlineKeyboardMarkup().apply {
                keyboard = listOf(
                    listOf(
                        InlineKeyboardButton().apply {
                            text = "отправить";
                            webApp = WebAppInfo(miniAppUrl);
                        }
                    )
                );
            };
        };
        execute(message);
    }
    fun sendCatalog(chatId: Long, miniAppUrl: String) {
        val photo = SendPhoto().apply {
            this.chatId = chatId.toString()
            this.photo = InputFile(photoFile)
            this.replyMarkup = InlineKeyboardMarkup().apply {
                keyboard = listOf(
                    listOf(
                        InlineKeyboardButton().apply {
                            text = "Выбрать вкусы"
                            webApp = WebAppInfo(miniAppUrl)
                        }
                    )
                )
            }
        }

        execute(photo)
    }
}
