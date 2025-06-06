# Google Maps Integration Assignment

This assignment requires you to integrate Google Maps into your single-activity application and plot a route using polyline shapes and markers. In addition, you will retrieve trip path data from a remote API using the OkHTTP library.

## Part 1: Google Maps SDK Integration
1. Create a new project in Google Cloud Console with the Google Maps SDK enabled. Use the API key provided.
2. Integrate the Google Maps SDK in your project following the guide:
   https://developers.google.com/maps/documentation/android-sdk/config
3. Add a map to your activity layout and initialize it in your MainActivity using `getMapAsync`.  
   Refer to: https://developers.google.com/maps/documentation/android-sdk/map#maps_android_get_map_async-java  
   // ...existing map initialization code in MainActivity...

## Part 2: Retrieve Route using OkHTTP
1. Use OkHTTP to perform a GET request to:
   URL: https://www.theappsdr.com/map/route  
2. Parse the JSON response to extract the trip path coordinates.
   // ...existing code for OkHTTP request and JSON parsing in MainActivity...

## Part 3: Plot Trip Information on the Map
1. Once the map is loaded, trigger the API call to retrieve trip points.
2. Plot the trip points on the map using a Polyline. Mark the start and end locations with markers.  
   Refer to: https://developers.google.com/maps/documentation/android-sdk/polygon-tutorial
3. Center and auto-zoom the map to include all trip points by creating a bounding box using `LatLngBounds` and updating the camera with `CameraUpdateFactory`.  
   // ...existing code for plotting the polyline, markers, and auto zooming...

This README outlines the key requirements and steps for integrating Google Maps with route plotting in your application.
