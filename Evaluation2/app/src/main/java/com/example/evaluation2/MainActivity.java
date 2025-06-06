package com.example.caloriecalculator;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements 
        CalorieCalculatorFragment.CalorieCalculatorListener, 
        SelectFragment.SelectListener, ActivityLevelFragment.ActivityLevelListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            replaceFragment(new CalorieCalculatorFragment(), false);
        }
    }

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Override
    public void onSelect(String value, String type) {
        CalorieCalculatorFragment fragment = (CalorieCalculatorFragment) getSupportFragmentManager()
                .findFragmentByTag("CALORIE_CALCULATOR");
        if (fragment != null) {
            fragment.updateValue(type, value);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onActivityLevelSelected(String activityLevel) {
        CalorieCalculatorFragment fragment = (CalorieCalculatorFragment) getSupportFragmentManager()
                .findFragmentByTag("CALORIE_CALCULATOR");
        if (fragment != null) {
            fragment.updateValue("Activity Level", activityLevel);
        }
        getSupportFragmentManager().popBackStack();
    }
}