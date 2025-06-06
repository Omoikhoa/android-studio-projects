# Color Selection App

This application allows users to create and adjust colors using RGB values. It demonstrates the use of common Android components and interactive event handling.

## Part 1: Building the Interface

- **Layout & Resources:**
  - The UI is constructed with layout XML files, and all text for labels, button texts, and hints are defined in strings.xml.
  - The interface follows the design shown in Figure 1.

- **Initial Setup:**
  - Three SeekBars are provided for Red, Green, and Blue channels, each with a maximum value of 255.
  - When the app starts, the SeekBars are initialized with:
    - Red: 64
    - Green: 128
    - Blue: 0
  - A colored view is used to display the current color using `Color.rgb(red, green, blue)`.
  - Under the color display, the RGB and HEX values are shown.

## Part 2: Event Handlers

- **Reset Button:**
  - When clicked, it resets the SeekBars to the initial values (Red: 64, Green: 128, Blue: 0).
  - The progress TextViews are updated accordingly.
  - The displayed color and its RGB/HEX values are updated to reflect the initial state.

- **SeekBar Handling:**
  - Each SeekBar includes an event handler that updates:
    - The adjacent TextView to show the current value.
    - The colored view to reflect the combined RGB values.
    - The RGB and HEX display below the color view.

- **Predefined Color Buttons:**
  - The buttons labeled "White", "Black", and "Blue" allow the user to set the color to a preset value.
  - Clicking any of these buttons updates:
    - The SeekBar progress for each color channel according to the preset.
    - The associated progress TextViews.
    - The colored view and the RGB/HEX values display.

This README provides an overview of the app’s design and functionality, ensuring that the interface and interactive elements work together to create an intuitive color selection experience.
