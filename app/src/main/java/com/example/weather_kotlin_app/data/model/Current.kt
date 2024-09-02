package com.example.weather_kotlin_app.api

data class Current(
    val condition: Condition,
    val feelslike_c: String,
    val feelslike_f: String,
    val gust_kph: String,
    val heatindex_c: String,
    val humidity: String,
    val is_day: String,
    val last_updated_epoch: String,
    val precip_in: String,
    val precip_mm: String,
    val pressure_in: String,
    val pressure_mb: String,
    val temp_c: String,
    val uv: String,
    val wind_dir: String,
    val wind_kph: String
)