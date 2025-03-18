package com.mehmettemiz.appweather.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.model.kotlin.place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.mehmettemiz.appweather.model.Forecast.ForecastList
import com.mehmettemiz.appweather.model.WeatherModel
import com.mehmettemiz.appweather.network.BaseWeatherService
import com.mehmettemiz.appweather.service.WeatherService
import com.mehmettemiz.appweather.ui.theme.clearColor1
import com.mehmettemiz.appweather.ui.theme.clearColor2
import com.mehmettemiz.appweather.ui.theme.cloudsColor1
import com.mehmettemiz.appweather.ui.theme.cloudsColor2
import com.mehmettemiz.appweather.ui.theme.drizzleColor1
import com.mehmettemiz.appweather.ui.theme.mistColor1
import com.mehmettemiz.appweather.ui.theme.mistColor2
import com.mehmettemiz.appweather.ui.theme.mistColor3
import com.mehmettemiz.appweather.ui.theme.rainColor1
import com.mehmettemiz.appweather.ui.theme.rainColor2
import com.mehmettemiz.appweather.ui.theme.snowColor1
import com.mehmettemiz.appweather.ui.theme.snowColor2
import com.mehmettemiz.appweather.ui.theme.snowColor3
import com.mehmettemiz.appweather.ui.theme.thunderstormColor1
import com.mehmettemiz.appweather.ui.theme.thunderstormColor2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale

class SearchViewModel(context: Context) : ViewModel() {
    var inputText by mutableStateOf("")
        private set
    var cityList by mutableStateOf(mutableListOf<Pair<String, String>>())
        private set
    var selectedLatLng by mutableStateOf<Pair<Double, Double>?>(null)

    private val _searchWeatherList = MutableLiveData<List<WeatherModel>>()
    val searchWeatherList: LiveData<List<WeatherModel>> = _searchWeatherList
    private val _searchForecastTodayList = MutableLiveData<List<ForecastList>>()
    val searchForecastTodayList: LiveData<List<ForecastList>> = _searchForecastTodayList
    private val _searchForecastWeatherList = MutableLiveData<Map<String, List<ForecastList>>>()
    val searchForecastWeatherList: LiveData<Map<String, List<ForecastList>>> get() = _searchForecastWeatherList
    val backgroundColor = MutableLiveData<List<Color>>()

    private val placesClient: PlacesClient = Places.createClient(context)
    private val retrofit: WeatherService = BaseWeatherService.createService(WeatherService::class.java)

