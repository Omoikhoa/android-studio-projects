# Credit Score Users Management App

This app uses one Main Activity and multiple fragments to manage users and their credit scores. The Main Activity stores an ArrayList of User objects and handles all communication between fragments via interfaces. When the app starts, it loads the Users Fragment as the initial fragment.

## Part 1: Users Fragment
- **Data Source & Display:**
  - The Users Fragment receives the ArrayList of users from the Main Activity (via an interface) and displays them in a RecyclerView.
  - Each row displays the user’s name, age, credit score (with corresponding credit category icon) and state.
- **Interactions:**
  - **Add User ("+") Icon:**  
    Communicates with Main Activity to replace the current fragment with the Add User Fragment and push the Users Fragment onto the back stack. When returning, the updated user list is refreshed.
  - **Trash Icon:**  
    Clicking the trash icon on a row sends a delete command through an interface to Main Activity. Main Activity removes the user from the ArrayList and the RecyclerView is refreshed.
  - **Filter Icon:**  
    Replaces the current fragment with the Filter Fragment (pushing the Users Fragment onto the back stack). On return, the Users Fragment receives the selected filter criteria (minimum credit score category) and refreshes the list to show users in that category and above.
  - **Sort Icon:**  
    Replaces the current fragment with the Sort Fragment (pushing the Users Fragment onto the back stack). On return, the fragment receives the selected sort attribute (default sort direction is ascending) and refreshes the RecyclerView accordingly.
  - **Sort Direction:**  
    Tapping the Ascending or Descending icons updates the sort direction and refreshes the list. The sort label is updated to reflect the chosen attribute and direction.

## Part 2: Add User Fragment
- A form to add a new user with input fields for:
  - Name, Age, Credit Score, and State.
- **State Selection:**  
  The “Select” button communicates with Main Activity to replace the fragment with the States Fragment (pushing the current fragment onto the back stack). Returning to the Add User Fragment displays the selected State.
- **Submission:**  
  The Submit button validates that all fields are provided and that the credit score is between 300 and 850. If valid, a new User object is created and sent to Main Activity (which adds it to the ArrayList) and then pops the back stack to return to the Users Fragment.

## Part 3: States Fragment
- Displays a ListView of State objects using a simple ArrayAdapter.
- **Interactions:**
  - Clicking on a state sends the selected State object through an interface to the Main Activity. Main Activity then finds the Add User Fragment, sends it the state, and pops the back stack.
  - The Cancel button simply pops the back stack to return to the Add User Fragment.

## Part 4: Filter Fragment
- Enables filtering of users by their credit score category. Categories include Poor, Fair, Good, Very Good, and Excellent.
- **Display:**
  - Uses a custom ArrayAdapter to display each credit category with its image and text.
- **Interactions:**
  - Selecting a category sends the corresponding CreditCategory object via an interface to the Main Activity. The Main Activity then passes the filter criteria to the Users Fragment, which refreshes the RecyclerView to show only users with a credit score in the selected category or higher.
  - The "No Filter" option sends a null value to clear any filtering.

## Part 5: Sort Fragment
- Allows selection of sort criteria via a ListView using a simple ArrayAdapter. Available attributes include Name, Age, Credit Score, and State.
- **Interactions:**
  - Clicking a sort criteria sends the selected attribute through an interface to the Main Activity. Main Activity then notifies the Users Fragment to sort the ArrayList (default sort direction is ascending) and refreshes the view.
  - The Cancel button pops the back stack to return to the Users Fragment without changes.

This README provides an overview of the application structure and the functionality requirements for managing users and their credit scores using RecyclerViews/ListViews.
