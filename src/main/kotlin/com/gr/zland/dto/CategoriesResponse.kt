package com.gr.zland.dto

data class CategoryDto(
    val id: Long,
    val icon: IconDto,
    val link: LinkDto
)

data class IconDto(
    val url: String,
    val alt: String
)

data class LinkDto(
    val to: String,
    val label: String
)