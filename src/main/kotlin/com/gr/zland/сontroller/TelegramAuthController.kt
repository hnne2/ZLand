package com.gr.zland.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.gr.zland.model.TelegramUser
import com.gr.zland.repository.UserRepository
import com.gr.zland.util.JwtUtil
import com.gr.zland.util.TelegramAuthUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@RestController
class TelegramAuthController(
    @Value("\${telegram.bot.token}") private val botToken: String,
    private val userRepository: UserRepository,
    private val jwtUtil: JwtUtil,
    private val objectMapper: ObjectMapper
) {
    data class AuthRequest(val initData: String)

    @PostMapping("/api/auth/telegram")
    fun authenticate(@RequestBody request: AuthRequest): ResponseEntity<Any> {
        val initData = request.initData
        if (initData.isBlank()) {
            return ResponseEntity.badRequest().body("initData is required")
        }

        // Парсим initData
        val data = parseInitData(initData)
        val receivedHash = data["hash"] ?: return ResponseEntity.badRequest().body("Missing hash")
        data.remove("hash")

        // Формируем data-check-string
        val dataCheckString = data.entries
            .sortedBy { it.key }
            .joinToString("\n") { "${it.key}=${it.value}" }

        // Проверяем подпись
        val secretKey = TelegramAuthUtils.createSecretKey(botToken)
        val computedHash = TelegramAuthUtils.computeHmacSha256(dataCheckString, secretKey)
        if (computedHash != receivedHash) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid hash")
        }

        // Извлекаем данные пользователя
        val userJson = URLDecoder.decode(data["user"] ?: "", StandardCharsets.UTF_8.toString())
        val userData = objectMapper.readValue(userJson, Map::class.java) as Map<String, Any>
        val telegramId = (userData["id"] as Number).toLong()
        val username = userData["username"] as String?
        val firstName = userData["first_name"] as String?

        // Сохраняем или обновляем пользователя
        val user = userRepository.findByTelegramId(telegramId) ?: TelegramUser(telegramId)
        user.username = username
        user.firstName = firstName
        userRepository.save(user)

        // Генерируем JWT
        val token = jwtUtil.generateToken(telegramId.toString())
        return ResponseEntity.ok(mapOf("token" to token))
    }

    private fun parseInitData(initData: String): MutableMap<String, String> {
        val result = mutableMapOf<String, String>()
        initData.split("&").forEach { pair ->
            val (key, value) = pair.split("=", limit = 2)
            if (value.isNotEmpty()) {
                result[key] = value
            }
        }
        return result
    }
}
