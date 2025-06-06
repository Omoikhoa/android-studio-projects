package com.example.evaluation2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.evaluation2.databinding.FragmentSelectGenderBinding;

public class SelectGenderFragment extends Fragment {

    private FragmentSelectGenderBinding binding;
    private SelectGenderFragmentListener listener;

    public interface SelectGenderFragmentListener {
        void onGenderSelected(String gender);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSelectGenderBinding.inflate(inflater, container, false);

        binding.buttonGenderSubmit.setOnClickListener(v -> {
            int selectedId = binding.radioGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(getActivity(), "Please select a gender", Toast.LENGTH_SHORT).show();
            } else {
                RadioButton radioButton = binding.radioGroup.findViewById(selectedId);
                listener.onGenderSelected(radioButton.getText().toString());
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        binding.buttonGenderCancel.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());

        return binding.getRoot();
    }
}