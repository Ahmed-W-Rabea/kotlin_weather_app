import com.example.weather_kotlin_app.Constant
import com.example.weather_kotlin_app.api.Condition
import com.example.weather_kotlin_app.api.Current
import com.example.weather_kotlin_app.api.Location
import com.example.weather_kotlin_app.api.LocationEntity
import com.example.weather_kotlin_app.api.NetworkResponse
import com.example.weather_kotlin_app.api.WeatherApi
import com.example.weather_kotlin_app.api.WeatherEntity
import com.example.weather_kotlin_app.api.WeatherModel
class WeatherRepository(private val weatherApi: WeatherApi) {

    suspend fun getWeatherData(city: String): NetworkResponse<WeatherModel> {
        return try {
            val response = weatherApi.getWeather(Constant.apiKey, city)
            if (response.isSuccessful) {
                response.body()?.let {
                    NetworkResponse.Success(it)
                } ?: NetworkResponse.Error("No data available")
            } else {
                NetworkResponse.Error("Failed to load data")
            }
        } catch (e: Exception) {
            NetworkResponse.Error("Failed to load data: ${e.message}")
        }
    }
}
