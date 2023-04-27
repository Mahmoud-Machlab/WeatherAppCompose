package com.example.weatherappcompose.repository

import androidx.compose.runtime.derivedStateOf
import com.example.weatherappcompose.model.WeatherData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

interface WeatherEndpointInterface {

    @GET("weather?")
    suspend fun getWeatherData(
        @Query("q") city:String,
        @Query("lang") lang: String = "de",
        @Query("appid") id: String
    ):Response<WeatherData>

    @GET
    @Streaming
    suspend fun loadImage(@Url url:String): Call<ResponseBody>
}