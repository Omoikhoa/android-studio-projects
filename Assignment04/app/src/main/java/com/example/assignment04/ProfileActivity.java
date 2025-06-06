//Assignment 04
//Assignment04.zip
//Alex Ilevbare

package com.example.assignment04;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity {
    TextView textViewName, textViewEmail, textViewRole;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textViewName = findViewById(R.id.textViewName);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewRole = findViewById(R.id.textViewRole);

        user = (User) getIntent().getSerializableExtra("user");

        if (user != null) {
            textViewName.setText("Name: " + user.getName());
            textViewEmail.setText("Email: " + user.getEmail());
            textViewRole.setText("Role: " + user.getRole());
        }

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                user = (User) result.getData().getSerializableExtra("updatedUser");
                textViewName.setText("Name: " + user.getName());
                textViewEmail.setText("Email: " + user.getEmail());
                textViewRole.setText("Role: " + user.getRole());
            }
        });


        findViewById(R.id.buttonUpdate).setOnClickListener(v -> {
            Intent intent = new Intent(this, EditUserActivity.class);
            intent.putExtra("user", user);
            launcher.launch(intent);
        });
    }
}