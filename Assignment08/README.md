# RecyclerView Task Manager App

## Overview
This application uses one Main Activity and multiple fragments to manage tasks using a RecyclerView. The Main Activity stores an ArrayList of Task objects and handles all communication between fragments via defined interfaces.

## Part 1: Tasks Fragment
- **Display & Data Source:**
  - Receives the ArrayList of tasks from the Main Activity using an interface.
  - Displays tasks in a RecyclerView; each row shows the task name, priority, and date.
- **Functionality:**
  - **Clear Button:**  
    - Communicates with the Main Activity to remove all tasks from the ArrayList and refreshes the RecyclerView.
  - **New Button:**  
    - Replaces the current fragment with the Create Task Fragment (pushing the current fragment on the back stack).  
    - Upon return, the fragment requests the updated ArrayList from the Main Activity and refreshes the RecyclerView.
  - **Sort Button:**  
    - Replaces the current fragment with the Select Sort Fragment (pushing the current fragment on the back stack).  
    - On return, the selected sort criteria is applied and the RecyclerView is updated.
  - **Trash Can Icon:**  
    - Clicking the icon on a row communicates with the Main Activity to delete the corresponding task from the ArrayList, updates the local copy, and refreshes the RecyclerView.
  - **Row Item Click:**  
    - Sends the selected Task object to the Main Activity, which then replaces the fragment with the Task Summary Fragment (and pushes the current fragment onto the back stack).

## Part 2: Task Summary Fragment
- **Display:**
  - Receives a Task object from the Tasks Fragment and displays its details (name, priority, date).
- **Actions:**
  - **Delete Button:**  
    - Uses an interface to instruct the Main Activity to remove the displayed task from the ArrayList, then pops the back stack to return to the Tasks Fragment.
  - **Back Button:**  
    - Communicates with the Main Activity to pop the back stack, returning to the Tasks Fragment.

This README provides an overview of the key features and fragment interactions for the RecyclerView Task Manager App.
