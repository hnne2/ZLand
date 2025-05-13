package com.gr.zland.model

import jakarta.persistence.*

@Entity
@Table(name = "raffle_settings")
data class RaffleSettings(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "probability", nullable = false)
    val probability: Float = 0.1f,

    @Column(name = "prize", nullable = false, columnDefinition = "TEXT")
    val prize: String,

    @Column(name = "prize_image_path", length = 255)
    val prizeImagePath: String? = null,

    @Column(name = "pickup_location", nullable = false, length = 255)
    val pickupLocation: String,

    @Column(name = "max_prizes", nullable = false)
    var maxPrizes: Int = 10,

    @Column(name = "latitude")
    val latitude: Float? = null,

    @Column(name = "longitude")
    val longitude: Float? = null,

    @Column(name = "created_at", nullable = false)
    val createdAt: Long,

    @Column(name = "updated_at", nullable = false)
    val updatedAt: Long,

    @Version
    @Column(name = "version")
    var version: Long? = null
)
