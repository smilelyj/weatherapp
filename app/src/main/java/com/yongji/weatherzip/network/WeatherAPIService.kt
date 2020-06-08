package com.yongji.weatherzip.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://weather.ls.hereapi.com/weather/1.0/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

/**
 * A public interface that exposes the [getWeatherResponse] method
 */
interface WeatherAPIService {
    /**
     * Returns a Coroutine [Deferred] [WeatherResponse] which can be fetched with await() if
     * in a Coroutine scope.
     * The @GET annotation indicates that the "report.json" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("report.json")
    fun getWeatherResponse(
        @Query("product") product: String,
        @Query("zipcode") zipcode: String,
        @Query("oneobservation") oneobservation: String,
        @Query("apiKey") apiKey: String
    ): Deferred<WeatherResponse>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object WeatherApi {
    val retrofitService : WeatherAPIService by lazy { retrofit.create(WeatherAPIService::class.java) }
}