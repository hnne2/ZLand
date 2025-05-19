package com.gr.zland.сontroller

import com.gr.zland.model.TelegramUser
import com.gr.zland.repository.UserRepository
import com.gr.zland.servis.TelegramAuthService
import com.gr.zland.servis.myTelegramUserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
@RestController
@RequestMapping("/apiZ/auth")
class TelegramAuthController(
    private val telegramAuthService: TelegramAuthService,
    private val userService: myTelegramUserService
) {
    @Operation(summary = "Аутентификация через Telegram WebApp")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Успешная авторизация", content = [Content(mediaType = "application/json", schema = Schema(implementation = AuthResponse::class))]),
        ApiResponse(responseCode = "400", description = "Ошибка авторизации", content = [Content(mediaType = "application/json", schema = Schema(example = """{ "error": "Неверный initData" }"""))])])
    @PostMapping("/telegram")
    fun authenticate(@RequestBody request: TelegramAuthRequest): ResponseEntity<Any> {
        return try {
            println(request.initData)
            val (token, telegramUser) = telegramAuthService.authenticate(request.initData)
            val user = userService.getOrCreateUser(telegramUser)
            ResponseEntity.ok(AuthResponse(token, user))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message))
        }
    }

}
@Schema(description = "Ответ при успешной авторизации")
data class AuthResponse(
    @Schema(description = "JWT-токен", example = "eyJhbGciOiJIUzI1...")
    val token: String,

    @Schema(description = "Данные Telegram-пользователя")
    val user: TelegramUser
)

data class TelegramAuthRequest(
    val initData: String
)

