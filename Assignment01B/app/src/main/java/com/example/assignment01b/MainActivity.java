//Assignment 01B
//Assignment01B.zip
//Alex Ilevbare

package com.example.assignment01b;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
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

    RadioGroup radioGroupConversion;


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
        radioGroupConversion = findViewById(R.id.radioGroupConversion);

        findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextTemperatureHint.setText("");
                textViewConversionAmount.setText("N/A");
                radioGroupConversion.check(R.id.radioButtonCtoF);
            }
        });

        findViewById(R.id.buttonCalculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredTemperature = editTextTemperatureHint.getText().toString();

                try {
                    double temperature = Double.valueOf(enteredTemperature);

                    if (radioGroupConversion.getCheckedRadioButtonId() == R.id.radioButtonCtoF) {
                        double temperatureConversion = (temperature * 9/5) + 32;
                        textViewConversionAmount.setText(String.format("%.2f F", temperatureConversion));
                    } else {
                        double temperatureConversion = (temperature - 32) * 5/9;
                        textViewConversionAmount.setText(String.format("%.2f C", temperatureConversion));
                    }

                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Enter a Valid Number!", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }
}