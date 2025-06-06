# Single Activity Multi-Fragment Task Manager App

This application uses one Main Activity and multiple fragments. All communication between fragments is managed through the Main Activity. The app is designed to allow users to manage tasks with features to create, view, and delete tasks.

## General Structure
- A Task class holds each task's details (name, date, priority) and is stored in an ArrayList in Main Activity.
- The Main Activity loads the Tasks Fragment as the initial fragment.
- All fragment-to-fragment communication occurs via interfaces provided by Main Activity.

## Part 1: Tasks Fragment
- **Display:**  
  - Receives the list of Tasks from Main Activity.
  - Shows the total number of tasks at the top.
  - If no tasks exist, displays an empty state view.
- **Task Sorting & Navigation:**  
  - The tasks list is sorted by date in descending order (most recent first).
  - Navigation arrows (“<” and “>”) cycle through tasks (wrapping at the beginning/end) and display “Task X of Y”.
- **Deletion:**  
  - The “Trash Can” icon deletes the current task, updates the count, and shows the previous task.
- **Create Task:**  
  - The “Create Task” button communicates with Main Activity to replace the current fragment with the Create Task Fragment while pushing the Tasks Fragment onto the back stack.

## Part 2: Create Task Fragment
- **Input Fields:**  
  - Requests the task name, task date, and priority.
  - Task date is not pre-set; user must select it.
- **Set Date:**  
  - The “Set Date” button uses an interface to prompt Main Activity to load the Select Task Date Fragment (current fragment pushed to back stack).
  - Upon return, the selected date is displayed.
- **Actions:**  
  - “Cancel” pops the back stack returning to the Tasks Fragment.
  - “Submit” validates all inputs and task date; if valid, creates a Task object, sends it to Main Activity to add to the tasks list, then pops the back stack to return to the Tasks Fragment. Toast messages are shown on missing input.

## Part 3: Select Task Date Fragment
- **Date Selection:**  
  - Uses a DatePicker component with the maximum selectable date set to today.
- **Actions:**  
  - “Submit”: If a valid date is selected, uses an interface to send the date to Main Activity which then finds the Create Task Fragment (by tag) and passes the date to it. Finally, it pops the back stack to return to the Create Task Fragment.
  - “Cancel”: Communicates with Main Activity to simply pop the back stack and return to the Create Task Fragment.
