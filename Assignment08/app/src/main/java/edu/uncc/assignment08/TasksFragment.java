// Assignment 08
// TasksFragment.java
// Alex Ilevbare
package edu.uncc.assignment08;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.uncc.assignment08.databinding.FragmentTasksBinding;
import edu.uncc.assignment08.databinding.TaskRowItemBinding;
import edu.uncc.assignment08.models.SortSelection;
import edu.uncc.assignment08.models.Task;

public class TasksFragment extends Fragment {
    public TasksFragment() {
        // Required empty public constructor
    }

    FragmentTasksBinding binding;
    SortSelection sortSelection;

    public void setSortSelection(SortSelection sortSelection) {
        this.sortSelection = sortSelection;
    }

    ArrayList<Task> mTasks = new ArrayList<>();
    TaskAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTasksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Tasks");
        mTasks = mListener.getTasks();

        binding.buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.clearAllTasks();
                mTasks.clear();
                mAdapter.notifyDataSetChanged();
            }
        });

        binding.buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoCreateTask();
            }
        });

        binding.buttonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoSelectSort();
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new TaskAdapter();
        binding.recyclerView.setAdapter(mAdapter);
    }

    public void refreshTasks() {
        mTasks = mListener.getTasks();
        if (sortSelection != null) {
            // Sort the tasks based on the selected criteria
            if (sortSelection.getSortAttribute().equals("date")) {
                if (sortSelection.getSortOrder().equals("ASC")) {
                    Collections.sort(mTasks, Comparator.comparing(Task::getDate));
                } else {
                    Collections.sort(mTasks, Comparator.comparing(Task::getDate).reversed());
                }
            } else if (sortSelection.getSortAttribute().equals("name")) {
                if (sortSelection.getSortOrder().equals("ASC")) {
                    Collections.sort(mTasks, Comparator.comparing(Task::getName));
                } else {
                    Collections.sort(mTasks, Comparator.comparing(Task::getName).reversed());
                }
            } else if (sortSelection.getSortAttribute().equals("priority")) {
                if (sortSelection.getSortOrder().equals("ASC")) {
                    Collections.sort(mTasks, Comparator.comparing(Task::getPriority));
                } else {
                    Collections.sort(mTasks, Comparator.comparing(Task::getPriority).reversed());
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    TasksListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof TasksListener) {
            mListener = (TasksListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement TasksListener");
        }
    }

    interface TasksListener {
        void gotoCreateTask();
        void gotoSelectSort();
        void clearAllTasks();
        void gotoTaskDetails(Task task);
        ArrayList<Task> getTasks();
        void deleteTask(Task task);
    }

    class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
        @NonNull
        @Override
        public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TaskRowItemBinding binding = TaskRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new TaskViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.setupUI(task);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }

        class TaskViewHolder extends RecyclerView.ViewHolder {
            TaskRowItemBinding mBinding;
            Task mTask;

            public TaskViewHolder(TaskRowItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            public void setupUI(Task task) {
                mBinding.textViewTaskName.setText(task.getName());
                mBinding.textViewPriority.setText(task.getPriorityString());
                mBinding.textViewDate.setText(task.getDate().toString());

                mTask = task;

                mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.deleteTask(mTask);
                        mTasks.remove(mTask);
                        notifyDataSetChanged();
                    }
                });

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.gotoTaskDetails(mTask);
                    }
                });
            }
        }
    }
}