# Multi-Activity User Info App

## Overview
This application demonstrates the use of multiple activities to exchange data through a User object. Users can create their profile information, view it, and update it using different screens.

## Part 1: Main Activity
- Displays a welcome screen with a banner photo.
- Contains a "Start" button that:
  - Launches the "Create User" activity.
  - Finishes the Main Activity.

## Part 2: Create User Activity
- Prompts the user to input:
  - **Name** (with the hint "Enter Name")
  - **Email** (with the hint "Enter Email")
  - **Role** (selection options: Student, Employee, Other)
- Clicking the "Next" button:
  - Validates that all inputs are provided; if any are missing, a Toast message alerts the user.
  - If all inputs are valid, a User object is created and sent to the Profile Activity.
  - The activity then finishes.

## Part 3: Profile Activity
- Receives the User object from the Create User Activity and displays it ("Name", "Email", and "Role").
- An "Update" button:
  - Launches the Edit User Activity for result, sending the current User object.
  - Upon returning, the updated User object is displayed.

## Part 4: Edit User Activity
- Receives the User object from the Profile Activity and uses it to initialize the input fields (EditTexts and RadioButtons).
- Clicking the "Submit" button:
  - Validates the inputs; if any fields are missing, a Toast message indicates the error.
  - If valid, a new User object is created with the updated information, sent back to the Profile Activity, and the activity finishes.
- Clicking the "Cancel" button simply finishes the activity and returns to the Profile Activity without any changes.
