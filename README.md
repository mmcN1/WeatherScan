# 🚀 SplashScreen
When the app is opened, the user is greeted with the logo and version information.

In the background, weather data is fetched.

Once the data is received, the user is automatically redirected to the main page.

![SplashScreen1](https://github.com/user-attachments/assets/b20be140-da31-42d0-b103-9b096f9464d0)

# 📍 LocationInfo (Weather Based on Location)
Current weather information is displayed based on the user's location.

Hourly forecasts for the day and 5-day weather details are provided.

The background color changes dynamically based on the weather.
![LocationScreen1](https://github.com/user-attachments/assets/9ff2b67a-2072-44fe-924a-97fd858f4670)
![LocationScreen2](https://github.com/user-attachments/assets/cb61f84d-3fff-4199-9931-c319a5fd701a)

# 🔎 SearchScreen (Location Search)
The user can search for locations such as cities, districts, or countries.

The weather for the selected location is loaded dynamically with animations.

Today's and the 5-day forecast are displayed visually.

![SearchScreen4](https://github.com/user-attachments/assets/efe662d8-eb1f-4921-8af8-e43d949ae468)
![SearchScreen3](https://github.com/user-attachments/assets/d9aa03e3-1315-493c-ab0e-62c7206bda7c)
![SearchScreen2](https://github.com/user-attachments/assets/7568f2b9-e240-47e0-89ee-d5ad4a79c1a7)

# 🧠 ViewModel Structure (Weather & Search)
The MVVM structure ensures clean and testable data management.

Data is fetched from Google Places and OpenWeatherMap APIs.

The background dynamically changes based on the weather.


# 🌐 API and Data Structure
Data is fetched from the OpenWeatherMap API using Retrofit.

JSON data is parsed using classes like WeatherModel and ForecastList.

Location suggestions are fetched using the Google Places API.

