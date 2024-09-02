//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.Query
//import androidx.room.Transaction
//import com.example.weather_kotlin_app.api.LocationEntity
//import com.example.weather_kotlin_app.api.WeatherEntity
//
//@Dao
//interface WeatherDao {
//
//    @Insert
//    suspend fun insertWeather(weather: WeatherEntity)
//
//    @Insert
//    suspend fun insertLocation(location: LocationEntity)
//
//    @Query("SELECT * FROM weather WHERE city = :city")
//    suspend fun getWeatherByCity(city: String): WeatherEntity?
//
//    @Query("SELECT * FROM location WHERE city = :city")
//    suspend fun getLocationByCity(city: String): LocationEntity?
//}
