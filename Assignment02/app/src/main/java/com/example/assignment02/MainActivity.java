//Assignment 02
//Assignment02.zip
//Alex Ilevbare

package com.example.assignment02;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView textViewColorHex, textViewColorRgb, textViewRed, textViewGreen, textViewBlue;
    SeekBar seekBarRed, seekBarGreen, seekBarBlue;
    View viewColor;

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

        textViewColorHex = findViewById(R.id.textViewColorHex);
        textViewColorRgb = findViewById(R.id.textViewColorRgb);
        textViewRed = findViewById(R.id.textViewRed);
        textViewGreen = findViewById(R.id.textViewGreen);
        textViewBlue = findViewById(R.id.textViewBlue);
        seekBarRed = findViewById(R.id.seekBarRed);
        seekBarGreen = findViewById(R.id.seekBarGreen);
        seekBarBlue = findViewById(R.id.seekBarBlue);
        viewColor = findViewById(R.id.viewColor);

        findViewById(R.id.viewColor).setBackgroundColor(Color.rgb(64, 128, 0));

        seekBarRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewRed.setText(String.valueOf(progress));
                updateColorDisplay();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewGreen.setText(String.valueOf(progress));
                updateColorDisplay();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewBlue.setText(String.valueOf(progress));
                updateColorDisplay();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor(64, 128, 0);
            }
        });

        findViewById(R.id.buttonWhite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor(255,255, 255);
            }
        });

        findViewById(R.id.buttonBlack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor(0,0, 0);
            }
        });

        findViewById(R.id.buttonBlue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor(0,0, 255);
            }
        });

    }

    private void updateColorDisplay() {
        int red = seekBarRed.getProgress();
        int green = seekBarGreen.getProgress();
        int blue = seekBarBlue.getProgress();

        viewColor.setBackgroundColor(Color.rgb(red, green, blue));
        textViewColorRgb.setText(String.format("Color RGB: (%d, %d, %d)", red, green, blue));
        textViewColorHex.setText(String.format("Color HEX: #%02X%02X%02X", red, green, blue));
    }

    private void setColor(int red, int green, int blue) {
        seekBarRed.setProgress(red);
        seekBarGreen.setProgress(green);
        seekBarBlue.setProgress(blue);

        textViewRed.setText(String.valueOf(red));
        textViewGreen.setText(String.valueOf(green));
        textViewBlue.setText(String.valueOf(blue));

        updateColorDisplay();
    }
}