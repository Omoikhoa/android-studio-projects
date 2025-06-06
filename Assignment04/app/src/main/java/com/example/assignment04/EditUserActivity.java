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

public class EditUserActivity extends AppCompatActivity {
    EditText editTextTextName, editTextTextEmail;
    RadioGroup radioGroupRole;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextTextName = findViewById(R.id.editTextTextName);
        editTextTextEmail = findViewById(R.id.editTextTextEmail);
        radioGroupRole = findViewById(R.id.RadioGroupRole);

        user = (User)  getIntent().getSerializableExtra("user");
        if (user != null) {
            editTextTextName.setText(user.getName());
            editTextTextEmail.setText(user.getEmail());

            for (int i = 0; i < radioGroupRole.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) radioGroupRole.getChildAt(i);
                if (radioButton.getText().toString().equals(user.getRole())) {
                    radioButton.setChecked(true);
                    break;
                }
            }
        }

        findViewById(R.id.buttonSubmit).setOnClickListener(v -> {
            String name = editTextTextName.getText().toString();
            String email = editTextTextEmail.getText().toString();
            int selectedRoleId = radioGroupRole.getCheckedRadioButtonId();

            if (name.isEmpty() || email.isEmpty() || selectedRoleId == -1) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedRadioButton = findViewById(selectedRoleId);
            String role = selectedRadioButton.getText().toString();

            user.setName(name);
            user.setEmail(email);
            user.setRole(role);
            Intent intent = new Intent();
            intent.putExtra("updatedUser", user);
            setResult(RESULT_OK, intent);
            finish();
        });

        findViewById(R.id.buttonCancel).setOnClickListener(v -> {
            finish();
        });
    }
}