// Assignment 12
// ToDoListsFragment.java
// Alex Ilevbare

package edu.uncc.assignment11.fragments.todo;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.uncc.assignment11.R;
import edu.uncc.assignment11.databinding.FragmentToDoListsBinding;
import edu.uncc.assignment11.databinding.ListItemTodoListBinding;
import edu.uncc.assignment11.models.ToDoList;

public class ToDoListsFragment extends Fragment {
    public ToDoListsFragment() {
        // Required empty public constructor
    }

    FragmentToDoListsBinding binding;
    ArrayList<ToDoList> mToDoLists = new ArrayList<>();
    ToDoListAdapter adapter;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private ListenerRegistration snapshotListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentToDoListsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("ToDo Lists");

        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.todo_lists_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.add_new_todo_list_action){
                    mListener.gotoCreateNewToDoList();
                    return true;
                } else if(menuItem.getItemId() == R.id.logout_action){
                    mListener.logout();
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
        adapter = new ToDoListAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);


        getAllToDoListsForUser();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (snapshotListener != null) {
            snapshotListener.remove();
            snapshotListener = null;
        }
    }

    private void getAllToDoListsForUser() {
        //TODO: reload the todo lists for the currently logged in user
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        snapshotListener = db.collection("toDoLists")
                .whereEqualTo("userId", mAuth.getCurrentUser().getUid())
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.d(TAG, "Error listening to changes: ", error);
                        return;
                    }

                    if (value != null) {
                        mToDoLists.clear();
                        for (QueryDocumentSnapshot document : value) {
                            ToDoList toDoList = document.toObject(ToDoList.class);
                            toDoList.setTodoListId(document.getId());
                            mToDoLists.add(toDoList);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void deleteToDoList(ToDoList toDoList) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete To-Do List")
                .setMessage("Are you sure you want to delete this to-do list and all its items?")
                .setPositiveButton("OK", (dialog, which) -> {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    // Query to find all items with the same todoListId
                    db.collection("toDoListDetails")
                            .whereEqualTo("todoListId", toDoList.getTodoListId())
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful() && task.getResult() != null) {
                                    // Start a batch operation
                                    db.runBatch(batch -> {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            batch.delete(document.getReference());
                                        }
                                    }).addOnCompleteListener(batchTask -> {
                                        if (batchTask.isSuccessful()) {
                                            // Delete the ToDoList after deleting its items
                                            db.collection("toDoLists")
                                                    .document(toDoList.getTodoListId())
                                                    .delete()
                                                    .addOnCompleteListener(deleteTask -> {
                                                        if (deleteTask.isSuccessful()) {
                                                            mToDoLists.remove(toDoList);
                                                            adapter.notifyDataSetChanged();
                                                            Toast.makeText(getActivity(), "To-Do List and its items deleted", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Log.d(TAG, "Error deleting ToDoList: ", deleteTask.getException());
                                                            Toast.makeText(getActivity(), "Error deleting list", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        } else {
                                            Log.d(TAG, "Error deleting ToDoListItems: ", batchTask.getException());
                                            Toast.makeText(getActivity(), "Error deleting list items", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    Log.d(TAG, "Error querying ToDoListItems: ", task.getException());
                                    Toast.makeText(getActivity(), "Error finding list items", Toast.LENGTH_SHORT).show();
                                }
                            });
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }


    class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ToDoListViewHolder>{

        @NonNull
        @Override
        public ToDoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ListItemTodoListBinding itemBinding = ListItemTodoListBinding.inflate(getLayoutInflater(), parent, false);
            return new ToDoListViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ToDoListViewHolder holder, int position) {
            ToDoList toDoList = mToDoLists.get(position);
            holder.bind(toDoList);
        }

        @Override
        public int getItemCount() {
            return mToDoLists.size();
        }

        class ToDoListViewHolder extends RecyclerView.ViewHolder{
            ListItemTodoListBinding itemBinding;
            ToDoList mToDoList;
            public ToDoListViewHolder(ListItemTodoListBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }

            public void bind(ToDoList toDoList) {
                mToDoList = toDoList;
                itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.gotoToDoListDetails(toDoList);
                    }
                });

                if(mAuth.getCurrentUser().getUid().equals(toDoList.getUserId())){
                    itemBinding.imageViewDelete.setVisibility(View.VISIBLE);
                    itemBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteToDoList(mToDoList);
                        }
                    });
                } else {
                    itemBinding.imageViewDelete.setVisibility(View.GONE);
                }



                itemBinding.textViewName.setText(toDoList.getName());
            }
        }
    }

    ToDoListsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ToDoListsListener) {
            mListener = (ToDoListsListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ToDoListsListener");
        }
    }

    public interface ToDoListsListener {
        void gotoCreateNewToDoList();
        void gotoToDoListDetails(ToDoList toDoList);
        void logout();
    }
}

