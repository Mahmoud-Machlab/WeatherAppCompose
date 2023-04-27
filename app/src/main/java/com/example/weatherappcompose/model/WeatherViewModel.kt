package com.example.weatherappcompose.model

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappcompose.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(val app: Context) : ViewModel() {

    val weatherRepository = WeatherRepository(app)

    val _weatherData = MutableLiveData<WeatherData>()
    val weatherData: LiveData<WeatherData> = _weatherData

    val _weatherImage = MutableLiveData<Bitmap>()
    val weatherImage: LiveData<Bitmap> = _weatherImage

    fun getWeatherData(city: String) {

        viewModelScope.launch {
            _weatherData.value = weatherRepository.getWeater(city)
        }
    }

    fun getImage() {
        viewModelScope.launch {
            weatherRepository.getImage(_weatherImage, weatherData.value!!)
        }
    }
}
