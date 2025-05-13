package com.gr.zland.repository

import com.gr.zland.model.TelegramUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<TelegramUser, Long> {
    fun findByTelegramId(telegramId: Long): TelegramUser?
}
