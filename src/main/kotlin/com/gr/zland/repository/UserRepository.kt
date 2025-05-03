package com.gr.zland.repository

import com.gr.zland.model.TelegramUser
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<TelegramUser, Long> {
    fun findByTelegramId(telegramId: Long): TelegramUser?
}
