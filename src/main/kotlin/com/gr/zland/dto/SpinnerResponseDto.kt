package com.gr.zland.dto

data class SpinnerResponseDto(
    val isEnded:Boolean,
    val spinner: SpinnerData,
    val prize: PrizeData,
    val theEnd: TheEndData,
    val seo: SeoData
)

data class SpinnerData(
    val chanceToWin: Float,
    val maxSpins: Int
)

data class PrizeData(
    val image: PrizeImage,
    val title: String,
    val message: String
)

data class PrizeImage(
    val url: String,
    val alt: String
)

data class TheEndData(
    val title: String,
    val description: String
)