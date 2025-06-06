// Assignment 07
// BooksFragment.java
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.assignment07.databinding.FragmentBooksBinding;

import java.util.ArrayList;
import java.util.List;


public class BooksFragment extends Fragment {

    private static final String ARG_PARAM_GENRE = "ARG_PARAM_GENRE";

    private String mGenre;
    private ArrayList<Book> bookArrayList;

    public BooksFragment() {
        // Required empty public constructor
    }

    public static BooksFragment newInstance(String Genre) {
        BooksFragment fragment = new BooksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_GENRE, Genre);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGenre = getArguments().getString(ARG_PARAM_GENRE);
        }
    }

    FragmentBooksBinding binding;
    BooksAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBooksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bookArrayList = Data.getBooksByGenre(mGenre);
        adapter = new BooksAdapter(getActivity(), bookArrayList);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = bookArrayList.get(position);
                listener.onSelectedBook(book);

            }
        });
        getActivity().setTitle(mGenre);
        binding.buttonBooksBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBackButtonPressed();
            }
        });
    }

    class BooksAdapter extends ArrayAdapter<Book>{
        public BooksAdapter(@NonNull Context context, @NonNull List<Book> objects) {
            super(context, R.layout.book_list_item, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.book_list_item, parent, false);
            }
            TextView textViewBookTitle = convertView.findViewById(R.id.textViewBookTitle);
            TextView textViewAuthorName = convertView.findViewById(R.id.textViewAuthorName);
            TextView textViewGenre = convertView.findViewById(R.id.textViewGenre);
            TextView textViewYear = convertView.findViewById(R.id.textViewYear);

            Book book = getItem(position);
            textViewBookTitle.setText(book.getTitle());
            textViewAuthorName.setText(book.getAuthor());
            textViewGenre.setText(book.getGenre());
            textViewYear.setText(String.valueOf(book.getYear()));
            binding.textViewGenre.setText(mGenre);

            return convertView;
        }
    }

    BooksListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (BooksListener) context;
    }

    interface BooksListener{
        void onSelectedBook(Book book);
        void onBackButtonPressed();
    }
}