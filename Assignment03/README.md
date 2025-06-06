# Discount Calculator App

This project is an Android application designed to help users quickly calculate discounts on items. The app features a single activity where users can input an item’s price, select a discount option, and view the calculated discount and final price.

## User Interface Overview

- The interface is built using layout files, with all text elements sourced from strings.xml.
- An EditText field is provided for entering the item price. It accepts only positive numbers and displays the hint "Enter List Price" when empty.
- A RadioGroup allows the user to choose from preset discount options (10%, 15%, 18%) or select a Custom option, with 10% being the default.
- A SeekBar is available when the Custom option is selected. It is configured with a maximum of 50% and a default progress of 25%, with the current progress displayed in a nearby TextView.
- TextView components are included to show the discount amount and the final price, both of which are initially empty.

## App Functionality

- **Reset Functionality:**  
  The Reset button clears the input field, returns the discount option to 10%, resets the SeekBar to 25%, and sets the discount and final price to 0.00.

- **Calculation Process:**  
  Upon clicking the Calculate button:
  - If no item price is entered, the discount and final price remain empty and a message prompts the user to input a price.
  - Otherwise, the app determines the chosen discount percentage and computes the discount and final price accordingly.
  - When the Custom option is selected, the app uses the SeekBar's current value to perform the calculations.

## Additional Details

An interactive SeekBar handler updates the TextView in real time to display the current custom discount percentage, ensuring a responsive and user-friendly experience.

This README provides an overview of the app's interface and functionality, facilitating a smooth user experience for calculating discounts.
