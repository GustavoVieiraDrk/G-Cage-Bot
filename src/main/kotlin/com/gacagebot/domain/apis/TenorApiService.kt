package com.gacagebot.domain.apis

import com.gacagebot.constants.Secrets
import com.gacagebot.domain.apiresponse.TenorApiResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TenorApiService {

    @GET("search")
    fun getRandomGif(
        @Query("q") query: String,
        @Query("key") apiKey: String = Secrets.TENOR_API_KEY.getString,
        @Query("limit") limit: Int,
        @Query("random") random: Boolean
    ): Single<TenorApiResponse>
}
