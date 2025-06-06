# ToDo Lists Management App

This application uses REST APIs to manage ToDo Lists for multiple users. All HTTP and JSON parsing libraries (such as OkHttp, Retrofit, Gson, etc.) have been imported as required. The app is structured as a single-activity application with multiple fragments. All API calls use the provided endpoints and responses are parsed from JSON. The authentication token is stored in SharedPreferences.

## Part 1: Login Fragment
- On entering email and password:
  - If inputs are non-empty, a GET/POST request is made to the `/api/login` endpoint.
  - On success, the returned auth token (and user info) is parsed and saved to SharedPreferences. The fragment is then replaced with the ToDo Lists Fragment.
  - If the API returns an error or inputs are missing, an alert dialog displays the corresponding message.
- Clicking "Create New Account" navigates to the SignUp Fragment.

## Part 2: SignUp Fragment
- Provides input fields for first name, last name, email and password.
- On clicking Submit:
  - If all inputs are provided, a call is made to `/api/signup`. 
  - On success, the authentication token is parsed, saved to SharedPreferences, and the app navigates to the ToDo Lists Fragment.
  - On failure or missing input, an alert dialog shows the error.
- Clicking "Cancel" returns to the Login Fragment.

## Part 3: Checking User Authentication
- On Main Activity launch, the app checks SharedPreferences for an auth token.
  - If a token is present, the user is considered authenticated and the ToDo Lists Fragment is loaded.
  - If absent, the Login Fragment is displayed.
- On user logout, the token is deleted from SharedPreferences.

## Part 4: ToDo Lists Fragment
- Retrieves the list of ToDo Lists for the logged-in user by calling the `/api/todolists` API with an Authorization header.
- The JSON response is parsed and the lists are displayed in a RecyclerView.
- Tapping the trash icon:
  - Shows an alert dialog and then calls `/api/todolists/delete` to delete the selected list.
  - After deletion, the API is re-called to refresh the list.
- The "Logout" button clears the token and returns the user to the Login Fragment.

## Part 5: Create ToDo List Fragment
- Allows the user to create a new ToDo List.
- Clicking "Cancel" pops the fragment back stack.
- Entering a valid list name and clicking Submit calls the `/api/todolists/create` API (with Authorization header). On success, the fragment pops back to the ToDo Lists Fragment.

## Part 6: ToDo List Details Fragment
- Displays details of a selected ToDo List by retrieving its items using `/api/todolists/{todolist_id}` API.
- The JSON response is parsed and the items are displayed (e.g., item name, priority).
- Tapping the trash icon on a list item:
  - Shows an alert dialog, then calls `/api/todolist-items/delete` to remove the item.
  - Upon success, the list is refreshed by re-calling `/api/todolists/{todolist_id}`.

## Part 7: Add ToDo List Item Fragment
- Enables the user to add a new item to an existing ToDo List.
- Clicking "Cancel" pops the fragment back stack.
- Once the user enters all required fields and submits, a call is made to the `/api/todolist-items/create` API with the Authorization header. If the call is successful, the fragment is popped from the back stack.

This README summarizes the functional requirements of the app. The provided skeleton contains all necessary UI components and classes; the implementation focuses on making API calls, JSON parsing, and appropriate state management (using SharedPreferences for token storage).
