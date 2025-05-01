package com.gr.zland.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalTime

@Entity
@Table(name = "winner")
data class Winner(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "phone", nullable = false, length = 20)
    val phone: String,

    @Column(name = "telegram_nick", nullable = false, length = 100)
    val telegramNick: String,

    @Column(name = "date", nullable = false)
    val date: LocalDate,

    @Column(name = "time", nullable = false)
    val time: LocalTime,

    @Column(name = "prize", nullable = false, length = 255)
    val prize: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: Long,

    @Column(name = "updated_at", nullable = false)
    val updatedAt: Long
)
