package com.gacagebot.domain.repositories

import com.gacagebot.domain.models.TenorMediaFormats
import io.reactivex.rxjava3.core.Single

interface TenorApiRepository {

    fun fetchTenorGif(query: String, limit: Int, random: Boolean): Single<TenorMediaFormats>

}