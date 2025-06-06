// Assignment 07
// GenresFragment.java
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

import com.example.assignment07.databinding.FragmentGenresBinding;

import java.util.ArrayList;

public class GenresFragment extends Fragment {

    public GenresFragment() {
        // Required empty public constructor
    }
    FragmentGenresBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGenresBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    ArrayAdapter<String> adapter;
    ArrayList<String> genres;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        genres = Data.getAllGenres();

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, genres);
        binding.listView.setAdapter(adapter);
        getActivity().setTitle("Genres");

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String genre = genres.get(position);
                listener.onSelectedGenre(genre);

            }
        });
    }

    GenresListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (GenresListener) context;
    }

    interface GenresListener{
        void onSelectedGenre(String genre);
    }
}