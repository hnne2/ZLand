package com.gr.zland.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalTime

@Entity
@Table(name = "vape")
data class Vape(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "sort", nullable = false)
    val sort: String,

    @Column(name = "flavor_list", nullable = false)
    val flavorList: String,

    @Column(name = "sweetness", nullable = false)
    val sweetness: Float,

    @Column(name = "ice_level", nullable = false)
    val iceLevel: Float,

    @Column(name = "sourness", nullable = false)
    val sourness: Float,

    @Column(name = "image_path", length = 255)
    val imagePath: String? = null
)
