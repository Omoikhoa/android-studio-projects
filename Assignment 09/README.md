# Credit Score Users Management App

This application uses one Main Activity and multiple fragments to manage users and their credit scores. All user data is stored locally using the Rooms library. All communication between fragments is handled through the Main Activity.

## Setup
- Add the Rooms Library to your Gradle file.
- The Main Activity holds the ArrayList of User objects which is persisted in a local Rooms database.
- When the app starts, the Main Activity loads the Users Fragment as the initial fragment.

## Part 1: Users Fragment
- **Display:**
  - Retrieves the list of users from the Rooms database (via Main Activity) and displays them in a RecyclerView.
  - Each row shows the user’s credit score (and corresponding credit category image), name, age, and state.
- **Interactions:**
  1. **Add User:**  
     - Clicking the “+” menu item replaces the current fragment with the Add User Fragment and pushes the Users Fragment onto the back stack.  
     - Upon return, the fragment retrieves the updated user list from the Rooms database and refreshes the RecyclerView.
  2. **Delete User:**  
     - Tapping the trash icon on a row sends a delete command to the Main Activity which removes the user from the Rooms database.  
     - The fragment then refreshes its RecyclerView with the updated user list.
  3. **Filter:**  
     - Clicking the “Filter” icon replaces the current fragment with the Filter Fragment (back stack push).  
     - Upon return, the Users Fragment receives the selected filter criteria (i.e. a minimum credit score category to display) and refreshes to show only qualifying users.
  4. **Sort:**  
     - Clicking the “Sort” icon replaces the current fragment with the Sort Fragment (back stack push).  
     - When a sort attribute is selected (default sort direction is ascending), the Users Fragment refreshes its list sorted accordingly and updates the sort label.
  5. **Sort Direction:**  
     - Clicking the “Ascending” or “Descending” icon updates the sort direction, re-sorts the list using the selected attribute, and updates the sort label.

## Part 2: Add User Fragment
- Displays a form to add a new user with fields for Name, Age, Credit Score, and State.
- **State Selection:**  
  - Tapping “Select” launches the States Fragment (with back stack push).  
  - On return, the selected State is passed back and displayed in the form.
- **Submission:**  
  - Validates that all fields are filled and that the credit score is between 300 and 850.  
  - If validation passes, a User object is created and sent to the Main Activity, which adds it to the Rooms database and pops the back stack.

## Part 3: States Fragment
- Displays a list of State objects using a simple ArrayAdapter.
- Selecting a state sends the selected State back to the Main Activity, which then passes it to the Add User Fragment; cancelling simply pops the back stack.

## Part 4: Filter Fragment
- Allows the user to select a credit score filtering criterion via a ListView with a custom ArrayAdapter that shows credit category images and text.
- Selecting a category sends the chosen CreditCategory to the Main Activity, which then passes it to the Users Fragment to filter the displayed list.  
- Selecting “No Filter” clears any filtering criteria.

## Part 5: Sort Fragment
- Allows selection of a sort attribute (e.g. Name, Age, Credit Score, State) via a simple ArrayAdapter in a ListView.
- When a sort criteria is selected, it is sent to the Main Activity, which passes it to the Users Fragment to sort the list in ascending order by default.  
- Cancelling simply pops the back stack.

This README provides an overview of the app structure and requirements for managing users and their credit scores with data stored locally via the Rooms library.
