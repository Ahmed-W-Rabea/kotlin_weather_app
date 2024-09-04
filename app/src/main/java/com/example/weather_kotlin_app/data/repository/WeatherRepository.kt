package com.example.weather_kotlin_app.data.repository

import android.util.Log
import com.example.weather_kotlin_app.Constant
import com.example.weather_kotlin_app.api.NetworkResponse
import com.example.weather_kotlin_app.data.api.RetrofitInstance.weatherApi
import com.example.weather_kotlin_app.data.api.WeatherApi
import com.example.weather_kotlin_app.data.api.WeatherDao

import com.example.weather_kotlin_app.data.model.Condition
import com.example.weather_kotlin_app.data.model.Current
import com.example.weather_kotlin_app.data.model.Location
import com.example.weather_kotlin_app.data.model.LocationEntity
import com.example.weather_kotlin_app.data.model.WeatherEntity
import com.example.weather_kotlin_app.data.model.WeatherModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
class WeatherRepository(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao
) {
    suspend fun getWeatherData(city: String): NetworkResponse<WeatherModel> {
        return try {
            val response = weatherApi.getWeather(Constant.apiKey, city)
            if (response.isSuccessful) {
                response.body()?.let {
                    val weatherEntity = WeatherEntity(
                        temperature = it.current.temp_c,
                        conditionText = it.current.condition.text,
                        conditionIcon = it.current.condition.icon,
                        humidity = it.current.humidity,
                        windSpeed = it.current.wind_kph,
                        windDirection = it.current.wind_dir,
                        uv = it.current.uv,
                        localTime = it.location.localtime,
                        localDate = it.location.localtime,
                        city = it.location.name,
                        country = it.location.country
                    )

                    val locationEntity = LocationEntity(
                        city = it.location.name,
                        country = it.location.country
                    )

                    withContext(Dispatchers.IO) {
                        try {
                            weatherDao.insertWeather(weatherEntity)
                            Log.d("WeatherRepository", "Weather data inserted successfully")
                        } catch (e: Exception) {
                            Log.e("WeatherRepository", "Error inserting weather data: ${e.message}", e)
                        }

                        try {
                            weatherDao.insertLocation(locationEntity)
                            Log.d("WeatherRepository", "Location data inserted successfully")
                        } catch (e: Exception) {
                            Log.e("WeatherRepository", "Error inserting location data: ${e.message}", e)
                        }
                    }

                    NetworkResponse.Success(it)
                } ?: NetworkResponse.Error("No data available")
            } else {
                NetworkResponse.Error("Failed to load data")
            }
        } catch (e: Exception) {
            Log.e("WeatherRepository", "Exception: ${e.message}", e)
            val weatherEntity = weatherDao.getWeather(city).value
            val locationEntity = weatherDao.getLocation(city).value
            if (weatherEntity != null && locationEntity != null) {
                NetworkResponse.Success(
                    WeatherModel(
                        current = Current(
                            temp_c = weatherEntity.temperature,
                            condition = Condition(
                                text = weatherEntity.conditionText,
                                icon = weatherEntity.conditionIcon
                            ),
                            humidity = weatherEntity.humidity,
                            wind_kph = weatherEntity.windSpeed,
                            wind_dir = weatherEntity.windDirection,
                            uv = weatherEntity.uv
                        ),
                        location = Location(
                            name = locationEntity.city,
                            country = locationEntity.country,
                            localtime = weatherEntity.localTime
                        )
                    )
                )
            } else {
                NetworkResponse.Error("Failed to load data from database: ${e.message}")
            }
        }
    }
}
