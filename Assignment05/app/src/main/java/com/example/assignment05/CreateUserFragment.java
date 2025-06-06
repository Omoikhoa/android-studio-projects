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

public class CreateUserFragment extends Fragment {

    private FragmentNavigationListener listener;
    private EditText editTextName, editTextEmail;
    private RadioGroup radioGroupRole;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_user, container, false);

        listener = (FragmentNavigationListener) getActivity();
        editTextName = view.findViewById(R.id.editTextTextName);
        editTextEmail = view.findViewById(R.id.editTextTextEmail);
        radioGroupRole = view.findViewById(R.id.RadioGroupRole);

        view.findViewById(R.id.buttonNext).setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            int selectedId = radioGroupRole.getCheckedRadioButtonId();

            if (name.isEmpty() || email.isEmpty() || selectedId == -1) {
                Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedRadio = view.findViewById(selectedId);
            User user = new User(name, email, selectedRadio.getText().toString());

            if (listener != null) {
                listener.onUserCreated(user);
            }
        });

        return view;
    }
}