    fun onInputChange(value: String) {
        inputText = value
        if (value.isEmpty()) {
            cityList = mutableListOf()
            return
        }

        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(value)
            .setTypesFilter(
                listOf(
                    PlaceTypes.COUNTRY,
                    PlaceTypes.ADMINISTRATIVE_AREA_LEVEL_1,
                    PlaceTypes.ADMINISTRATIVE_AREA_LEVEL_2,
                    PlaceTypes.ADMINISTRATIVE_AREA_LEVEL_3
                    )
            )
            .build()

        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response ->
                cityList = response.autocompletePredictions.map {
                    it.getPrimaryText(null).toString() to it.placeId
                }.toMutableList()
            }
            .addOnFailureListener {
                cityList = mutableListOf()
                println("Hata oluştu")
            }
    }

    fun getDayNameByDate(dateString: String, format: String = "yyyy-MM-dd"): String {
        val formatter = DateTimeFormatter.ofPattern(format)
        val localDate = LocalDate.parse(dateString, formatter)

        return localDate.dayOfWeek.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())
    }


    private fun fetchWeather(cityName: String? = null, latitude: Double? = null, longitude: Double? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // İlk olarak cityName ile hava durumu verisi almayı deneyelim
                val response = retrofit.getWeatherByCityName(cityName ?: "", "metric")

                if (response.isSuccessful && response.body() != null) {
                    response.body()?.let { body ->
                        // Ensure weather is not empty
                        val weatherList = body.weather
                        if (weatherList.isNotEmpty()) {
                            // Proceed only if the weather list is non-empty
                            withContext(Dispatchers.Main) {
                                _searchWeatherList.postValue(listOf(body))

                                // Set background colors based on weather conditions
                                val weatherMain = weatherList[0].main
                                when (weatherMain) {
                                    "Clear" -> backgroundColor.value = listOf(clearColor1, clearColor2)
                                    "Snow" -> backgroundColor.value = listOf(snowColor1, snowColor2, snowColor3)
                                    "Rain" -> backgroundColor.value = listOf(rainColor1, rainColor2)
                                    "Thunderstorm" -> backgroundColor.value = listOf(thunderstormColor1, thunderstormColor2)
                                    "Clouds" -> backgroundColor.value = listOf(cloudsColor1, cloudsColor2)
                                    "Drizzle" -> backgroundColor.value = listOf(drizzleColor1)
                                    else -> backgroundColor.value = listOf(mistColor1, mistColor2, mistColor3)
                                }
                            }
                        } else {
                            // Handle case where weather list is empty
                            println("Weather data is empty for $cityName")
                            fetchWeatherByLocation(latitude, longitude)
                        }
                    }
                } else {
                    // Eğer cityName ile veri gelmediyse veya response başarısız olduysa, latitude ve longitude ile dene
                    println("CityName not found or response failed, trying by location")
                    fetchWeatherByLocation(latitude, longitude)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle error case
                withContext(Dispatchers.Main) {
                    _searchWeatherList.postValue(emptyList())
                }
            }
        }
    }

    private suspend fun fetchWeatherByLocation(latitude: Double?, longitude: Double?) {
        if (latitude != null && longitude != null) {
            try {
                val response = retrofit.getWeatherByLocation(latitude, longitude, "metric")
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.let { body ->
                        // Ensure weather is not empty
                        val weatherList = body.weather
                        if (weatherList.isNotEmpty()) {
                            // Proceed only if the weather list is non-empty
                            withContext(Dispatchers.Main) {
                                _searchWeatherList.postValue(listOf(body))

                                // Set background colors based on weather conditions
                                val weatherMain = weatherList[0].main
                                when (weatherMain) {
                                    "Clear" -> backgroundColor.value = listOf(clearColor1, clearColor2)
                                    "Snow" -> backgroundColor.value = listOf(snowColor1, snowColor2, snowColor3)
                                    "Rain" -> backgroundColor.value = listOf(rainColor1, rainColor2)
                                    "Thunderstorm" -> backgroundColor.value = listOf(thunderstormColor1, thunderstormColor2)
                                    "Clouds" -> backgroundColor.value = listOf(cloudsColor1, cloudsColor2)
                                    "Drizzle" -> backgroundColor.value = listOf(drizzleColor1)
                                    else -> backgroundColor.value = listOf(mistColor1, mistColor2, mistColor3)
                                }
                            }
                        } else {
                            // Handle case where weather list is empty
                            println("Weather data is empty for location")
                            withContext(Dispatchers.Main) {
                                _searchWeatherList.postValue(emptyList())
                            }
                        }
                    }
                } else {
                    // Handle response error
                    println("Response Error: ${response.code()}")
                    withContext(Dispatchers.Main) {
                        _searchWeatherList.postValue(emptyList())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle error case
                withContext(Dispatchers.Main) {
                    _searchWeatherList.postValue(emptyList())
                }
            }
        } else {
            println("Invalid location coordinates")
            withContext(Dispatchers.Main) {
                _searchWeatherList.postValue(emptyList())
            }
        }
    }

    fun fetchForecast(lat: Double, lon: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val todayDate = dateFormatter.format(Date())

            try {
                val response = retrofit.getForecastByLocation(lat, lon)
                if (response.isSuccessful) {
                    response.body()?.let { forecastModel ->

                        val groupedForecast = forecastModel.list.groupBy { item ->
                            item.dt_txt.substring(0, 10)
                        }

                        val todayForecast = groupedForecast[todayDate] ?: emptyList()
                        val futureForecast = groupedForecast.filterKeys { it != todayDate }

                        withContext(Dispatchers.Main) {
                            _searchForecastTodayList.postValue(todayForecast)
                            _searchForecastWeatherList.postValue(futureForecast)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Hata mesajı ekleyebilirsiniz
            }
        }
    }

    fun fetchPlaceDetails(placeId: String, cityname : String) {
        val request = FetchPlaceRequest.newInstance(placeId, listOf(Place.Field.LAT_LNG, Place.Field.TYPES))

        placesClient.fetchPlace(request)
            .addOnSuccessListener { response ->
                val place = response.place
                val latLng = response.place.latLng
                val placeTypes = place.placeTypes

                println("Place Types: $placeTypes")

                var placeType = when {
                    placeTypes.contains(PlaceTypes.COUNTRY) -> "Country"
                    placeTypes.contains(PlaceTypes.ADMINISTRATIVE_AREA_LEVEL_1) -> "City"
                    placeTypes.contains(PlaceTypes.ADMINISTRATIVE_AREA_LEVEL_2) -> "State"
                    else -> "Unknown"
                }


                if (latLng != null) {
                    selectedLatLng = Pair(latLng.latitude, latLng.longitude)
                    println("City Name : $cityname, Place Type : $placeType")
                    fetchWeather(cityname, latLng.latitude, latLng.longitude)
                    fetchForecast(latLng.latitude, latLng.longitude)
                }
            }
            .addOnFailureListener {
                println("Error fetching place details: ${it.message}")
            }
    }
}
