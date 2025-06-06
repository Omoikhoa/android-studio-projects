# Book Library App

This app uses a single Activity (MainActivity) and multiple fragments to display books using ListViews. All communication between fragments is managed by the MainActivity. When the app starts, the Genres Fragment is loaded as the initial fragment.

## Part 1: Genres Fragment
- Displays a ListView of genres. The list is retrieved from the Data class.
- A simple adapter is used to show genre string values.
- Clicking a genre row communicates with MainActivity to:
  - Send the selected genre.
  - Replace the current fragment with the Books Fragment.
  - Push the current fragment on the back stack.

## Part 2: Books Fragment
- Receives the selected genre and displays it as the title.
- Retrieves the list of books for the genre using the Data class.
- Uses a ListView with a custom ArrayAdapter to display each book's title, author, year, and genre.
- Clicking "Back" uses an interface to pop the back stack (returning to the Genres Fragment).
- Clicking a book item communicates with MainActivity to:
  - Send the selected Book.
  - Replace the current fragment with the Book Details Fragment.
  - Push the current fragment on the back stack.

## Part 3: Book Details Fragment
- Receives a Book object from the Books Fragment and displays its details.
- Clicking "Back" communicates with MainActivity to pop the back stack (returning to the Books Fragment).

...
