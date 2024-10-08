package com.example.weather_kotlin_app.data.model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weather_kotlin_app.data.model.Current
import com.example.weather_kotlin_app.data.model.Location

data class WeatherModel(
    val current: Current,
    val location: Location
)



@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "temperature") val temperature: Double,
    @ColumnInfo(name = "conditionText") val conditionText: String,
    @ColumnInfo(name = "conditionIcon") val conditionIcon: String,
    @ColumnInfo(name = "humidity") val humidity: Int,
    @ColumnInfo(name = "windSpeed") val windSpeed: Double,
    @ColumnInfo(name = "windDirection") val windDirection: String,
    @ColumnInfo(name = "uv") val uv: Double,
    @ColumnInfo(name = "localTime") val localTime: String,
    @ColumnInfo(name = "localDate") val localDate: String,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "country") val country: String
)

@Entity(tableName = "location")
data class LocationEntity(
    @PrimaryKey @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "country") val country: String
)
