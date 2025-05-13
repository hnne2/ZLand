package com.gr.zland.model

import jakarta.persistence.*

@Entity
@Table(name = "partner")
data class Partner(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "name", nullable = false, length = 255)
    val name: String,

    @Column(name = "phone", nullable = false, length = 20)
    val tel: String,

    @Column(name = "email", length = 255)
    val email: String? = null,

    @Column(name = "telegram_nick", length = 100)
    val telegram: String? = null,

    @Column(name = "message", columnDefinition = "TEXT")
    val comment: String? = null,

    @Column(name = "created_at", nullable = false)
    val createdAt: Long,

    @Column(name = "updated_at", nullable = false)
    val updatedAt: Long
)
