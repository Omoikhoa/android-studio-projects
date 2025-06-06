# Temperature Conversion App

This mobile application enables users to convert temperatures between Celsius and Fahrenheit using radio buttons for selecting the conversion method. It demonstrates essential Android components and event handling.

## User Interface

- **Layout & Resources:**  
  The UI is built using layout XML files, with all text values (e.g., button labels, hints) defined in strings.xml.
  
- **Components:**  
  - An **EditText** with a hint "Enter Temperature" for user input.
  - A **TextView** that displays the conversion result (initially set to "N/A").
  - A **RadioGroup** containing:
    - **RadioButton "C to F"** (default selected)
    - **RadioButton "F to C"**
  - Two buttons labeled **"Calculate"** and **"Reset"**.

## Functionality

### Calculate Button

When the "Calculate" button is pressed:

1. The app retrieves the numeric value entered in the EditText.
2. It checks which RadioButton is selected to decide the conversion:
   - If "C to F" is selected, it calculates Fahrenheit using:  
     F = (C × 9/5) + 32.
   - If "F to C" is selected, it calculates Celsius using:  
     C = (F − 32) × 5/9.
3. The conversion result is displayed in the designated TextView.

### Reset Button

Clicking the "Reset" button will:

- Clear the temperature input.
- Reset the conversion result to "N/A".
- Re-select the default conversion ("C to F").

### Error Handling

- The app validates the input to ensure a valid number is entered.
- If the input is missing or invalid (e.g., non-numeric characters), a Toast message is displayed to indicate the error, preventing the conversion.

## Screenshots

The expected screens include:
- **Initial Screen:** Empty input, "N/A" conversion result, and "C to F" selected.
- **Successful Conversion:** Displays the converted temperature in either Celsius or Fahrenheit based on the selected RadioButton.

This README provides an overview of the Temperature Conversion App, highlighting its user interface and core functionality.
