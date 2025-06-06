// CalorieDetailsFragment.java
package com.example.evaluation2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CalorieDetailsFragment extends Fragment {

    private static final String ARG_CALORIE = "calorie";

    private Calorie calorie;

    public CalorieDetailsFragment() {

        // Required empty public constructor

    }

    public static CalorieDetailsFragment newInstance(Calorie calorie) {
        CalorieDetailsFragment fragment = new CalorieDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CALORIE, calorie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            calorie = (Calorie) getArguments().getSerializable(ARG_CALORIE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calorie_details, container, false);

        TextView textViewGender = view.findViewById(R.id.textViewGenderDetail);
        TextView textViewWeight = view.findViewById(R.id.textViewWeightDetail);
        TextView textViewHeight = view.findViewById(R.id.textViewHeightDetail);
        TextView textViewAge = view.findViewById(R.id.textViewAgeDetail);
        TextView textViewActivityLevel = view.findViewById(R.id.textViewActivityLevelDetail);
        TextView textViewBMR = view.findViewById(R.id.textViewBMR);
        TextView textViewTDEE = view.findViewById(R.id.textViewTDEE);

        textViewGender.setText(calorie.getGender());
        textViewWeight.setText(calorie.getWeight());
        textViewHeight.setText(calorie.getHeight());
        textViewAge.setText(calorie.getAge());
        textViewActivityLevel.setText(calorie.getActivityLevel());

        double bmr = calculateBMR(calorie);
        double tdee = calculateTDEE(bmr, calorie.getActivityLevel());

        textViewBMR.setText(String.format("%.2f kcal/day", bmr));
        textViewTDEE.setText(String.format("%.2f kcal/day", tdee));

        return view;
    }

    private double calculateBMR(Calorie calorie) {
        double weightKg = Double.parseDouble(calorie.getWeight()) / 2.205;
        String[] heightParts = calorie.getHeight().split("ft |in");
        int heightFt = Integer.parseInt(heightParts[0]);
        int heightIn = Integer.parseInt(heightParts[1]);
        double heightCm = (heightFt * 12 + heightIn) * 2.54;
        int age = Integer.parseInt(calorie.getAge());

        if (calorie.getGender().equalsIgnoreCase("female")) {
            return (10 * weightKg) + (6.25 * heightCm) - (5 * age) - 161;
        } else {
            return (10 * weightKg) + (6.25 * heightCm) - (5 * age) + 5;
        }
    }

    private double calculateTDEE(double bmr, String activityLevel) {
        switch (activityLevel.toLowerCase()) {
            case "sedentary":
                return bmr * 1.2;
            case "lightly active":
                return bmr * 1.375;
            case "moderately active":
                return bmr * 1.55;
            case "very active":
                return bmr * 1.725;
            case "super active":
                return bmr * 1.9;
            default:
                return bmr;
        }
    }
}