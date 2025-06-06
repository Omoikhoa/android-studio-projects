# Temperature Conversion App

This project is a simple mobile application that converts temperatures between Celsius and Fahrenheit. It demonstrates the use of Android components and basic event handling.

## Overview

- The app allows users to enter a temperature value and convert it between Celsius and Fahrenheit.
- The user interface is constructed using layout files and string resources from strings.xml.
- Three buttons are provided for conversion and reset functionalities.

## Functionality

### Using Buttons

- **UI Requirements:**
  - All button labels are defined in strings.xml.
  - The EditText displays a grayed-out hint "Enter Temperature" until the user inputs a number.
  
- **Button Handlers:**
  - **C to F:**  
    Converts the entered value from Celsius to Fahrenheit using the formula:  
    F = (C × 9/5) + 32.
  - **F to C:**  
    Converts the entered value from Fahrenheit to Celsius using the formula:  
    C = (F − 32) × 5/9.
  - **Reset:**  
    Clears the temperature input and resets the user interface to its initial state.

- **Error Handling:**
  - When no value is entered, an invalid number is provided, or special characters are detected:
    - A Toast message is displayed indicating the error.
    - Conversion is not performed until valid input is provided.
    
This README outlines the expected behavior and interface for the Temperature Conversion App.
