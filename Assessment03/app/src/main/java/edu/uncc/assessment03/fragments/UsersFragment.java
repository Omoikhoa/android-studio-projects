package edu.uncc.assessment03.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.stream.Collectors;

import edu.uncc.assessment03.R;
import edu.uncc.assessment03.databinding.FragmentUsersBinding;
import edu.uncc.assessment03.databinding.SelectFilterRowBinding;
import edu.uncc.assessment03.models.CreditCategory;
import edu.uncc.assessment03.models.User;

public class UsersFragment extends Fragment {
    public UsersFragment() {
        // Required empty public constructor
    }

    FragmentUsersBinding binding;
    ArrayList<User> mUsers = new ArrayList<>();
    public UsersAdapter adapter;

    public void notifyAdapterDataSetChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
    String selectedSort;
    String sortDirection = "ASC";
    CreditCategory selectedFilterCategory;

    public void setSelectedFilterCategory(CreditCategory selectedFilterCategory) {
        this.selectedFilterCategory = selectedFilterCategory;
    }

    private void applyFilter() {
        if (selectedFilterCategory != null) {
            int minCreditScore = getMinCreditScore(selectedFilterCategory.getName());
            ArrayList<User> filteredUsers = mUsers.stream()
                    .filter(user -> user.getCreditScore() >= minCreditScore)
                    .collect(Collectors.toCollection(ArrayList::new));
            adapter.updateUsers(filteredUsers);
        } else {
            adapter.updateUsers(mUsers);
        }
    }

    private int getMinCreditScore(String categoryName) {
        switch (categoryName) {
            case "Excellent":
                return 800;
            case "Very Good":
                return 740;
            case "Good":
                return 670;
            case "Fair":
                return 580;
            case "Poor":
                return 300;
            default:
                return 0;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUsersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Users");

        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.add_new_user_action){
                    mListener.gotoAddUser();
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        mUsers = mListener.getAllUsers();


        binding.imageViewSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoSelectSort();
            }
        });

        binding.imageViewFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoSelectFilter();
            }
        });

        binding.imageViewSortAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortDirection = "ASC";
                applySort();

            }
        });

        binding.imageViewSortDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortDirection = "DESC";
                applySort();
            }
        });

        if(selectedFilterCategory == null){
            binding.textViewFilter.setText("N/A");
        } else {
            binding.textViewFilter.setText(selectedFilterCategory.getName() + " or Higher" );
        }

        if(selectedSort == null){
            selectedSort = "Name";
            sortDirection = "ASC";
        }
        binding.textViewSort.setText(selectedSort + " (" + sortDirection + ")");


        adapter = new UsersAdapter(mUsers);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        applyFilter();


    }

    class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
        ArrayList<User> users;

        UsersAdapter(ArrayList<User> users) {
            this.users = users;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            SelectFilterRowBinding binding = SelectFilterRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            User user = users.get(position);
            holder.setupUI(user);

        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        public void updateUsers(ArrayList<User> newUsers) {
            this.users = newUsers;
            notifyDataSetChanged();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            SelectFilterRowBinding mBinding;
            User mUser;
            public ViewHolder (SelectFilterRowBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            public void setupUI(User user) {
                mBinding.textViewName.setText(user.getName());
                mBinding.textViewAge.setText(String.valueOf(user.getAge()));
                mBinding.textViewCreditScore.setText(String.valueOf(user.getCreditScore()));
                mBinding.textViewLocation.setText(user.getState().getName());
                mBinding.imageView.setImageResource(getCreditCategoryImage(user.getCreditScore()));

                mUser = user;

                mBinding.imageView6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mUsers.remove(mUser);
                        if (mUsers.size() == 0) {
                            mListener.deleteUser(mUser);
                        } else {
                            notifyDataSetChanged();
                        }
                    }
                });
            }
            int getCreditCategoryImage(int creditScore) {
                if (creditScore >= 800) return R.drawable.excellent;
                else if (creditScore >= 740) return R.drawable.very_good;
                else if (creditScore >= 670) return R.drawable.good;
                else if (creditScore >= 580) return R.drawable.fair;
                else return R.drawable.poor;
            }
        }
    }

    UsersListener mListener;


    public void setSelectedSort(String selectedSort) {

        this.selectedSort = selectedSort;

        this.sortDirection = "ASC"; // Default to ascending
        applySort();
    }

    private void applySort() {
        if (selectedSort != null) {
            ArrayList<User> sortedUsers = new ArrayList<>(mUsers);
            sortedUsers.sort((u1, u2) -> {
                int result = 0;
                switch (selectedSort) {
                    case "Name":
                        result = u1.getName().compareTo(u2.getName());
                        break;
                    case "Age":
                        result = Integer.compare(u1.getAge(), u2.getAge());
                        break;
                    case "Credit Score":
                        result = Integer.compare(u1.getCreditScore(), u2.getCreditScore());
                        break;
                    case "State":
                        result = u1.getState().getName().compareTo(u2.getState().getName());
                        break;
                }
                return sortDirection.equals("ASC") ? result : -result;
            });
            adapter.updateUsers(sortedUsers);
            binding.textViewSort.setText(selectedSort + " (" + sortDirection + ")");
        }
    }

//    public void setSelectedFilterCategory(CreditCategory selectedFilterCategory) {
//        this.selectedFilterCategory = selectedFilterCategory;
//    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof UsersListener) {
            mListener = (UsersListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement UsersListener");
        }
    }

    public interface UsersListener {
        void gotoAddUser();
        void gotoSelectFilter();
        void gotoSelectSort();
        ArrayList<User> getAllUsers();
        void deleteUser(User user);
    }
}