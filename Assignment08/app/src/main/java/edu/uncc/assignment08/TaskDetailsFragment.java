// Assignment 08
// TaskDetailsFragment.java
// Alex Ilevbare
package edu.uncc.assignment08;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uncc.assignment08.databinding.FragmentTaskDetailsBinding;
import edu.uncc.assignment08.models.Task;

public class TaskDetailsFragment extends Fragment {
    private static final String ARG_PARAM_TASK = "ARG_PARAM_TASK";
    private Task mTask;
    TaskDetailsListener mListener;

    public TaskDetailsFragment() {
        // Required empty public constructor
    }

    public static TaskDetailsFragment newInstance(Task task) {
        TaskDetailsFragment fragment = new TaskDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_TASK, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTask = (Task)getArguments().getSerializable(ARG_PARAM_TASK);
        }
    }

    FragmentTaskDetailsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTaskDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Task Details");

        binding.textViewTaskName.setText(mTask.getName());
        binding.textViewPriority.setText(String.valueOf(mTask.getPriorityString()));
        binding.textViewDate.setText(mTask.getDate().toString());

        binding.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.deleteTask(mTask);
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof TaskDetailsListener) {
            mListener = (TaskDetailsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement TaskDetailsListener");
        }
    }

    interface TaskDetailsListener {
        void deleteTask(Task task);
    }
}