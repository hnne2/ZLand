package com.gr.zland.servis

import com.gr.zland.model.TelegramUser
import com.gr.zland.sequrity.JwtUtil
import org.json.JSONObject
import org.springframework.stereotype.Service
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Service
class TelegramAuthService(private val jwtUtil: JwtUtil){

    private val botToken = "5653166571:AAGGDKYjD3YYNA0ahUWrzFMqfvVBgy_24AQ"

    fun authenticate(initData: String): Pair<String,TelegramUser> {
        if (!verifyInitData(initData)) {
            throw IllegalArgumentException("Invalid initData")
        }
        val userId = extractUserId(initData)

        val paramMap = URLDecoder.decode(initData, StandardCharsets.UTF_8).split("&").associate {
            val (key, value) = it.split("=")
            key to value
        }
        val userJson = paramMap["user"]
            ?: throw IllegalArgumentException("User not found in initData")

        val jsonObject = JSONObject(userJson)
        val telegramUser = TelegramUser(
            telegramId = userId,
            username = jsonObject.optString("username", null),
            firstName = jsonObject.optString("first_name", null),
        )

        return Pair(jwtUtil.generateToken(userId.toString()),telegramUser)
    }

    private fun verifyInitData(initData: String): Boolean {
        println("Исходный initData: $initData")

        val parts = initData
            .split("&")
            .filterNot { it.startsWith("hash=") }
            .map {
                val (key, value) = it.split("=", limit = 2)
                key to URLDecoder.decode(value, StandardCharsets.UTF_8.name())
            }
            .sortedBy { it.first }

        println("Парсенные и отсортированные пары:")
        parts.forEach { println(" - ${it.first}=${it.second}") }

        val dataCheckString = parts.joinToString("\n") { "${it.first}=${it.second}" }
        println("Сформированная строка для проверки (data_check_string):\n$dataCheckString")

        // Создаём секретный ключ на основе botToken
        val secretKey = "WebAppData".toByteArray(Charsets.UTF_8)
        val keySpec = SecretKeySpec(
            Mac.getInstance("HmacSHA256").run {
                init(SecretKeySpec(secretKey, "HmacSHA256"))
                doFinal(botToken.toByteArray(Charsets.UTF_8))
            },
            "HmacSHA256"
        )

        // Вычисляем HMAC SHA-256
        val mac = Mac.getInstance("HmacSHA256").apply {
            init(keySpec)
        }

        val calculatedHash = mac.doFinal(dataCheckString.toByteArray(Charsets.UTF_8)).joinToString("") {
            "%02x".format(it)
        }

        println("Рассчитанный hash: $calculatedHash")

        // Получаем hash из initData
        val actualHash = initData.split("&")
            .firstOrNull { it.startsWith("hash=") }
            ?.substringAfter("hash=")
            ?: run {
                println("Hash не найден в initData.")
                return false
            }

        println("Переданный hash:    $actualHash")

        val result = calculatedHash == actualHash
        println("Результат сравнения: $result")

        return result
    }


    private fun extractUserId(initData: String): Long {
        val paramMap = URLDecoder.decode(initData, StandardCharsets.UTF_8)
            .split("&")
            .associate {
                val (key, value) = it.split("=")
                key to value
            }

        val userJson = paramMap["user"] ?: throw IllegalArgumentException("User not found")

        // Распарсить JSON и достать id
        val json = JSONObject(userJson)
        return json.getLong("id")
    }

}

