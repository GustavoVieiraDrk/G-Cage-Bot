package com.gacagebot.domain.apiresult

import com.gacagebot.domain.models.TenorMediaFormats

class TenorResult(
    val id: String,
    val title: String,
    val media_formats: TenorMediaFormats,
    val created: Double,
    val contentDescription: String,
    val itemUrl: String,
    val url: String,
    val tags: List<String>,
    val flags: List<String>,
    val hasAudio: Boolean
)