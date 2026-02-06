package com.example.assignment1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {

    private Calculator calculator;

    private TextView tvResult, tvHistory;
    private View historyScroll;
    private Button btnAdvanceMode;

    private String displayText = "0";     // what user sees
    private String currentNumber = "";    // builds multi-digit numbers like 20, 105

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Prevent dim UI in dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculator = new Calculator();

        tvResult = findViewById(R.id.tvResult);
        tvHistory = findViewById(R.id.tvHistory);
        historyScroll = findViewById(R.id.historyScroll);
        btnAdvanceMode = findViewById(R.id.btnAdvanceMode);

        tvResult.setText("0");
        historyScroll.setVisibility(View.GONE);
        btnAdvanceMode.setText("Advance - With History");

        setupNumberButtons();
        setupOperatorButtons();
        setupClearButton();
        setupEqualsButton();
        setupAdvanceModeButton();
    }

    private void setupNumberButtons() {
        int[] ids = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };

        View.OnClickListener listener = v -> {
            String digit = ((Button) v).getText().toString();

            // Build current number (multi-digit)
            currentNumber += digit;

            // Update display: if it was "0", replace it
            if (displayText.equals("0")) displayText = digit;
            else displayText += digit;

            tvResult.setText(displayText);
        };

        for (int id : ids) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setupOperatorButtons() {
        int[] ids = {R.id.btnPlus, R.id.btnMinus, R.id.btnMultiply, R.id.btnDivide};

        View.OnClickListener listener = v -> {
            String op = ((Button) v).getText().toString();

            //  push the number typed so far
            if (!currentNumber.isEmpty()) {
                calculator.push(currentNumber);
                currentNumber = "";
            } else {
                // If user presses operator first, ignore
                if (displayText.equals("0")) return;
            }

            //  push operator
            calculator.push(op);

            // Update display with spaces around operator
            displayText += " " + op + " ";
            tvResult.setText(displayText);
        };

        for (int id : ids) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setupClearButton() {
        findViewById(R.id.btnClear).setOnClickListener(v -> {
            calculator.clear();
            displayText = "0";
            currentNumber = "";
            tvResult.setText(displayText);
        });
    }

    private void setupEqualsButton() {
        findViewById(R.id.btnEquals).setOnClickListener(v -> {

            //  push last number before calculating
            if (!currentNumber.isEmpty()) {
                calculator.push(currentNumber);
                currentNumber = "";
            }

            // If nothing valid to calculate, do nothing
            if (displayText.equals("0")) return;

            String expression = displayText.trim();
            int result = calculator.calculate();

            displayText = expression + " = " + result;
            tvResult.setText(displayText);

            if (calculator.isAdvanceMode()) {
                historyScroll.setVisibility(View.VISIBLE);
                tvHistory.setText(calculator.getHistory());
            }

            calculator.clear();
        });
    }

    private void setupAdvanceModeButton() {
        btnAdvanceMode.setOnClickListener(v -> {
            calculator.toggleMode();

            if (calculator.isAdvanceMode()) {
                btnAdvanceMode.setText("Standard - No History");
                historyScroll.setVisibility(View.VISIBLE);
                tvHistory.setText(calculator.getHistory());
            } else {
                btnAdvanceMode.setText("Advance - With History");
                historyScroll.setVisibility(View.GONE);
            }
        });
    }
}
