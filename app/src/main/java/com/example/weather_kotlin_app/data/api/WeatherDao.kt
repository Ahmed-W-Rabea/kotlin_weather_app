package com.example.weather_kotlin_app.data.api

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns

import com.example.weather_kotlin_app.data.model.LocationEntity
import com.example.weather_kotlin_app.data.model.WeatherEntity

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertWeather(weatherEntity: WeatherEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertLocation(locationEntity: LocationEntity)

    @Query("SELECT * FROM weather WHERE city = :city")
    fun getWeather(city: String): LiveData<WeatherEntity?>

    @Query("SELECT * FROM location WHERE city = :city")
    fun getLocation(city: String): LiveData<LocationEntity?>

}
