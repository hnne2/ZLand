package com.gr.zland.dto

import com.gr.zland.model.Vape

data class SeoData(val H1: String)

data class CategoryResponse(
    val seo: SeoData,
    val specification: String,
    val products: List<Vape>
)
