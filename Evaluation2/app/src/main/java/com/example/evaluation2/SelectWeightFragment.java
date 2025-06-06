package com.example.evaluation2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.evaluation2.databinding.FragmentSelectWeightBinding;

public class SelectWeightFragment extends Fragment {

    private FragmentSelectWeightBinding binding;
    private SelectWeightFragmentListener listener;

    public interface SelectWeightFragmentListener {
        void onWeightSelected(String weight);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSelectWeightBinding.inflate(inflater, container, false);

        binding.buttonWeightSubmit.setOnClickListener(v -> {
            String weight = binding.editTextNumberDecimalWeight.getText().toString();
            if (weight.isEmpty()) {
                Toast.makeText(getActivity(), "Please enter a weight", Toast.LENGTH_SHORT).show();
            } else {
                listener.onWeightSelected(weight);
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        binding.buttonWeightCancel.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());

        return binding.getRoot();
    }
}