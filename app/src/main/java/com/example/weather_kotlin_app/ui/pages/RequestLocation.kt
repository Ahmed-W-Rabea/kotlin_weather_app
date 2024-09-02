//import android.Manifest
//import android.annotation.SuppressLint
//import android.content.Context
//import android.location.Location
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.material3.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.platform.LocalContext
//import com.google.android.gms.location.LocationServices
//
//@Composable
//fun com.example.weather_kotlin_app.ui.pages.RequestLocationPermission(onPermissionGranted: () -> Unit) {
//    val permissionLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestPermission(),
//        onResult = { isGranted ->
//            if (isGranted) {
//                onPermissionGranted()
//            }
//        }
//    )
//    LaunchedEffect(Unit) {
//        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//    }
//}
//
//@SuppressLint("MissingPermission")
//fun com.example.weather_kotlin_app.ui.pages.getCurrentLocation(context: Context, onLocationReceived: (Location) -> Unit) {
//    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
//    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
//        location?.let {
//            onLocationReceived(it)
//        }
//    }
//}
//
//@Composable
//fun LocationScreen() {
//    val context = LocalContext.current
//    var location by remember { mutableStateOf<Location?>(null) }
//
//    com.example.weather_kotlin_app.ui.pages.RequestLocationPermission {
//        com.example.weather_kotlin_app.ui.pages.getCurrentLocation(context) {
//            location = it
//        }
//    }
//
//    location?.let {
//        Text(text = "Latitude: ${it.latitude}, Longitude: ${it.longitude}")
//    } ?: Text(text = "Fetching location...")
//}
