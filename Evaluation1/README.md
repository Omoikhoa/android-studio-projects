# BAC Calculator App

## Overview
This app calculates the Blood Alcohol Content (BAC) based on user inputs including weight, gender, and the number of drinks consumed. The UI and functionality follow the design and subsequent figures.

## Part 1: Main Screen (Layout and Initialization)
- **Layout & Resources:**
  - All text (labels, hints, button texts) is defined in strings.xml.
  - The weight input EditText uses a hint ("Enter Weight") as shown.
  - Drink images (e.g., beer, wine, liquor, malt liquor) are imported and displayed accordingly.
- **Initial Setup:**
  - The UI is initialized with default values for the drink counts, gender selection, and BAC level.

## Part 2: Main Screen (Event Handlers)
- **User Input:**
  - The user enters their weight and selects their gender.
- **Drink Counters:**
  - Each drink type has “+” and “–” buttons:
    - The “+” button increments the drink count up to a maximum of 10.
    - The “–” button decrements the count down to a minimum of 0.
- **Calculate Button:**
  - Validates the weight input; if invalid, a dialog alerts the user.
  - Ensures that at least one drink is selected.
  - Calculates BAC using the formula:
    ```
    BAC = (A × 5.14) / (W × r)
    ```
    where A is the total alcohol in ounces, W is weight in lbs, and r is a gender-specific constant (0.73 for men, 0.66 for women).
  - Updates the BAC display and shows the status message:
    - **Green ("You're Safe")** if 0 ≤ BAC ≤ 0.08.
    - **Orange ("Be careful.")** if 0.08 < BAC ≤ 0.2.
    - **Red ("Over the limit!")** if BAC > 0.2.
- **Reset Button:**
  - Resets the UI to its initial state:
    - Clears the weight input.
    - Resets drink counts to 0.
    - Sets the BAC level to 0.00% and restores the default status message.
    - Resets the gender selection to the default.

## BAC Calculation Details
- **Calculation Example:**
  - For instance, given a 150 lbs individual and specific drink counts, the app computes A (total alcohol ounces) and then BAC using the provided formula.
- **Additional Features:**
  - Error dialogs appear if the weight is invalid or no drinks are selected.

This README provides a complete overview of the BAC Calculator App, detailing both the UI/initialization and event-handling logic.
