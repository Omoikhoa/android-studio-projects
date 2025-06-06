package com.example.evaluation2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.evaluation2.databinding.FragmentActivityLevelBinding;

public class ActivityLevelFragment extends Fragment {

    private FragmentActivityLevelBinding binding;
    private ActivityLevelFragmentListener listener;

    public interface ActivityLevelFragmentListener {
        void onActivityLevelSelected(String activityLevel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentActivityLevelBinding.inflate(inflater, container, false);

        binding.buttonSedentary.setOnClickListener(v -> selectActivityLevel("Sedentary"));
        binding.buttonLightlyActive.setOnClickListener(v -> selectActivityLevel("Lightly Active"));
        binding.buttonModeratelyActive.setOnClickListener(v -> selectActivityLevel("Moderately Active"));
        binding.buttonVeryActive.setOnClickListener(v -> selectActivityLevel("Very Active"));
        binding.buttonSuperActive.setOnClickListener(v -> selectActivityLevel("Super Active"));
        binding.buttonActivityLevelCancel.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());

        return binding.getRoot();
    }

    private void selectActivityLevel(String activityLevel) {
        listener.onActivityLevelSelected(activityLevel);
        getActivity().getSupportFragmentManager().popBackStack();
    }
}