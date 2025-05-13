package com.gr.zland.dto

data class CatalogResponse(
    val seo: SeoDto,
    val specification: String,
    val products: List<ProductDto>
)

data class SeoDto(val H1: String)

data class ImageDto(
    val url: String,
    val alt: String
)

data class ParameterDto(
    val id: Long,
    val label: String,
    val value: String
)


data class ProductDto(
    val id: Long,
    val label: String,
    val sort: String,
    val flavorList: String,
    val sweetness: Float,
    val iceLevel: Float,
    val sourness: Float,
    val image: ImageDto,
    val isTop: Boolean,
    val parameters: List<ParameterDto>
)