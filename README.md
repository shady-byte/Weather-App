# Weather-App

## Description
  A simple weather application built with Kotlin, MVVM, and Jetpack Compose to display current weather conditions for a country state of your choice.

## Features
- Fetches all states of specific country when you write its Iso2 in searchbar
- Fetches real-time weather data of specific country state
- Displays temperature, humidity, and other weather conditions

## Tech Stack & Libraries
- Kotlin: Programming language
- Jetpack Compose: Ui platform
- MVVM & Clean architecture: Architecture pattern 
- Retrofit: Networking
- Koin: Dependency Injection
- Coroutines: Asynchronous programming
- Mockk & Turbine: UnitTest

## Installation

### Prerequisites
- You need to create your own access keys to be able to fetch data from Apis
- visit https://weatherstack.com to create your own access key for weather Api
- visit https://countrystatecity.in/ to create your own access key for states Api

### Steps
1. Clone the repository:
   - git clone https://github.com/your-username/your-repo.git
2. Open the project in Android Studio.
3. add your Api keys to local.properties file
   - WEATHER_API_KEY = WEATHER_API_ACCESS_KEY
   - STATES_API_KEY = STATES_API_ACCESS_KEY
4. Sync Gradle files and build project.
5. Run the app on an emulator or physical device.

## Acknowledgments
- Thanks to weatherstack for providing the weather data API.
- Thanks to countrystatecity for providing the country/state data API.

## Screenshots
<table>
  <tr>
    <td><img src="https://github.com/shady-byte/Weather-App/blob/8b559819c55aa2cf6ca9e0afd64536d795a0a6d4/states-screenshot.png" width=223 height=450></td>
    <td><img src="https://github.com/shady-byte/Weather-App/blob/8b559819c55aa2cf6ca9e0afd64536d795a0a6d4/weather-screenshot.png" width=223 height=450></td>
  </tr>
 </table>

 
## Woow, Now You're Ready to Know The Current Weather Of Your State! 🚀

  
