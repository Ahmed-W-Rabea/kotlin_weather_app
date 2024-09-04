package com.example.weather_kotlin_app.data.model


data class Current(
    val condition: Condition,
    val humidity: Int,
    val temp_c: Double,
    val uv: Double,
    val wind_dir: String,
    val wind_kph: Double
) {
}