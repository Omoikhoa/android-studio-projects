// Assignment 07
// BookDetailsFragment.java
// Alex Ilevbare
package com.example.assignment07;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignment07.databinding.FragmentBookDetailsBinding;

public class BookDetailsFragment extends Fragment {

    private static final String ARG_PARAM_BOOK = "ARG_PARAM_BOOK";
    private Book mBook;

    public BookDetailsFragment() {
        // Required empty public constructor
    }

    public static BookDetailsFragment newInstance(Book book) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_BOOK, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBook = (Book) getArguments().getSerializable(ARG_PARAM_BOOK);
        }
    }

    FragmentBookDetailsBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBookDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(mBook.getTitle());
        binding.textViewBookTitle.setText(mBook.getTitle());
        binding.textViewAuthorName.setText(mBook.getAuthor());
        binding.textViewGenre.setText(mBook.getGenre());
        binding.textViewYear.setText(String.valueOf(mBook.getYear()));

        binding.buttonBookDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBackDetailButtonPressed();
            }
        });
    }

    BookDetailsListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (BookDetailsListener) context;
    }

    public interface BookDetailsListener {
        void onBackDetailButtonPressed();
    }
}