package com.example.maps;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String TAG = "MainActivity";
    private LatLng startLocation;
    private LatLng endLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String apiKey = "YOUR_API_KEY";

        // Initialize the SDK
        try {
            if (!Places.isInitialized()) {
                Places.initialize(getApplicationContext(), apiKey);
                Log.d(TAG, "Places SDK initialized successfully");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error initializing Places: " + e.getMessage());
            Toast.makeText(this, "Error initializing Places API", Toast.LENGTH_LONG).show();
            return;
        }

        // Initialize map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        setupStartLocationAutocomplete();
        setupEndLocationAutocomplete();
    }

    private void setupStartLocationAutocomplete() {
        AutocompleteSupportFragment startAutocomplete = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.start_autocomplete_fragment);

        if (startAutocomplete != null) {
            startAutocomplete.setPlaceFields(Arrays.asList(
                    Place.Field.ID,
                    Place.Field.NAME,
                    Place.Field.LAT_LNG,
                    Place.Field.ADDRESS
            ));
            startAutocomplete.setTypeFilter(TypeFilter.ADDRESS);
            startAutocomplete.setHint("Enter start location");

            startAutocomplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(@NonNull Place place) {
                    startLocation = place.getLatLng();
                    
                    if (startLocation != null) {
                        updateMap();
                    }
                }

                @Override
                public void onError(@NonNull Status status) {
                    Log.e(TAG, "Start location error: " + status);
                    Toast.makeText(MainActivity.this,
                            "Error: " + status.getStatusMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setupEndLocationAutocomplete() {
        AutocompleteSupportFragment endAutocomplete = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.end_autocomplete_fragment);

        if (endAutocomplete != null) {
            endAutocomplete.setPlaceFields(Arrays.asList(
                    Place.Field.ID,
                    Place.Field.NAME,
                    Place.Field.LAT_LNG,
                    Place.Field.ADDRESS
            ));
            endAutocomplete.setTypeFilter(TypeFilter.ADDRESS);
            endAutocomplete.setHint("Enter destination");

            endAutocomplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(@NonNull Place place) {
                    endLocation = place.getLatLng();
                    if (endLocation != null) {
                        updateMap();
                    }
                }

                @Override
                public void onError(@NonNull Status status) {
                    Log.e(TAG, "End location error: " + status);
                    Toast.makeText(MainActivity.this,
                            "Error: " + status.getStatusMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void findGasStation() {
        if (startLocation == null || endLocation == null) {
            Toast.makeText(this, "Please select both start and destination locations first", Toast.LENGTH_SHORT).show();
            return;
        }

        // Will be populated when route is fetched
        final LatLng[] midPoint = new LatLng[1];
        String apiKey = "YOUR_API_KEY";
        String routeUrl = "https://routes.googleapis.com/directions/v2:computeRoutes?key=" + apiKey;

        // First get the route to find the actual midpoint
        String routeBody = "{"
                + "\"origin\": {\"location\": {\"latLng\": {\"latitude\":" + startLocation.latitude + ",\"longitude\":" + startLocation.longitude + "}}},"
                + "\"destination\": {\"location\": {\"latLng\": {\"latitude\":" + endLocation.latitude + ",\"longitude\":" + endLocation.longitude + "}}},"
                + "\"travelMode\": \"DRIVE\""
                + "}";

        OkHttpClient client = new OkHttpClient();
        Request routeRequest = new Request.Builder()
                .url(routeUrl)
                .addHeader("Content-Type", "application/json")
                .addHeader("X-Goog-Api-Key", apiKey)
                .addHeader("X-Goog-FieldMask", "routes.polyline.encodedPolyline")
                .post(RequestBody.create(routeBody, MediaType.parse("application/json")))
                .build();

        client.newCall(routeRequest).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "Route API failed: " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Route API error: " + response.message());
                    return;
                }

                try {
                    JSONObject json = new JSONObject(response.body().string());
                    JSONArray routes = json.getJSONArray("routes");
                    if (routes.length() > 0) {
                        JSONObject route = routes.getJSONObject(0);
                        JSONObject polylineObj = route.getJSONObject("polyline");
                        String encoded = polylineObj.getString("encodedPolyline");
                        List<LatLng> points = decodePolyline(encoded);

                        // Get the middle point from the route
                        if (points.size() > 0) {
                            midPoint[0] = points.get(points.size() / 2);
                            // Now search for gas stations near all points
                            searchGasStations(startLocation, midPoint[0], endLocation);
                            
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Parse error: " + e.getMessage());
                }
            }
        });
    }

    private void searchGasStations(LatLng start, LatLng middle, LatLng end) {
        String apiKey = "YOUR_API_KEY";
        String baseUrl = "https://places.googleapis.com/v1/places:searchNearby";

        String startBody = "{"
                + "\"locationRestriction\": {"
                + "  \"circle\": {"
                + "    \"center\": {\"latitude\": " + start.latitude + ", \"longitude\": " + start.longitude + "},"
                + "    \"radius\": 2000.0"
                + "  }"
                + "},"
                + "\"includedTypes\": [\"gas_station\"]"
                + "}";

        String midBody = "{"
                + "\"locationRestriction\": {"
                + "  \"circle\": {"
                + "    \"center\": {\"latitude\": " + middle.latitude + ", \"longitude\": " + middle.longitude + "},"
                + "    \"radius\": 2000.0"
                + "  }"
                + "},"
                + "\"includedTypes\": [\"gas_station\"]"
                + "}";

        String endBody = "{"
                + "\"locationRestriction\": {"
                + "  \"circle\": {"
                + "    \"center\": {\"latitude\": " + end.latitude + ", \"longitude\": " + end.longitude + "},"
                + "    \"radius\": 2000.0"
                + "  }"
                + "},"
                + "\"includedTypes\": [\"gas_station\"]"
                + "}";

        OkHttpClient client = new OkHttpClient();

        // Create requests for all three locations
        Request startRequest = new Request.Builder()
                .url(baseUrl)
                .addHeader("Content-Type", "application/json")
                .addHeader("X-Goog-Api-Key", apiKey)
                .addHeader("X-Goog-FieldMask", "places.location,places.displayName")
                .post(RequestBody.create(startBody, MediaType.parse("application/json")))
                .build();

        Request midRequest = new Request.Builder()
                .url(baseUrl)
                .addHeader("Content-Type", "application/json")
                .addHeader("X-Goog-Api-Key", apiKey)
                .addHeader("X-Goog-FieldMask", "places.location,places.displayName")
                .post(RequestBody.create(midBody, MediaType.parse("application/json")))
                .build();

        Request endRequest = new Request.Builder()
                .url(baseUrl)
                .addHeader("Content-Type", "application/json")
                .addHeader("X-Goog-Api-Key", apiKey)
                .addHeader("X-Goog-FieldMask", "places.location,places.displayName")
                .post(RequestBody.create(endBody, MediaType.parse("application/json")))
                .build();

        // Add a marker for the midpoint
        runOnUiThread(() -> {
            mMap.addMarker(new MarkerOptions()
                    .position(middle)
                    .title("Route Midpoint")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        });

        // Execute start location search
        client.newCall(startRequest).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "Error searching for gas stations near start: " + e.getMessage());
                runOnUiThread(() -> Toast.makeText(MainActivity.this,
                        "Failed to find gas stations near start location", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Error response for start location: " + response.body().string());
                    return;
                }

                try {
                    JSONObject jsonResponse = new JSONObject(response.body().string());
                    JSONArray places = jsonResponse.getJSONArray("places");

                    for (int i = 0; i < places.length(); i++) {
                        JSONObject place = places.getJSONObject(i);
                        JSONObject location = place.getJSONObject("location");
                        JSONObject displayName = place.getJSONObject("displayName");

                        double lat = location.getDouble("latitude");
                        double lng = location.getDouble("longitude");
                        String name = displayName.getString("text");

                        LatLng position = new LatLng(lat, lng);

                        runOnUiThread(() -> {
                            mMap.addMarker(new MarkerOptions()
                                    .position(position)
                                    .title(name)
                                    .snippet("Gas Station near start")
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        });
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error parsing start location response: " + e.getMessage());
                }
            }
        });

        // Execute middle point search
        client.newCall(midRequest).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "Error searching for gas stations near middle: " + e.getMessage());
                runOnUiThread(() -> Toast.makeText(MainActivity.this,
                        "Failed to find gas stations near middle point", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Error response for middle point: " + response.body().string());
                    return;
                }

                try {
                    JSONObject jsonResponse = new JSONObject(response.body().string());
                    JSONArray places = jsonResponse.getJSONArray("places");

                    for (int i = 0; i < places.length(); i++) {
                        JSONObject place = places.getJSONObject(i);
                        JSONObject location = place.getJSONObject("location");
                        JSONObject displayName = place.getJSONObject("displayName");

                        double lat = location.getDouble("latitude");
                        double lng = location.getDouble("longitude");
                        String name = displayName.getString("text");

                        LatLng position = new LatLng(lat, lng);

                        runOnUiThread(() -> {
                            mMap.addMarker(new MarkerOptions()
                                    .position(position)
                                    .title(name)
                                    .snippet("Gas Station near middle")
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                        });
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error parsing middle point response: " + e.getMessage());
                }
            }
        });

        // Execute destination search
        client.newCall(endRequest).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "Error searching for gas stations near destination: " + e.getMessage());
                runOnUiThread(() -> Toast.makeText(MainActivity.this,
                        "Failed to find gas stations near destination", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Error response for destination: " + response.body().string());
                    return;
                }

                try {
                    JSONObject jsonResponse = new JSONObject(response.body().string());
                    JSONArray places = jsonResponse.getJSONArray("places");

                    for (int i = 0; i < places.length(); i++) {
                        JSONObject place = places.getJSONObject(i);
                        JSONObject location = place.getJSONObject("location");
                        JSONObject displayName = place.getJSONObject("displayName");

                        double lat = location.getDouble("latitude");
                        double lng = location.getDouble("longitude");
                        String name = displayName.getString("text");

                        LatLng position = new LatLng(lat, lng);

                        runOnUiThread(() -> {
                            mMap.addMarker(new MarkerOptions()
                                    .position(position)
                                    .title(name)
                                    .snippet("Gas Station near destination")
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                        });
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error parsing destination response: " + e.getMessage());
                }
            }
        });
    }

    private void updateMap() {
        if (mMap == null) return;

        mMap.clear(); // Clear existing markers

        // Add markers for both locations if they exist
        if (startLocation != null) {
            mMap.addMarker(new MarkerOptions()
                    .position(startLocation)
                    .title("Start Location"));
        }

        if (endLocation != null) {
            mMap.addMarker(new MarkerOptions()
                    .position(endLocation)
                    .title("Destination"));
        }
    

        // If both locations are set, show both on the map
        if (startLocation != null && endLocation != null) {
            com.google.android.gms.maps.model.LatLngBounds.Builder boundsBuilder = new
                    com.google.android.gms.maps.model.LatLngBounds.Builder();
            boundsBuilder.include(startLocation);
            boundsBuilder.include(endLocation);
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 100));
            fetchAndDrawRoute(startLocation, endLocation);
            findGasStation();
        }
        // Otherwise focus on the location that is set
        else if (startLocation != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(startLocation, 15));
        } else if (endLocation != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(endLocation, 15));
        }
    }

    private void fetchAndDrawRoute(LatLng start, LatLng end) {
        String apiKey = "YOUR_API_KEY";
        String url = "https://routes.googleapis.com/directions/v2:computeRoutes?key=" + apiKey;

        String body = "{"
                + "\"origin\": {\"location\": {\"latLng\": {\"latitude\":" + start.latitude + ",\"longitude\":" + start.longitude + "}}},"
                + "\"destination\": {\"location\": {\"latLng\": {\"latitude\":" + end.latitude + ",\"longitude\":" + end.longitude + "}}},"
                + "\"travelMode\": \"DRIVE\""
                + "}";

        Log.d(TAG, "Requesting route from: " + start.latitude + "," + start.longitude + 
                  " to: " + end.latitude + "," + end.longitude);
        Log.d(TAG, "Request body: " + body);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("X-Goog-Api-Key", apiKey)
                .addHeader("X-Goog-FieldMask", "routes.duration,routes.distanceMeters,routes.polyline.encodedPolyline")
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "Route API failed: " + e.getMessage(), e);
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Route API failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String resp = response.body().string();
                Log.d(TAG, "Route API Response Code: " + response.code());
                Log.d(TAG, "Route API Response: " + resp);

                if (!response.isSuccessful()) {
                    Log.e(TAG, "Route API error: " + response.message() + ", Body: " + resp);
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "Route API error: " + response.message(), Toast.LENGTH_SHORT).show());
                    return;
                }

                try {
                    JSONObject json = new JSONObject(resp);
                    JSONArray routes = json.getJSONArray("routes");
                    Log.d(TAG, "Number of routes received: " + routes.length());
                    
                    if (routes.length() > 0) {
                        JSONObject route = routes.getJSONObject(0);
                        JSONObject polylineObj = route.getJSONObject("polyline");
                        String encoded = polylineObj.getString("encodedPolyline");
                        List<LatLng> points = decodePolyline(encoded);
                        Log.d(TAG, "Successfully decoded polyline with " + points.size() + " points");
                        
                        runOnUiThread(() -> {
                            mMap.addPolyline(new PolylineOptions().addAll(points).color(android.graphics.Color.BLUE));
                        });
                    } else {
                        Log.w(TAG, "No routes found in the response");
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Parse error: " + e.getMessage(), e);
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "Parse error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private List<LatLng> decodePolyline(String encoded) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;
            poly.add(new LatLng(lat / 1E5, lng / 1E5));
        }
        return poly;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Initial camera position (can be anywhere, will be updated when locations are selected)
        LatLng defaultLocation = new LatLng(-34, 151);  // Sydney
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 12));
    }
}