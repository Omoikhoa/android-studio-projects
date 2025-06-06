//Assignment 05
//Assignment05.zip
//Alex Ilevbare

package com.example.assignment05;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WelcomeFragment extends Fragment {

    private FragmentNavigationListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        listener = (FragmentNavigationListener) getActivity();

        view.findViewById(R.id.buttonStart).setOnClickListener(v -> {
            if (listener != null) {
                listener.onStartClicked();
            }
        });

        return view;
    }
}
