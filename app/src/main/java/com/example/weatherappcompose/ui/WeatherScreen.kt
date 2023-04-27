package com.example.weatherappcompose.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.weatherappcompose.R
import com.example.weatherappcompose.model.WeatherViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    var city = remember { mutableStateOf("") }
    val weatherData by viewModel.weatherData.observeAsState()
    val weatherImage by viewModel.weatherImage.observeAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        OutlinedTextField(
            value = city.value,
            onValueChange = { city.value = it },
            label = { Text("Stadt eingeben") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, end = 8.dp)
        )

        Button(
            onClick = {
                viewModel.getWeatherData(city.value)
                Thread.sleep(2000)
                Log.d("TAG", "WeatherScreen: ${viewModel.weatherData.value}")
             //   viewModel.getImage()
                city.value = ""
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 16.dp, end = 16.dp)
        ) {
            Text(text = "Wetter")
        }

        Row(
            modifier = Modifier
                .padding(top = 24.dp)
                .align(Alignment.Start)
        ) {
            AsyncImage(
                model = "https://openweathermap.org/img/w/${viewModel.weatherData.value?.info?.get(0)?.icon}.png",
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
            )

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = weatherData?.temp?.let {
                        viewModel.app.getString(
                            R.string.temp_template,
                            weatherData?.temp?.temp?.toInt()
                        )
                    } ?: "",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = weatherData?.info?.get(0)?.description ?: "",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}
