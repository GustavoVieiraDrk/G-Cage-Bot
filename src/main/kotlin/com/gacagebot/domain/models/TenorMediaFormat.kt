package com.gacagebot.domain.models

data class TenorMediaFormat(
    val url: String,
    val duration: Double,
    val preview: String,
    val dims: List<Int>,
    val size: Int
)