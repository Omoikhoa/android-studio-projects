package com.example.evaluation2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.evaluation2.databinding.FragmentSelectHeightBinding;

public class SelectHeightFragment extends Fragment {

    private FragmentSelectHeightBinding binding;
    private SelectHeightFragmentListener listener;

    public interface SelectHeightFragmentListener {
        void onHeightSelected(String height);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSelectHeightBinding.inflate(inflater, container, false);

        binding.buttonHeightSubmit.setOnClickListener(v -> {
            String heightFt = binding.editTextNumberDecimalHeightFt.getText().toString();
            String heightIn = binding.editTextNumberDecimalHeightIn.getText().toString();
            if (heightFt.isEmpty() || heightIn.isEmpty()) {
                Toast.makeText(getActivity(), "Please enter height in feet and inches", Toast.LENGTH_SHORT).show();
            } else {
                listener.onHeightSelected(heightFt + "ft " + heightIn + "in");
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        binding.buttonHeightCancel.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());

        return binding.getRoot();
    }
}