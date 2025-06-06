// Assignment 10
// City.java
// Alex Ilevbare

package com.example.assignment10;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class City implements Serializable {
    private String name;
    private String state;
    private double lat;
    private double lng;;

    public City(JSONObject cityJsonObject) throws JSONException {
        this.name = cityJsonObject.getString("name");
        this.state = cityJsonObject.getString("state");
        this.lat = cityJsonObject.getDouble("lat");
        this.lng = cityJsonObject.getDouble("lng");
    }


    public String getName() {
        return name;
    }
    public String getState() {
        return state;
    }
    public double getLat() {
        return lat;
    }
    public double getLng() {
        return lng;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setLat(int lat) {
        this.lat = lat;
    }
    public void setLng(int lng) {
        this.lng = lng;
    }
    @Override
    public String toString() {
        return name + ", " + state;
    }
}
