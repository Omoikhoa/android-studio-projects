# Weather Forecast App

This assignment involves building a weather forecast app that makes REST API calls and retrieves data in JSON format. The app uses one Main Activity and multiple fragments to display a list of cities and detailed weather forecasts. Key libraries such as OkHttp and Picasso are used to make network requests and load images.

## Libraries and Setup
- Add the following libraries to your Gradle build file:
  - OkHttp for REST API calls.
  - Picasso for image loading.
  - (Other standard Android libraries are provided by default.)
- Import the provided Postman file containing the APIs needed.

## Part 1: Cities Screen
- **Functionality:**
  - Makes a GET request to `https://www.theappsdr.com/api/cities` to retrieve a list of cities.
  - A `City` class holds the data returned from the API (including name, state, latitude, and longitude).
  - The Cities Screen displays each city's name and state in a ListView.
- **User Interaction:**
  - When a city row is clicked, the app transitions to the Weather Forecast screen.
  - The selected `City` object is passed to the Weather Forecast fragment.

## Part 2: Weather Forecast Screen
- **Functionality:**
  - The screen receives the `City` object which contains the city’s name, latitude, and longitude.
  - **Step 1:**  
    - A GET request is made to:  
      `https://api.weather.gov/points/{latitude},{longitude}`  
      For example: `https://api.weather.gov/points/35.2033219,-81.0049789`.
    - From the JSON response, retrieve the forecast URL located at `properties.forecast`.
  - **Step 2:**  
    - A GET request is made to the forecast URL (e.g., `https://api.weather.gov/gridpoints/GSP/113,63/forecast`).
    - A `Forecast` class is created to hold the data returned from this API.
    - The screen then displays a list of forecast items including:
      - Start time (`startTime`)
      - Temperature (with “°F”)
      - Precipitation probability (e.g., "Precipitation: 20%")
      - Short forecast description (e.g., "Sunny")
      - Wind speed (e.g., "Wind Speed: 9 mph")
      - Forecast icon (loaded via Picasso)
- **User Interface:**
  - The forecast list is shown in a ListView (or similar component).
  - The UI closely matches the provided wireframe where weather details for each period are displayed.

This README outlines the app structure and functionalities. The Cities Screen allows users to select a city and view corresponding weather details retrieved via REST API calls, while the Weather Forecast Screen parses and displays the forecast information for the selected city.
