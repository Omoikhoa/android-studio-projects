# ToDo Lists Management App

## Overview
This app uses the Rooms library to manage ToDo Lists for multiple users. It is built with a single Main Activity and multiple fragments. Data is stored locally using the Rooms library so that each user can only access their own lists. All communication between fragments is performed through the Main Activity.

## Requirements

### Part 1: Users Fragment
- Retrieve all users from the Rooms database.
- Display the users in a RecyclerView.
- Tapping a user row sends the selected user to the Enter Passcode fragment.

### Part 2: Add User Fragment
- Provide a form to add a new user (name and passcode).
- Store the new user in the Rooms database.

### Part 3: Enter Passcode Fragment
- Verify that the entered passcode matches the one stored for the selected user.

### Part 4: ToDo Lists Fragment
- Retrieve all ToDo lists belonging to the logged in user from the Rooms database.
- Display the list of ToDo lists.
- Tapping the trash icon deletes the corresponding ToDo list, then refreshes the list.

### Part 5: Create ToDo List Fragment
- Provide a form to create a new ToDo list.
- Store the new list in the Rooms database with an association to the logged in user.

### Part 6: List Details Fragment
- Retrieve all ToDo list items for the selected ToDo list from the Rooms database.
- Display the list items.
- Tapping the trash icon deletes the item and refreshes the list.

### Part 7: Add ToDo List Item Fragment
- Provide a form to add a new item to a selected ToDo list.
- Store the new item in the Rooms database under the selected ToDo list.

## Flow
- The Main Activity starts by loading the Users Fragment.
- The user selects a user row and is taken to the Enter Passcode fragment.
- Upon successful passcode entry, the user’s ToDo lists are displayed.
- The user can create new ToDo lists, view list details, add new items, and delete lists or individual items.

This README outlines the key features and requirements for the ToDo Lists Management App.
