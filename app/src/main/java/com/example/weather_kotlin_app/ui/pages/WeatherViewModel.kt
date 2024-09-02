package com.example.weather_kotlin_app.ui.pages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_kotlin_app.api.NetworkResponse
import com.example.weather_kotlin_app.data.model.WeatherModel
import com.example.weather_kotlin_app.data.repository.WeatherRepository
import kotlinx.coroutines.launch


class WeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult: LiveData<NetworkResponse<WeatherModel>> = _weatherResult

    fun getData(city: String) {
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            val response = weatherRepository.getWeatherData(city)
            _weatherResult.value = response
        }
    }
}

