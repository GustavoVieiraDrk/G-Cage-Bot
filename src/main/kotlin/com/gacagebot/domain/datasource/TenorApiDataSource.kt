package com.gacagebot.domain.datasource

import com.gacagebot.constants.Secrets
import com.gacagebot.domain.apis.TenorApiService
import com.gacagebot.domain.models.TenorMediaFormats
import com.gacagebot.domain.repositories.TenorApiRepository
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object TenorApiDataSource : TenorApiRepository{

    private var tenorApiService: TenorApiService
    private const val BASE_TENOR_URL_API = "https://tenor.googleapis.com/v2/"

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_TENOR_URL_API)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        tenorApiService = retrofit.create(TenorApiService::class.java)
    }

    override fun fetchTenorGif(query: String, limit: Int, random: Boolean): Single<TenorMediaFormats> {
        return tenorApiService.getRandomGif(query, Secrets.TENOR_API_KEY.getString ,limit, random)
            .map { response ->
                response.results.firstOrNull()?.media_formats!!
            }
    }

}