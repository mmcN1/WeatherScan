# Weather Scan App

## Project Description
This project is an Android application that provides weather information based on the user's location. Users can view current weather conditions, hourly forecasts, and 5-day weather predictions. The app also allows searching for weather data by city, district, or country.

## Features

### Splash Screen:
- The initial screen displayed when the app is launched.
- Simple design featuring the app name and logo.
- ![SplashScreen1](https://github.com/user-attachments/assets/12377fdb-f9a2-4a43-8184-087d0c7143b3)
  


### Location Info Screen:
- Displays the current weather information based on the user's location.
- Shows temperature, weather conditions, and location name.
- Dynamic background color changes based on the current weather.
![LocationScreen1](https://github.com/user-attachments/assets/642cfedf-1c77-4843-8d4b-caa1ac9c0a65)
-

### Hourly Forecast:
- Displays hourly weather forecast for the day.
- Shows hourly temperature, weather conditions, and time.
- Hourly forecast information is fetched and updated dynamically.
![LocationScreen2](https://github.com/user-attachments/assets/47d54331-8aa3-4ace-a45b-5165b15d9a06)

### Search Screen:
- Allows users to search for weather information by city, district, or country.
- Displays the selected location's current weather and 5-day forecast.
- Weather data is loaded dynamically with smooth animations.
![SearchScreen4](https://github.com/user-attachments/assets/02f35a52-9751-44e9-811a-dc4fc0e0e335)

### 5-Day Forecast:
- Displays a detailed 5-day weather forecast.
- Shows the daily high/low temperature, weather conditions, and the day of the week.
![SearchScreen3](https://github.com/user-attachments/assets/cfc6112c-c202-4548-8727-bbbee9a3e6dc)

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
