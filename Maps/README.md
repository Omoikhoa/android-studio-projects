# Maps Integration Assignment

This assignment integrates Google Maps into a single-activity Android app. It retrieves route data using the OkHTTP library and then plots the route on the map with polyline overlays and markers. The app also uses the Places API to support location autocomplete.

## Part 1: Google Maps SDK Integration
- Create and enable a project in Google Cloud Console with the Google Maps SDK.
- Integrate the SDK by following the guide:
  https://developers.google.com/maps/documentation/android-sdk/config
- Add a map to the activity layout and initialize it using getMapAsync.
  ...existing map initialization code...

## Part 2: Retrieve Route with OkHTTP
- Make a GET request to the API endpoint:
  https://www.theappsdr.com/map/route
- Parse the JSON response to extract the list of LatLng points.
  ...existing OkHTTP and JSON parsing code...

## Part 3: Plot Route, Markers, and Auto-Zoom
- Once the route is retrieved, plot the trip points using a Polyline.
- Add markers at the start and end of the trip.
- Adjust the camera using LatLngBounds and CameraUpdateFactory so that all points are visible.
  ...existing code for polyline drawing and camera updates...

This README summarizes the requirements and implementation details for the Maps Integration Assignment.
