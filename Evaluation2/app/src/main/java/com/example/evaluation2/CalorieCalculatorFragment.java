package com.example.evaluation2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.evaluation2.databinding.FragmentCalorieCalculatorBinding;

public class CalorieCalculatorFragment extends Fragment {
    private FragmentCalorieCalculatorBinding binding;
    private CalorieCalculatorFragmentListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CalorieCalculatorFragmentListener) {
            listener = (CalorieCalculatorFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement CalorieCalculatorFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCalorieCalculatorBinding.inflate(inflater, container, false);

        binding.buttonGenderSelect.setOnClickListener(v -> listener.openSelectGenderFragment());
        binding.buttonWeightSelect.setOnClickListener(v -> listener.openSelectWeightFragment());
        binding.buttonHeightSelect.setOnClickListener(v -> listener.openSelectHeightFragment());
        binding.buttonAgeSelect.setOnClickListener(v -> listener.openSelectAgeFragment());
        binding.buttonActivityLevelSelect.setOnClickListener(v -> listener.openSelectActivityLevelFragment());
        binding.buttonSubmit.setOnClickListener(v -> submit());

        return binding.getRoot();
    }

    private void submit() {
        String gender = binding.textViewGender.getText().toString();
        String weight = binding.textViewWeight.getText().toString();
        String height = binding.textViewHeight.getText().toString();
        String age = binding.textViewAge.getText().toString();
        String activityLevel = binding.textViewActivityLevel.getText().toString();

        if (gender.equals("N/A") || weight.equals("N/A") || height.equals("N/A") || age.equals("N/A") || activityLevel.equals("N/A")) {
            Toast.makeText(getActivity(), "Please select all values", Toast.LENGTH_SHORT).show();
        } else {
            Calorie calorie = new Calorie(gender, weight, height, age, activityLevel);
            listener.submit(calorie);
        }
    }

    public void updateSelectedValue(String tag, String value) {
        switch (tag) {
            case "gender":
                binding.textViewGender.setText(value);
                break;
            case "weight":
                binding.textViewWeight.setText(value);
                break;
            case "height":
                binding.textViewHeight.setText(value);
                break;
            case "age":
                binding.textViewAge.setText(value);
                break;
            case "activityLevel":
                binding.textViewActivityLevel.setText(value);
                break;
        }
    }
}