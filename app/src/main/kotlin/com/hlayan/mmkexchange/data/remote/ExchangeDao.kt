package com.hlayan.mmkexchange.data.remote

import com.hlayan.mmkexchange.LatestRates
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface ExchangeDao {

    @GET("latest")
    suspend fun syncLatestRates(): ApiResponse<LatestRates>

    companion object {
        val Instance: ExchangeDao = retrofit.create(ExchangeDao::class.java)

        private val retrofit: Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
                .build()

        private const val BASE_URL = "https://forex.cbm.gov.mm//api/"

        private val okHttpClient
            get() = OkHttpClient().newBuilder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build()
    }
}