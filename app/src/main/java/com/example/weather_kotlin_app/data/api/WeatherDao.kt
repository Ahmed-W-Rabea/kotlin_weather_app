package com.example.weather_kotlin_app.data.api

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

import androidx.room.*
import com.example.weather_kotlin_app.data.model.LocationEntity
import com.example.weather_kotlin_app.data.model.WeatherEntity


@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weatherEntity: WeatherEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(locationEntity: LocationEntity)

    @Query("SELECT * FROM weather WHERE city = :city")
    suspend fun getWeather(city: String): WeatherEntity?

    @Query("SELECT * FROM location WHERE city = :city")
    suspend fun getLocation(city: String): LocationEntity?
}
