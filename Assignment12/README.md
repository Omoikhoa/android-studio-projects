# Firebase ToDo Lists Management App - Assignment 12

This is an individual assignment that builds on the concepts from Assignment 11. In this assignment, you will develop an app using Firebase for both Authentication and Data Storage instead of a local Rooms database.

## Overview
- The app is a single-activity, multi-fragment application.
- Firebase Authentication is used for user login and registration.
- Firebase Cloud Firestore is used to store and retrieve each user’s ToDo Lists and list items.
- All communication between fragments is handled through the Main Activity.

## Features
### Authentication
- **Login Fragment:**
  - Users enter email and password.
  - Firebase Authentication signs in users.
  - On successful login, the app navigates to the ToDo Lists Fragment.
- **SignUp Fragment:**
  - New users register by providing first name, last name, email, and password.
  - Firebase creates a new user account.
  - On success, the user is navigated to the ToDo Lists Fragment.
- Authentication details are managed using Firebase so that user sessions are maintained.

### ToDo List Management
- **ToDo Lists Fragment:**
  - Retrieves all ToDo Lists for the logged-in user from Cloud Firestore.
  - Lists are displayed in a RecyclerView.
  - A delete option allows the user to remove a list (with confirmation).
- **Create New ToDo List Fragment:**
  - Users can create a new list which is stored in Firestore and associated with their account.
- **List Details Fragment:**
  - Displays all items (tasks) for a selected ToDo List, retrieved from Firestore.
  - Users can delete individual list items with confirmation.
- **Add ToDo List Item Fragment:**
  - Provides a form to add a new item to an existing ToDo List.
  - The new item is stored in Firestore under the associated list.

## Implementation Guidelines
- Use Firebase Authentication SDK for user management.
- Use Cloud Firestore for real-time data storage and retrieval.
- Maintain proper fragment navigation and state management via the Main Activity.
- Utilize view binding for UI component access.
- Ensure that each user’s data is isolated by using their unique Firebase UID.

This README outlines the functional requirements and design goals for Assignment 12. Focus on using Firebase services to manage both authentication and data storage, and demonstrate an understanding of integrating Firebase into your mobile app.
