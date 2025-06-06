//Assignment 03
//Assignment03.zip
//Alex Ilevbare

package com.example.assignment03;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText editTextNumberDecimalListPrice;
    TextView textViewCustomPercent, textViewDiscountAmount, textViewFinalAmount;
    SeekBar seekBarCustomPercent;
    RadioGroup radioGroupSalePercent;



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
        editTextNumberDecimalListPrice = findViewById(R.id.editTextNumberDecimalListPrice);
        textViewCustomPercent = findViewById(R.id.textViewCustomPercent);
        textViewDiscountAmount = findViewById(R.id.textViewDiscountAmount);
        textViewFinalAmount = findViewById(R.id.textViewFinalAmount);
        seekBarCustomPercent = findViewById(R.id.seekBarCustomPercent);
        radioGroupSalePercent = findViewById(R.id.radioGroupSalePercent);

        findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextNumberDecimalListPrice.setText("");
                seekBarCustomPercent.setProgress(25);
                textViewDiscountAmount.setText("0.00");
                textViewFinalAmount.setText("0.00");
                radioGroupSalePercent.check(R.id.radioButtonTen);
            }
        });
        seekBarCustomPercent.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewCustomPercent.setText(String.valueOf(progress) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        findViewById(R.id.buttonCalculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price = editTextNumberDecimalListPrice.getText().toString().trim();

                try {
                    double itemPrice = Double.parseDouble(price);
                    double discountPercent = getDiscount();
                    double discountAmount = (itemPrice * discountPercent) / 100;
                    double finalAmount = itemPrice - discountAmount;

                    textViewDiscountAmount.setText(String.format("%.2f", discountAmount));
                    textViewFinalAmount.setText(String.format("%.2f", finalAmount));
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Enter Item Price", Toast.LENGTH_SHORT).show();
                    textViewDiscountAmount.setText("0.00");
                    textViewFinalAmount.setText("0.00");
                }

            }

        });

    }
    private double getDiscount() {
        int selectedRadioButton = radioGroupSalePercent.getCheckedRadioButtonId();

        if (selectedRadioButton == R.id.radioButtonTen) {
            return 10.0;
        }
        if (selectedRadioButton == R.id.radioButtonFifteen) {
            return 15.0;
        }
        if (selectedRadioButton == R.id.radioButtonEighteen) {
            return 18.0;
        }
        if (selectedRadioButton == R.id.radioButtonCustom) {
            return seekBarCustomPercent.getProgress();
        }

        return 10.0;
    }


}