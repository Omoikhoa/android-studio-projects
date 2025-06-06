//Assignment 05
//Assignment05.zip
//Alex Ilevbare

package com.example.assignment05;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private static final String ARG_USER = "user";
    private User user;
    private FragmentNavigationListener listener;

    public static ProfileFragment newInstance(User user) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        listener = (FragmentNavigationListener) getActivity();
        user = getArguments().getParcelable(ARG_USER);

        TextView textViewName = view.findViewById(R.id.textViewName);
        TextView textViewEmail = view.findViewById(R.id.textViewEmail);
        TextView textViewRole = view.findViewById(R.id.textViewRole);

        textViewName.setText("Name: " + user.getName());
        textViewEmail.setText("Email: " + user.getEmail());
        textViewRole.setText("Role: " + user.getRole());

        view.findViewById(R.id.buttonUpdate).setOnClickListener(v -> {
            if (listener != null) {
                listener.onUpdateClicked(user);
            }
        });

        return view;
    }

    public void updateUser(User newUser) {
        this.user = newUser;
        if (getView() != null) {
            TextView textViewName = getView().findViewById(R.id.textViewName);
            TextView textViewEmail = getView().findViewById(R.id.textViewEmail);
            TextView textViewRole = getView().findViewById(R.id.textViewRole);

            textViewName.setText("Name: " + user.getName());
            textViewEmail.setText("Email: " + user.getEmail());
            textViewRole.setText("Role: " + user.getRole());
        }
    }

}
