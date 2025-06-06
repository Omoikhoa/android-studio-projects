//Assignment 01a
//Assignment01a.zip
//Alex Ilevbare

package com.example.assignment1a;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText editTextTemperatureHint;
    TextView textViewConversionAmount;

    public static final String TAG = "demo";

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

        editTextTemperatureHint = findViewById(R.id.editTextTemperatureHint);
        textViewConversionAmount = findViewById(R.id.textViewConversionAmount);

        findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextTemperatureHint.setText("");
                textViewConversionAmount.setText("");
            }
        });

        findViewById(R.id.buttonCtoF).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredTemperature = editTextTemperatureHint.getText().toString();

                try {
                    double temperature = Double.valueOf(enteredTemperature);
                    double temperatureConversion = (temperature * 9/5) + 32;
                    textViewConversionAmount.setText(String.format("%.2f F", temperatureConversion));
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Enter a Valid Number!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        findViewById(R.id.buttonFtoC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredTemperature = editTextTemperatureHint.getText().toString();
                try {
                    double temperature = Double.valueOf(enteredTemperature);
                    double temperatureConversion = (temperature - 32) * 5/9;
                    textViewConversionAmount.setText(String.format("%.2f C", temperatureConversion));
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Enter a Valid Number!", Toast.LENGTH_SHORT).show();


                }

            }
        });



    }
}