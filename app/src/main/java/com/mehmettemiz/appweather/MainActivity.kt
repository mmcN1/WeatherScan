package com.mehmettemiz.appweather

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.pdf.models.ListItem
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.BottomAppBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.libraries.places.api.Places
import com.mehmettemiz.appweather.factory.SearchViewModelFactory
import com.mehmettemiz.appweather.model.Forecast.ForecastList
import com.mehmettemiz.appweather.model.WeatherModel
import com.mehmettemiz.appweather.screen.LocationInfo
import com.mehmettemiz.appweather.screen.SearchScreen
import com.mehmettemiz.appweather.ui.theme.AppWeatherTheme
import com.mehmettemiz.appweather.ui.theme.bgColor
import com.mehmettemiz.appweather.viewmodel.SearchViewModel
import com.mehmettemiz.appweather.viewmodel.WeatherViewModel

class MainActivity : ComponentActivity() {
    private val viewModel : WeatherViewModel by viewModels<WeatherViewModel>()
    private lateinit var searchViewModel: SearchViewModel

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel.getLocation(this, this)
        println("viewModel.getLocation(this, this)")

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, "AIzaSyD8EWmmzViIz9m6oMO3IHGzUbDhcBQFQ_0")
        }

        searchViewModel = ViewModelProvider(this, SearchViewModelFactory(applicationContext)) [SearchViewModel::class.java]

        setContent {
            val weatherList by viewModel.weatherList.observeAsState(emptyList())
            val forecastTodayList by viewModel.forecastTodayList.observeAsState(emptyList())
            val forecastWeatherList by viewModel.forecastWeatherList.observeAsState(emptyMap())
            val customBackground by viewModel.backgroundColor.observeAsState(emptyList())
            val searchWeatherList by searchViewModel.searchWeatherList.observeAsState(emptyList())
            val searchForecastTodayList by searchViewModel.searchForecastTodayList.observeAsState(emptyList())
            val searchForecastWeatherList by searchViewModel.searchForecastWeatherList.observeAsState(emptyMap())
            val searchCustomBackground by viewModel.backgroundColor.observeAsState(emptyList())



            val navController = rememberNavController()

            AppWeatherTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomAppBar(
                            actions = {
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    IconButton(onClick = { navController.navigate("LocationInfo") }) {
                                        Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Location")
                                    }
                                    IconButton(onClick = { navController.navigate("Search") }) {
                                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                                    }
                                }
                            },
                            containerColor = Color.DarkGray,
                            contentColor = Color.White,
                        )
                    }
                ) {innerPadding ->
                    Box(modifier = Modifier.fillMaxSize()) { // Tam ekran kullanım
                        NavHost(navController = navController, startDestination = "SplashScreen") {
                            composable("SplashScreen") {
                                SplashScreen(navController, weatherList)
                            }
                            composable("LocationInfo") {
                                if (weatherList.isNotEmpty()) {
                                    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)
                                        , contentAlignment = Alignment.Center) {
                                        LocationInfo(
                                            weatherList = weatherList,
                                            forecastTodayList = forecastTodayList,
                                            forecastWeatherList = forecastWeatherList,
                                            viewModel = viewModel,
                                            customBackground = customBackground
                                        )
                                    }
                                } else {
                                    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)
                                        , contentAlignment = Alignment.Center) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }
                            composable("Search") {
                                Box(modifier = Modifier.fillMaxSize().padding(bottom = 100.dp)
                                    , contentAlignment = Alignment.Center) {
                                    SearchScreen(
                                        viewModel = searchViewModel,
                                        weatherList = searchWeatherList,
                                        forecastTodayList = searchForecastTodayList,
                                        forecastWeatherList = searchForecastWeatherList,
                                        customBackground = searchCustomBackground
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)} passing\n      in a {@link RequestMultiplePermissions} object for the {@link ActivityResultContract} and\n      handling the result in the {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 101) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.getLocation(this, this)
                println("onLocationResult")
            } else {
                println("Failed")
            }
        }
    }
}

@Composable
fun SplashScreen(
    navController: NavController,
    weatherList: List<WeatherModel>,
) {
    val versionName = getAppVersionName()

    LaunchedEffect(weatherList) {
        if (weatherList.isNotEmpty()) {
            navController.navigate("LocationInfo") {
                popUpTo("SplashScreen") { inclusive = true } // Splash ekranını geri yığından kaldır
            }
        }
    }

    // Splash ekranı içeriği
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = "App logo")
            Text(text = versionName)
        }
    }
}

@Composable
fun getAppVersionName(): String {
    val context = LocalContext.current
    val packageManager: PackageManager = context.packageManager
    val packageInfo: PackageInfo = packageManager.getPackageInfo(context.packageName, 0)
    return packageInfo.versionName ?: "Unknown"
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppWeatherTheme {
    }
}