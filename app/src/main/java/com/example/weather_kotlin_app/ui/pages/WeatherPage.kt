package com.example.weather_kotlin_app

import WeatherViewModel
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weather_kotlin_app.api.NetworkResponse
import com.example.weather_kotlin_app.api.WeatherModel
import com.google.android.gms.location.LocationServices
import android.Manifest
import androidx.compose.ui.platform.LocalContext


@Composable
fun RequestLocationPermission(onPermissionGranted: () -> Unit) {
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                onPermissionGranted()
            }
        }
    )
    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}

@SuppressLint("MissingPermission")
fun getCurrentLocation(context: Context, onLocationReceived: (Location) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
        if (location != null) {
            onLocationReceived(location)
        }
    }
}

@Composable
fun WeatherPage(viewModel: WeatherViewModel){

    var city by remember {
        mutableStateOf("")
    }
    val weatherResult = viewModel.weatherResult.observeAsState()
    val keyboardController= LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    var location by remember { mutableStateOf<Location?>(null) }

    RequestLocationPermission {
        getCurrentLocation(context) {
            location = it
        }
    }

    location?.let {
        LaunchedEffect(it) {
            viewModel.getData("${it.latitude},${it.longitude}")
        }
        Text(text = "Latitude: ${it.latitude}, Longitude: ${it.longitude}")
    } ?: Text(text = "Fetching location...", textAlign = TextAlign.Center)

    Column (modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)){
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween){
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value =  city, onValueChange ={city = it},
            label = {
                Text(text = "search for any location")            })

            IconButton(onClick = { viewModel.getData(city)
            keyboardController?.hide()}) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search for any location" )


                }
        }

        when(val result = weatherResult.value){
            is NetworkResponse.Error -> {
                Text(text = result.message)
            }
            NetworkResponse.Loading -> {

                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }            }
            is NetworkResponse.Success -> {
                WeatherDetails(data = result.data)
            }
            null -> {

            }
        }
    }
}
@Composable
fun WeatherDetails(data: WeatherModel){
Column (modifier = Modifier
    .fillMaxWidth()
    .padding(vertical = 12.dp),
    horizontalAlignment = Alignment.CenterHorizontally){
    Row(modifier = Modifier
        .fillMaxWidth()
        ,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom) {
        Icon(imageVector = Icons.Default.LocationOn, contentDescription ="Location icon",
            modifier = Modifier.size(40.dp))
        Text(text = data.location.name, fontSize = 30.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = data.location.country, fontSize = 18.sp , color = Color.Gray)


    }
    Spacer(modifier = Modifier.height(16.dp))
    Text(text = "${data.current.temp_c} °c" , fontSize = 56.sp , fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
    AsyncImage( modifier = Modifier.size(160.dp),model = "https:${data.current.condition.icon}".replace("64*64" , "128*128"), contentDescription = "Condition ivon" )
    Text(text = data.current.condition.text, fontSize = 24.sp ,  textAlign = TextAlign.Center  , fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(16.dp))
    Card {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround) {
                WeatherKeyValue("Humidity" , data.current.humidity.toString())
                WeatherKeyValue("Wind Speed" , data.current.wind_kph.toString()+ " km/h")


            }
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround) {
                WeatherKeyValue("UV" , data.current.uv.toString())
                WeatherKeyValue("Wind Direction" , data.current.wind_dir)


            }
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround) {
                WeatherKeyValue("Local Time" , data.location.localtime.split(" ")[1])
                WeatherKeyValue("Local Date" , data.location.localtime.split(" ")[0])


            }

        }

    }

}
}
@Composable
fun WeatherKeyValue(key: String , value: String){
    Column(modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value , fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = key , fontWeight = FontWeight.SemiBold , color = Color.Gray)
    }
}