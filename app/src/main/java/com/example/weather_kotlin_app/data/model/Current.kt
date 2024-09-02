package com.example.weather_kotlin_app.data.model


data class Current(
    val condition: Condition,
    val humidity: String,
    val temp_c: String,
    val uv: String,
    val wind_dir: String,
    val wind_kph: String
) {
}