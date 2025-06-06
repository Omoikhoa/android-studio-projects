package edu.uncc.assignment06;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.text.SimpleDateFormat;
import java.util.Locale;

import edu.uncc.assignment06.databinding.FragmentTasksBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TasksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TasksFragment extends Fragment {
    FragmentTasksBinding binding;
    private ArrayList<Task> tasks;
    private int currentTaskIndex = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TasksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TasksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TasksFragment newInstance(String param1, String param2) {
        TasksFragment fragment = new TasksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTasksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Tasks");

        tasks = listener.getTasks();
        sortTasksByDateDescending();
        updateTaskCount();
        displayTask();

        binding.buttonCreateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.openCreateTaskFragment();
            }
        });

        binding.imageViewNext.setOnClickListener(v -> {
            if (!tasks.isEmpty()) {
                currentTaskIndex = (currentTaskIndex + 1) % tasks.size();
                displayTask();
            }
        });

        binding.imageViewPrevious.setOnClickListener(v -> {
            if (!tasks.isEmpty()) {
                currentTaskIndex = (currentTaskIndex - 1 + tasks.size()) % tasks.size();
                displayTask();
            }
        });

        binding.imageViewDelete.setOnClickListener(v -> {
            if (!tasks.isEmpty()) {
                tasks.remove(currentTaskIndex);
                if (currentTaskIndex >= tasks.size()) {
                    currentTaskIndex = tasks.size() - 1;
                }
                updateTaskCount();
                displayTask();
            }
        });
    }

    TasksFragmentListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (TasksFragmentListener) context;
    }

    interface TasksFragmentListener {
        void openCreateTaskFragment();
        ArrayList<Task> getTasks();
    }

    private void sortTasksByDateDescending() {
        Collections.sort(tasks, (task1, task2) -> task2.date.compareTo(task1.date));
        currentTaskIndex = 0; // Set to the most recent task
    }

    private void updateTaskCount() {
        binding.textViewTasksCount.setText("You have " + tasks.size() + " tasks");
    }

    private void displayTask() {
        if (tasks.isEmpty()) {
            binding.cardViewTask.setVisibility(View.GONE);
        } else {
            binding.cardViewTask.setVisibility(View.VISIBLE);
            Task task = tasks.get(currentTaskIndex);
            binding.textViewTaskName.setText(task.name);
            
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
            binding.textViewTaskDate.setText(sdf.format(task.date));
            
            binding.textViewTaskPriority.setText(task.priority);
            binding.textViewTaskOutOf.setText("Task " + (currentTaskIndex + 1) + " of " + tasks.size());
        }
    }
}