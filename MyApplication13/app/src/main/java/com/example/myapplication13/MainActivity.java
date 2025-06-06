// Assignment 13
// MainActivity.java
// Alex Ilevbare

package com.example.myapplication13;

import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.model.CircularBounds;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.libraries.places.api.model.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String API_URL = "https://www.theappsdr.com/map/route";
    private LatLng startLocation = null;
    private LatLng endLocation = null;
    private static final int AUTOCOMPLETE_REQUEST_CODE_START = 1;
    private static final int AUTOCOMPLETE_REQUEST_CODE_END = 2;
    private ActivityResultLauncher<Intent> launcherStart;
    private ActivityResultLauncher<Intent> launcherEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Updated initialization to use new Places API method.
        if (!Places.isInitialized()) {
            Places.initializeWithNewPlacesApiEnabled(getApplicationContext(), getString(R.string.google_maps_key));
        }

        // Set up map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Register ActivityResultLaunchers for Autocomplete
        launcherStart = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Place place = Autocomplete.getPlaceFromIntent(result.getData());
                        startLocation = place.getLatLng();
                        ((TextView) findViewById(R.id.tvStartAddress)).setText(place.getAddress());
                        if (startLocation != null && endLocation != null) {
                            fetchRouteAndPlot();
                        }
                    } else if (result.getResultCode() == AutocompleteActivity.RESULT_ERROR) {
                        // Handle error if needed.
                    }
                });

        launcherEnd = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Place place = Autocomplete.getPlaceFromIntent(result.getData());
                        endLocation = place.getLatLng();
                        ((TextView) findViewById(R.id.tvEndAddress)).setText(place.getAddress());
                        if (startLocation != null && endLocation != null) {
                            fetchRouteAndPlot();
                        }
                    } else if (result.getResultCode() == AutocompleteActivity.RESULT_ERROR) {
                        // Handle error if needed.
                    }
                });

        // Set button click listeners to launch Autocomplete
        Button btnStart = findViewById(R.id.btnStart);
        Button btnEnd = findViewById(R.id.btnEnd);

        btnStart.setOnClickListener(v -> {
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME,
                    Place.Field.LAT_LNG, Place.Field.ADDRESS);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                    .build(MainActivity.this);
            launcherStart.launch(intent);
        });

        btnEnd.setOnClickListener(v -> {
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME,
                    Place.Field.LAT_LNG, Place.Field.ADDRESS);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                    .build(MainActivity.this);
            launcherEnd.launch(intent);
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void fetchRouteAndPlot() {
        // Build URL with start and end coordinates
        String url = API_URL + "?start=" + startLocation.latitude + "," + startLocation.longitude +
                "&end=" + endLocation.latitude + "," + endLocation.longitude;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("MainActivity", "API call failed: " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String responseBody = response.body().string();
                        List<LatLng> routePoints = parseTripPoints(responseBody);
                        runOnUiThread(() -> plotRouteAndGasStations(routePoints));
                    } catch (JSONException e) {
                        Log.e("MainActivity", "JSON parsing error: " + e.getMessage());
                    }
                }
            }
        });
    }

    private List<LatLng> parseTripPoints(String json) throws JSONException {
        List<LatLng> tripPoints = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(json);
        JSONArray pathArray = jsonObject.getJSONArray("path");

        for (int i = 0; i < pathArray.length(); i++) {
            JSONObject point = pathArray.getJSONObject(i);
            double lat = point.getDouble("latitude");
            double lng = point.getDouble("longitude");
            tripPoints.add(new LatLng(lat, lng));
        }

        return tripPoints;
    }

    private void plotRouteAndGasStations(List<LatLng> routePoints) {
        if (routePoints.isEmpty() || mMap == null) return;

        // Clear previous markers and polylines if needed
        mMap.clear();

        // Draw polyline
        PolylineOptions polylineOptions = new PolylineOptions().addAll(routePoints);
        mMap.addPolyline(polylineOptions);

        // Add markers for start and end points
        LatLng startPoint = routePoints.get(0);
        LatLng endPoint = routePoints.get(routePoints.size() - 1);
        mMap.addMarker(new MarkerOptions().position(startPoint).title("Start"));
        mMap.addMarker(new MarkerOptions().position(endPoint).title("End"));

        // Simulate gas station markers:
        // Gas station near start (offset slightly)
        LatLng gasStart = new LatLng(startPoint.latitude + 0.001, startPoint.longitude + 0.001);
        // Gas station near end (offset slightly)
        LatLng gasEnd = new LatLng(endPoint.latitude + 0.001, endPoint.longitude + 0.001);
        // Gas station at mid point
        LatLng midPoint = routePoints.get(routePoints.size() / 2);

        mMap.addMarker(new MarkerOptions().position(gasStart).title("Gas Station (Start)"));
        mMap.addMarker(new MarkerOptions().position(midPoint).title("Gas Station (Mid)"));
        mMap.addMarker(new MarkerOptions().position(gasEnd).title("Gas Station (End)"));

        // Adjust camera to include all route points
        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        for (LatLng point : routePoints) {
            boundsBuilder.include(point);
        }
        LatLngBounds bounds = boundsBuilder.build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }

    // New method demonstrating Autocomplete (New) API usage.
    private void fetchAutocompletePredictions(String query) {
        PlacesClient placesClient = Places.createClient(this);
        LatLng center = new LatLng(37.7749, -122.4194);
        CircularBounds circle = CircularBounds.newInstance(center, 5000);

        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setQuery(query)
                .setLocationBias(circle)
                .build();

        placesClient.findAutocompletePredictions(request)
                .addOnSuccessListener(response -> {
                    List<AutocompletePrediction> predictions = response.getAutocompletePredictions();
                    for (AutocompletePrediction prediction : predictions) {
                        Log.d("MainActivity", "Prediction: " + prediction.getFullText(null));
                    }
                })
                .addOnFailureListener(exception -> {
                    Log.e("MainActivity", "Prediction fetch failed: " + exception.getMessage());
                });
    }
}
