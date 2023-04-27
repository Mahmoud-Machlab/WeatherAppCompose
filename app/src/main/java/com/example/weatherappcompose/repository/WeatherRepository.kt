package com.example.weatherappcompose.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.weatherappcompose.R
import com.example.weatherappcompose.model.WeatherData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository(val app:Context) {

    private val BASE_URL_STRING = "https://api.openweathermap.org/data/2.5/"
    private val IMAGE_URL_STRING = "https://openweathermap.org/img/w/"
    private val FILE_EXT = ".png"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_STRING)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(WeatherEndpointInterface::class.java)

    suspend fun getWeater(city:String):WeatherData?{
        val appid = app.getString(R.string.api_key)
        val response = apiService.getWeatherData(city, id =  appid)
        if (response.isSuccessful){
            Log.d("TAG", "getWeater: response vorhanden")
            return response.body()
        }
        Log.d("TAG", "getWeater: response nicht vorhanden")
        return null
    }

    suspend fun getImage(liveData: MutableLiveData<Bitmap>, weatherData: WeatherData){
        Log.d("TAG", "getImage: " + "$IMAGE_URL_STRING${weatherData.info[0].icon}$FILE_EXT")
        val imageCall = apiService.loadImage("$IMAGE_URL_STRING${weatherData.info[0].icon}$FILE_EXT")
        imageCall.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
               if (response.isSuccessful){
                   val bmp = BitmapFactory.decodeStream(response.body()?.byteStream())
                   liveData.postValue(bmp)
               }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("TAG", "onFailure: Bild konnte nicht geladen werden",t )
            }
        })
    }
}