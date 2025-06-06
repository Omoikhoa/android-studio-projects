//Assignment 05
//Assignment05.zip
//Alex Ilevbare

package com.example.assignment05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements FragmentNavigationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            loadFragment(new WelcomeFragment(), false, "welcomeFragment");
        }
    }

    public void loadFragment(Fragment fragment, boolean addToBackStack, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, tag);

        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }

        transaction.commit();
    }

    @Override
    public void onStartClicked() {
        loadFragment(new CreateUserFragment(), true, "createUserFragment");
    }

    @Override
    public void onUserCreated(User user) {
        ProfileFragment profileFragment = ProfileFragment.newInstance(user);
        loadFragment(profileFragment, true, "profileFragment");
    }

    @Override
    public void onUpdateClicked(User user) {
        EditUserFragment editUserFragment = EditUserFragment.newInstance(user);
        loadFragment(editUserFragment, true, "editUserFragment");
    }

    @Override
    public void onUserUpdated(User user) {
        getSupportFragmentManager().popBackStack();
        ProfileFragment profileFragment = (ProfileFragment) getSupportFragmentManager().findFragmentByTag("ProfileFragment");

        if (profileFragment != null) {
            profileFragment.updateUser(user);
        } else {
            // Reload the profile fragment if null
            loadFragment(ProfileFragment.newInstance(user), false, "ProfileFragment");
        }
    }

}
