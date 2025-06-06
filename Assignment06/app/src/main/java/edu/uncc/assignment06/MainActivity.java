//Assignment 06
//MainActivity.java
//Alex Ilevbare

package edu.uncc.assignment06;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements TasksFragment.TasksFragmentListener, CreateTaskFragment.CreateTaskFragmentListener, SelectTaskDateFragment.SelectTaskDateFragmentListener {

    ArrayList<Task> tasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main, new TasksFragment())
                    .commit();
        }
    }

    @Override
    public void openCreateTaskFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new CreateTaskFragment(), "CREATE_TASK_FRAGMENT")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void openSelectTaskDateFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectTaskDateFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void addTask(Task task) {
        tasks.add(task);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    @Override
    public void setSelectedDate(Date date) {
        CreateTaskFragment fragment = (CreateTaskFragment) getSupportFragmentManager().findFragmentByTag("CREATE_TASK_FRAGMENT");
        if (fragment != null) {
            fragment.setSelectedDate(date);
        }
    }
}