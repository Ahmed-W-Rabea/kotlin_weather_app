package com.example.weather_kotlin_app.api

import androidx.room.Entity
import androidx.room.PrimaryKey

data class WeatherModel(
    val current: Current,
    val location: Location,

)


@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey val id: Int,
    val temperature: Float,
    val conditionText: String,
    val conditionIcon: String,
    val humidity: Float,
    val windSpeed: Float,
    val windDirection: String,
    val uv: Float,
    val localTime: String,
    val localDate: String,
    val city: String,
    val country: String
)



@Entity(tableName = "location")
data class LocationEntity(
    @PrimaryKey val city: String,
    val country: String
)
