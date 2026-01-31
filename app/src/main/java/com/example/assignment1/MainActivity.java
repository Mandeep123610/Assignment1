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

    private String currentDisplay = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // ✅ stop “dim/invisible” issue in dark mode
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

        View.OnClickListener l = v -> {
            String number = ((Button) v).getText().toString();
            calculator.push(number);
            updateDisplay(number);
        };

        for (int id : ids) findViewById(id).setOnClickListener(l);
    }

    private void setupOperatorButtons() {
        int[] ids = {R.id.btnPlus, R.id.btnMinus, R.id.btnMultiply, R.id.btnDivide};

        View.OnClickListener l = v -> {
            String op = ((Button) v).getText().toString();
            calculator.push(op);
            updateDisplay(op);
        };

        for (int id : ids) findViewById(id).setOnClickListener(l);
    }

    private void setupClearButton() {
        findViewById(R.id.btnClear).setOnClickListener(v -> {
            calculator.clear();
            currentDisplay = "0";
            tvResult.setText(currentDisplay);
        });
    }

    private void setupEqualsButton() {
        findViewById(R.id.btnEquals).setOnClickListener(v -> {

            // ✅ Save expression before calculate
            String expression = currentDisplay;

            int result = calculator.calculate();

            // ✅ Show full equation on screen
            currentDisplay = expression + " = " + result;
            tvResult.setText(currentDisplay);

            // ✅ Show history only in advance mode (but history is always saved)
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

    private void updateDisplay(String value) {
        if (currentDisplay.equals("0")) currentDisplay = value;
        else currentDisplay += " " + value;

        tvResult.setText(currentDisplay);
    }
}
