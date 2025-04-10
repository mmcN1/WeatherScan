# Weather Scan App

## Project Description
This project is an Android application that provides weather information based on the user's location. Users can view current weather conditions, hourly forecasts, and 5-day weather predictions. The app also allows searching for weather data by city, district, or country.

## Features

### Splash Screen:
- The initial screen displayed when the app is launched.
- Simple design featuring the app name and logo.
![SplashScreen1](https://raw.githubusercontent.com/user-attachments/assets/a2909d81-9d89-4cf5-9efb-388c28c85189)
![SplashScreen2](https://raw.githubusercontent.com/user-attachments/assets/252c6c0a-bf10-4cf5-85db-38244459383d)

### Location Info Screen:
- Displays the current weather information based on the user's location.
- Shows temperature, weather conditions, and location name.
- Dynamic background color changes based on the current weather.
![LocationScreen1](https://raw.githubusercontent.com/user-attachments/assets/dc966882-5ef3-4155-b1bb-14b7405d52f6)

### Hourly Forecast:
- Displays hourly weather forecast for the day.
- Shows hourly temperature, weather conditions, and time.
- Hourly forecast information is fetched and updated dynamically.
![LocationScreen2](https://raw.githubusercontent.com/user-attachments/assets/10c18e4a-1f8b-4ddf-81fa-4cbedb3078c8)

### Search Screen:
- Allows users to search for weather information by city, district, or country.
- Displays the selected location's current weather and 5-day forecast.
- Weather data is loaded dynamically with smooth animations.
![SearchScreen4](https://raw.githubusercontent.com/user-attachments/assets/e705509b-dfde-4606-9316-075148e7ac57)
![SearchScreen3](https://raw.githubusercontent.com/user-attachments/assets/872e3679-2524-426b-be7b-fd8e134c63fd)

### 5-Day Forecast:
- Displays a detailed 5-day weather forecast.
- Shows the daily high/low temperature, weather conditions, and the day of the week.
![SearchScreen2](https://raw.githubusercontent.com/user-attachments/assets/ccca729e-2b19-4106-92f0-efb47231aa04)

## Technologies Used
- **Android**: The primary platform for app development.
- **Google Places API**: For location suggestions.
- **OpenWeatherMap API**: For weather data fetching.
- **Glide**: For image loading.
- **MaterialSearchView**: For search functionality.
- **MVVM**: Architecture for clean data management.

## Setup

### Install Required Dependencies:
- Add necessary dependencies for Google Places, OpenWeatherMap API, and Retrofit to your `build.gradle` file.

### API Configuration:
- Create accounts with Google Places and OpenWeatherMap.
- Get the necessary API keys and configure them in your app.

### Run the Application:
- Open the project in Android Studio.
- Run the application on a device or emulator.

## Usage
1. Open the app and grant location permissions to retrieve weather information based on your current location.
2. Browse the current weather and hourly forecast in the Location Info Screen.
3. Use the **Search Screen** to search for weather data by entering a city, district, or country.
4. View the 5-day weather forecast for your selected location.
5. Update your user information or log out from the profile section.
