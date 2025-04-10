# Weather Scan App

## Project Description
This project is an Android application that provides weather information based on the user's location. Users can view current weather conditions, hourly forecasts, and 5-day weather predictions. The app also allows searching for weather data by city, district, or country.

## Features

### Splash Screen:
- The initial screen displayed when the app is launched.
- Simple design featuring the app name and logo.
- <p align="center">
  <img src="https://github.com/user-attachments/assets/317d2d00-2c29-4cb3-b051-f227ed213246" width="300"/>
  <img src="https://github.com/user-attachments/assets/4e846f9b-d3a6-4b87-b233-8c01e46b7139" width="300"/>
  </p>

### Location Info Screen:
- Displays the current weather information based on the user's location.
- Shows temperature, weather conditions, and location name.
- Dynamic background color changes based on the current weather.
- <p align="center">
  <img src="https://github.com/user-attachments/assets/62e401f2-5364-4a10-920f-0c735c603f52" width="300"/>
  </p>

### Hourly Forecast:
- Displays hourly weather forecast for the day.
- Shows hourly temperature, weather conditions, and time.
- Hourly forecast information is fetched and updated dynamically.
- <p align="center">
  <img src="https://github.com/user-attachments/assets/219307f9-4601-4d3a-98fa-e419f9294083" width="300"/>
  </p>

### Search Screen:
- Allows users to search for weather information by city, district, or country.
- Displays the selected location's current weather and 5-day forecast.
- Weather data is loaded dynamically with smooth animations.
- <p align="center">
  <img src="https://github.com/user-attachments/assets/1827cf1f-71f1-4ec5-84ab-f0ab40db6e20" width="300"/>
  <img src="https://github.com/user-attachments/assets/a7db1d83-069d-4658-8d97-7d6302e4c170" width="300"/>
  </p>

### 5-Day Forecast:
- Displays a detailed 5-day weather forecast.
- Shows the daily high/low temperature, weather conditions, and the day of the week.
- <p align="center">
  <img src="https://github.com/user-attachments/assets/cf736dd6-b389-4301-be46-eb2f46ba7924" width="300"/>
  </p>

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
