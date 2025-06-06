# Podcast & Favorites Management App

This assignment uses APIs to manage podcasts and a favorites list for multiple users. The app is built using a single-activity architecture where fragments are swapped based on user actions and Firebase Authentication controls access. Data for podcasts is retrieved via an HTTP GET call and parsed from JSON. Favorites are managed and stored in Firebase Firestore ensuring no duplicates per user.

## Part 0: Main Activity Check Authentication
- On launch, MainActivity checks if a user is logged in using Firebase Auth.
  - If logged in, the Podcasts Fragment loads.
  - If not, the Login Fragment is shown.

## Part 1: Login Fragment
- The Login screen enables users to provide email and password.
  - On “Login,” validate inputs and attempt authentication via Firebase.
  - On success, notify MainActivity to load the Podcasts Fragment.
  - On error or missing input, show an alert dialog.
- A “Create New Account” button replaces this screen with the SignUp Fragment.

## Part 2: SignUp Fragment
- The SignUp screen collects email and password for registration.
  - On “Submit,” validate inputs and create the account using Firebase Auth.
  - On success, notify MainActivity to load the Podcasts Fragment.
  - On failure or missing input, show an appropriate alert.
- Clicking “Cancel” returns to the Login Fragment.

## Part 3: Podcasts Fragment
- Retrieve a list of podcasts via an API GET request from https://www.theappsdr.com/design.json.
  - Parse the response JSON and display each podcast’s image (artworkUrl100), collection name, artist name, release date, and genres (comma-separated).
- Tapping the “+” icon adds the selected podcast to the favorite list on Firebase Firestore.
  - The Firestore design ensures that each podcast is added only once per user.
- The fragment menu includes “Logout” (which triggers sign-out and shows the Login Fragment) and “Heart” (which navigates to the Favorites Fragment and pushes the current fragment on the back stack).

## Part 4: Favorites Fragment
- Setup a snapshot listener to retrieve the current user’s favorite podcasts from Firestore.
  - Parse and display each favorite podcast similarly to the Podcasts Fragment.
- Tapping the “Trash” icon deletes the selected podcast from the favorite list.
- A “Back” button pops the back stack to return to the previous screen.

This README outlines the functional requirements and design goals for managing podcasts and user favorites using Firebase Authentication and Firestore, alongside OkHTTP and JSON parsing for API data. Additional UI and business logic are provided in the skeleton code.
