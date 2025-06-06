package com.example.evaluation2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.evaluation2.databinding.FragmentSelectAgeBinding;

public class SelectAgeFragment extends Fragment {

    private FragmentSelectAgeBinding binding;
    private SelectAgeFragmentListener listener;

    public interface SelectAgeFragmentListener {
        void onAgeSelected(String age);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSelectAgeBinding.inflate(inflater, container, false);

        binding.buttonAgeSubmit.setOnClickListener(v -> {
            String age = binding.editTextNumberDecimalAge.getText().toString();
            if (age.isEmpty()) {
                Toast.makeText(getActivity(), "Please enter an age", Toast.LENGTH_SHORT).show();
            } else {
                listener.onAgeSelected(age);
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        binding.buttonAgeCancel.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());

        return binding.getRoot();
    }
}