package com.example.weatherappcompose.model


import android.icu.text.IDNA
import com.google.gson.annotations.SerializedName
import java.time.temporal.Temporal

class WeatherData(
    @SerializedName("name") val city: String,
    @SerializedName("main") val temp: Temp,
    @SerializedName("weather") val info:Array<Info>
)

class Info (
    val description:String,
    val icon: String
)

class Temp(
    val temp: Double
)
