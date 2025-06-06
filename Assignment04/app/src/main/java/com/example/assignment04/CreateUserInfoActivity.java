//Assignment 04
//Assignment04.zip
//Alex Ilevbare

package com.example.assignment04;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateUserInfoActivity extends AppCompatActivity {

    EditText editTextTextName, editTextTextEmail;
    RadioGroup radioGroupRole;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_user_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextTextName = findViewById(R.id.editTextTextName);
        editTextTextEmail = findViewById(R.id.editTextTextEmail);
        radioGroupRole = findViewById(R.id.RadioGroupRole);


        findViewById(R.id.buttonNext).setOnClickListener(v -> {
            String name = editTextTextName.getText().toString().trim();
            String email = editTextTextEmail.getText().toString().trim();
            int selectedId = radioGroupRole.getCheckedRadioButtonId();

            if (name.isEmpty() || email.isEmpty() || selectedId == -1) {
                Toast.makeText(CreateUserInfoActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton radioButton = findViewById(selectedId);
            String role = radioButton.getText().toString();

            User user = new User(name, email, role);

            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        });

    }
}