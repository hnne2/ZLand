package com.gr.zland.model

import jakarta.persistence.*

@Entity
@Table(name = "pickup_location")
data class PickupLocation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "name", nullable = false, length = 255)
    val name: String,

    @Column(name = "address", nullable = false, length = 255)
    val address: String,

    @Column(name = "latitude")
    val latitude: Float? = null,

    @Column(name = "longitude")
    val longitude: Float? = null,

    @Column(name = "created_at", nullable = false)
    val createdAt: Long,

    @Column(name = "updated_at", nullable = false)
    val updatedAt: Long
)
