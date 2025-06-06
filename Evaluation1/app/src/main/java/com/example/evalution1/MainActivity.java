//Evalution 1
//Evalution1.zip
//Alex Ilevbare

package com.example.evalution1;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText editTextNumberDecimalEnterWeight;
    TextView textViewBeerAmount, textViewWineAmount, textViewLiquorAmount, textViewMaltAmount, textViewBacLevel, textViewSafety;
    View viewColor;
    RadioGroup radioGroupGender;
    Button buttonCalculate, buttonReset;

    private int beerAmount = 0;
    private int wineAmount = 0;
    private int liquorAmount = 0;
    private int maltAmount = 0;

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

        editTextNumberDecimalEnterWeight = findViewById(R.id.editTextNumberDecimalEnterWeight);
        textViewBeerAmount = findViewById(R.id.textViewBeerAmount);
        textViewWineAmount = findViewById(R.id.textViewWineAmount);
        textViewLiquorAmount = findViewById(R.id.textViewLiquorAmount);
        textViewMaltAmount = findViewById(R.id.textViewMaltAmount);
        textViewBacLevel = findViewById(R.id.textViewBacLevel);
        textViewSafety = findViewById(R.id.textViewSafety);
        viewColor = findViewById(R.id.viewColor);
        radioGroupGender = findViewById(R.id.RadioGroupGender);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonReset = findViewById(R.id.buttonReset);

        Button buttonBeerPlus = findViewById(R.id.buttonBeerPlus);
        Button buttonBeerMinus = findViewById(R.id.buttonBeerMinus);
        Button buttonWinePlus = findViewById(R.id.buttonWinePlus);
        Button buttonWineMinus = findViewById(R.id.buttonWineMinus);
        Button buttonLiquorPlus = findViewById(R.id.buttonLiquorPlus);
        Button buttonLiquorMinus = findViewById(R.id.buttonLiquorMinus);
        Button buttonMaltPlus = findViewById(R.id.buttonMaltPlus);
        Button buttonMaltMinus = findViewById(R.id.buttonMaltMinus);

        buttonBeerPlus.setOnClickListener(v -> updateAmount(textViewBeerAmount, true, "beer"));
        buttonBeerMinus.setOnClickListener(v -> updateAmount(textViewBeerAmount, false, "beer"));
        buttonWinePlus.setOnClickListener(v -> updateAmount(textViewWineAmount, true, "wine"));
        buttonWineMinus.setOnClickListener(v -> updateAmount(textViewWineAmount, false, "wine"));
        buttonLiquorPlus.setOnClickListener(v -> updateAmount(textViewLiquorAmount, true, "liquor"));
        buttonLiquorMinus.setOnClickListener(v -> updateAmount(textViewLiquorAmount, false, "liquor"));
        buttonMaltPlus.setOnClickListener(v -> updateAmount(textViewMaltAmount, true, "malt"));
        buttonMaltMinus.setOnClickListener(v -> updateAmount(textViewMaltAmount, false, "malt"));

        buttonCalculate.setOnClickListener(v -> calculateBAC());
        buttonReset.setOnClickListener(v -> resetUI());
    }

    private void updateAmount(TextView textView, boolean isIncrement, String type) {
        switch (type) {
            case "beer":
                beerAmount = calculateNewAmount(beerAmount, isIncrement);
                textView.setText(String.valueOf(beerAmount));
                break;
            case "wine":
                wineAmount = calculateNewAmount(wineAmount, isIncrement);
                textView.setText(String.valueOf(wineAmount));
                break;
            case "liquor":
                liquorAmount = calculateNewAmount(liquorAmount, isIncrement);
                textView.setText(String.valueOf(liquorAmount));
                break;
            case "malt":
                maltAmount = calculateNewAmount(maltAmount, isIncrement);
                textView.setText(String.valueOf(maltAmount));
                break;
        }
    }

    private int calculateNewAmount(int currentAmount, boolean isIncrement) {
        int newAmount = currentAmount;
        if (isIncrement) {
            newAmount++;
        } else {
            newAmount--;
        }
        if (newAmount < 0) {
            newAmount = 0;
        } else if (newAmount > 10) {
            newAmount = 10;
        }
        return newAmount;
    }

    private void calculateBAC() {
        String weightStr = editTextNumberDecimalEnterWeight.getText().toString();
        if (TextUtils.isEmpty(weightStr)) {
            showErrorDialog("Please enter your weight.");
            return;
        }

        double weight;
        try {
            weight = Double.parseDouble(weightStr);
            if (weight <= 0) {
                showErrorDialog("Please enter a valid weight.");
                return;
            }
        } catch (NumberFormatException e) {
            showErrorDialog("Invalid weight format.");
            return;
        }

        if (beerAmount == 0 && wineAmount == 0 && liquorAmount == 0 && maltAmount == 0) {
            showErrorDialog("Please select at least one drink.");
            return;
        }

        int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
        if (selectedGenderId == -1) {
            showErrorDialog("Please select your gender.");
            return;
        }
        double r = (selectedGenderId == R.id.radioButtonMale) ? 0.73 : 0.66;

        double totalAlcoholOunces = (beerAmount * 12 * 0.05) + (wineAmount * 5 * 0.12) + (liquorAmount * 1.5 * 0.40) + (maltAmount * 9 * 0.07);
        double bac = (totalAlcoholOunces * 5.14) / (weight * r);

        textViewBacLevel.setText("Your BAC level: " + String.format("%.3f", bac) + "%");

        updateStatus(bac);
    }

    private void updateStatus(double bac) {
        if (bac <= 0.08) {
            textViewSafety.setText("You're Safe.");
            textViewSafety.setTextColor(Color.WHITE);
            viewColor.setBackgroundColor(getColor(R.color.safe_color));
        } else if (bac <= 0.2) {
            textViewSafety.setText("Be careful.");
            textViewSafety.setTextColor(Color.WHITE);
            viewColor.setBackgroundColor(getColor(R.color.becareful_color));
        } else {
            textViewSafety.setText("Over the limit!");
            textViewSafety.setTextColor(Color.WHITE);
            viewColor.setBackgroundColor(getColor(R.color.overlimit_color));
        }
    }

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    private void resetUI() {
        // Reset drink amounts
        beerAmount = 0;
        wineAmount = 0;
        liquorAmount = 0;
        maltAmount = 0;

        // Reset TextViews for drink amounts
        textViewBeerAmount.setText("0");
        textViewWineAmount.setText("0");
        textViewLiquorAmount.setText("0");
        textViewMaltAmount.setText("0");


        // Reset weight field
        editTextNumberDecimalEnterWeight.setText("");

        // Reset BAC level and status
        textViewBacLevel.setText("Your BAC level: 0.00%");
        textViewSafety.setText("You're Safe.");
        textViewSafety.setTextColor(Color.WHITE);
        viewColor.setBackgroundColor(getColor(R.color.safe_color));

        // Clear gender selection
        radioGroupGender.check(R.id.radioButtonFemale);

    }
}