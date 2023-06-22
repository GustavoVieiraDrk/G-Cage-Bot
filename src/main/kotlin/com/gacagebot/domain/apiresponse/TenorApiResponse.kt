package com.gacagebot.domain.apiresponse

import com.gacagebot.domain.apiresult.TenorResult

data class TenorApiResponse(
    val results: List<TenorResult>,
    val next: String?
)