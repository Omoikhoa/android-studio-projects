//Assignment 05
//Assignment05.zip
//Alex Ilevbare

package com.example.assignment05;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EditUserFragment extends Fragment {

    private static final String ARG_USER = "user";
    private User user;
    private FragmentNavigationListener listener;

    private EditText editTextTextName, editTextTextEmail;
    private RadioGroup radioGroupRole;

    public static EditUserFragment newInstance(User user) {
        EditUserFragment fragment = new EditUserFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_user, container, false);

        listener = (FragmentNavigationListener) getActivity();
        editTextTextName = view.findViewById(R.id.editTextTextName);
        editTextTextEmail = view.findViewById(R.id.editTextTextEmail);
        radioGroupRole = view.findViewById(R.id.RadioGroupRole);

        if (getArguments() != null) {
            user = getArguments().getParcelable(ARG_USER);
            if (user != null) {
                editTextTextName.setText(user.getName());
                editTextTextEmail.setText(user.getEmail());

                // Set the correct role selection
                if (user.getRole().equals("Student")) {
                    ((RadioButton) view.findViewById(R.id.radioButtonStudent)).setChecked(true);
                } else if (user.getRole().equals("Employee")) {
                    ((RadioButton) view.findViewById(R.id.radioButtonEmployee)).setChecked(true);
                } else {
                    ((RadioButton) view.findViewById(R.id.radioButtonOther)).setChecked(true);
                }
            }
        }

        view.findViewById(R.id.buttonSubmit).setOnClickListener(v -> handleSubmit());
        view.findViewById(R.id.buttonCancel).setOnClickListener(v -> handleCancel());

        return view;
    }

    private void handleSubmit() {
        String name = editTextTextName.getText().toString().trim();
        String email = editTextTextEmail.getText().toString().trim();
        int selectedId = radioGroupRole.getCheckedRadioButtonId();

        if (name.isEmpty() || email.isEmpty() || selectedId == -1) {
            Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        View view = getView(); // Ensure getView() is not null
        if (view == null) return;

        RadioButton selectedRadio = view.findViewById(selectedId);
        String role = selectedRadio.getText().toString();

        User updatedUser = new User(name, email, role);

        if (listener != null) {
            listener.onUserUpdated(updatedUser);
        }
    }


    private void handleCancel() {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }
}
