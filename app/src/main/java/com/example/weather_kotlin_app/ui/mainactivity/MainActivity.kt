package com.example.weather_kotlin_app

import com.example.weather_kotlin_app.data.repository.WeatherRepository
import com.example.weather_kotlin_app.ui.pages.WeatherViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.weather_kotlin_app.data.api.RetrofitInstance
import com.example.weather_kotlin_app.data.api.WeatherDatabase
import com.example.weather_kotlin_app.ui.pages.WeatherPage
import com.example.weather_kotlin_app.ui.pages.WeatherViewModelFactory
import com.example.weather_kotlin_app.ui.theme.Weather_kotlin_appTheme
class MainActivity : ComponentActivity() {
    private lateinit var viewModel: WeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val weatherDatabase = WeatherDatabase.getDatabase(this)
            weatherDatabase.logTableSchema()
        val weatherRepository = WeatherRepository(RetrofitInstance.weatherApi, weatherDatabase.weatherDao())
        val viewModelFactory = WeatherViewModelFactory(weatherRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(WeatherViewModel::class.java)

        setContent {
            Weather_kotlin_appTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) { WeatherPage(viewModel) }
            }
        }
    }
}
