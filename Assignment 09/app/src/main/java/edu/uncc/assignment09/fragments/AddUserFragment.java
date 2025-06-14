// Assignment 09
// File Name: AddUserFragment.java
// Full Name: Alex Ilevbare

package edu.uncc.assignment09.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.uncc.assignment09.R;
import edu.uncc.assignment09.databinding.FragmentAddUserBinding;
import edu.uncc.assignment09.models.AppDatabase;
import edu.uncc.assignment09.models.State;
import edu.uncc.assignment09.models.User;

public class AddUserFragment extends Fragment {
    public AddUserFragment() {
        // Required empty public constructor
    }

    FragmentAddUserBinding binding;
    State selectedState;

    public void setSelectedState(State selectedState) {
        this.selectedState = selectedState;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add User");
        binding.buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoSelectState();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editTextName.getText().toString();
                String ageStr = binding.editTextAge.getText().toString();
                String creditScoreStr = binding.editTextCreditScore.getText().toString();
                if(name.isEmpty()){
                    Toast.makeText(getActivity(), "Enter Name!!", Toast.LENGTH_SHORT).show();
                } else {
                    try{
                        int age = Integer.parseInt(ageStr);
                        if(age < 0 || age > 150){
                            Toast.makeText(getActivity(), "Age must be between 0 and 150!!", Toast.LENGTH_SHORT).show();
                        } else {
                            try{
                                int creditScore = Integer.parseInt(creditScoreStr);
                                if(creditScore < 300 || creditScore > 850){
                                    Toast.makeText(getActivity(), "Credit Score must be between 300 and 850!!", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (selectedState == null){
                                        Toast.makeText(getActivity(), "Select State!!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        User user = new User(name, age, creditScore, selectedState.getStateSummary());
                                        mListener.sendBackNewUser(user);
                                    }
                                }
                            } catch (NumberFormatException ex){
                                Toast.makeText(getActivity(), "Enter credit score!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (NumberFormatException ex) {
                        Toast.makeText(getActivity(), "Enter valid Age!!", Toast.LENGTH_SHORT).show();
                    }

                    AppDatabase db = Room.databaseBuilder(getActivity(), AppDatabase.class, "users-db")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();

                    int age = Integer.parseInt(ageStr);
                    int creditScore = Integer.parseInt(creditScoreStr);

                    User user = new User(name, age, creditScore, selectedState.getStateSummary());
                    db.userDao().insertAll(user);
                    mListener.cancelAddOrSelection();
                }
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancelAddOrSelection();
            }
        });

        if(selectedState == null){
            binding.textViewState.setText("N/A");
        } else {
            binding.textViewState.setText(selectedState.getName());
        }
    }

    AddUserListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddUserListener) {
            mListener = (AddUserListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AddUserListener");
        }
    }

    public interface AddUserListener{
        void sendBackNewUser(User user);
        void cancelAddOrSelection();
        void gotoSelectState();
    }
}