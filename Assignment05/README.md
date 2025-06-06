# Single Activity Multi-Fragment User Info App

## Overview
This application is built using one Activity (MainActivity) and multiple fragments. All communication between the fragments is managed by MainActivity. The app allows the user to create, view, and edit their profile information.

## Part 1: Welcome Fragment
- Displays a banner photo.
- Contains a "Start" button which, when clicked, communicates with MainActivity to:
  - Replace the Welcome Fragment with the Create User Fragment.
  - Push the Welcome Fragment on the back stack if needed.

## Part 2: Create User Fragment
- Requests user input for:
  - **Name** (with hint "Enter Name")
  - **Email** (with hint "Enter Email")
  - **Role** (options: Student, Employee, Other)
- On clicking the "Next" button:
  - Validates that all inputs are provided.
  - If any input is missing, shows a Toast message indicating the required information.
  - If all inputs are valid, creates a User object (which implements Serializable or Parcelable) with the provided data.
  - Communicates with MainActivity to replace the current fragment with the Profile Fragment and passes the User object. The current fragment is pushed to the back stack.

## Part 3: Profile Fragment
- Receives the User object from the Create User Fragment and displays the user's profile information (Name, Email, Role).
- Contains an "Update" button which, when clicked, communicates with MainActivity to:
  - Replace the Profile Fragment with the Edit User Fragment.
  - Pass the current User object to the Edit User Fragment.
  - Add the Profile Fragment to the back stack.

## Part 4: Edit User Fragment
- Receives the User object from the Profile Fragment and uses it to initialize the input fields (EditTexts for name and email, and RadioButtons for role).
- On clicking the "Submit" button:
  - Validates that all inputs are provided; if not, shows a Toast message for missing input.
  - If valid, creates a new User object with the updated information.
  - Sends the updated User object back to MainActivity, which locates the Profile Fragment (by tag), updates it with the new user data, and pops the back stack to display the updated Profile Fragment.
- Clicking the "Cancel" button simply communicates with MainActivity to pop the back stack, returning to the Profile Fragment without any updates.
