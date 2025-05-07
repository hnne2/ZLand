package com.gr.zland.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
data class TelegramUser(
    @Id
    val telegramId: Long,
    var username: String? = null,
    var firstName: String? = null,
    var spins: Int?=null
)
