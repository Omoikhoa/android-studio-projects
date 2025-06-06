// Assignment 07
// MainActivity.java
// Alex Ilevbare
package com.example.assignment07;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements GenresFragment.GenresListener, BooksFragment.BooksListener, BookDetailsFragment.BookDetailsListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rootView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new GenresFragment())
                .commit();

    }

    @Override
    public void onSelectedGenre(String genre) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, BooksFragment.newInstance(genre))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSelectedBook(Book book) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, BookDetailsFragment.newInstance(book))
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onBackButtonPressed() {
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void onBackDetailButtonPressed() {
        getSupportFragmentManager().popBackStack();
    }
}