// Assignment 10
// Forecast.java
// Alex Ilevbare
package com.example.assignment10;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Forecast {
    public String startTime;
    public String temperature;
    public String icon;
    public String shortForecast;

    public String windSpeed;
    public String probabilityOfPrecipitation;


    public Forecast(JSONObject jsonObject) throws JSONException {
        this.startTime = jsonObject.getString("startTime");
        this.temperature = jsonObject.getString("temperature");
        this.icon = jsonObject.getString("icon");
        this.shortForecast = jsonObject.getString("shortForecast");
        this.windSpeed = jsonObject.getString("windSpeed");
        if (jsonObject.has("probabilityOfPrecipitation") && !jsonObject.isNull("probabilityOfPrecipitation")) {
            this.probabilityOfPrecipitation = jsonObject.getJSONObject("probabilityOfPrecipitation").optString("value", "N/A");
        } else {
            this.probabilityOfPrecipitation = "N/A";
        }
    }


    public String getStartTime() {
        return startTime;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getIcon() {
        return icon;
    }

    public String getShortForecast() {
        return shortForecast;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getProbabilityOfPrecipitation() {
        return probabilityOfPrecipitation;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setShortForecast(String shortForecast) {
        this.shortForecast = shortForecast;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setProbabilityOfPrecipitation(String probabilityOfPrecipitation) {
        this.probabilityOfPrecipitation = probabilityOfPrecipitation;
    }

}



