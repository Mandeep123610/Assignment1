package com.example.assignment1;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    private final List<String> values = new ArrayList<>();
    private final List<String> history = new ArrayList<>();
    private boolean isAdvanceMode = false;

    public void push(String value) {
        values.add(value);
    }

    public int calculate() {
        if (values.isEmpty()) return 0;

        int result;
        try {
            result = Integer.parseInt(values.get(0));
        } catch (Exception e) {
            return 0;
        }

        for (int i = 1; i < values.size(); i += 2) {
            if (i + 1 >= values.size()) break;

            String op = values.get(i);
            int operand;
            try {
                operand = Integer.parseInt(values.get(i + 1));
            } catch (Exception e) {
                operand = 0;
            }

            switch (op) {
                case "+": result += operand; break;
                case "-": result -= operand; break;
                case "*": result *= operand; break;
                case "/": if (operand != 0) result /= operand; break;
            }
        }

        saveToHistory(result);
        return result;
    }

    private void saveToHistory(int result) {
        StringBuilder sb = new StringBuilder();
        for (String v : values) sb.append(v).append(" ");
        sb.append("= ").append(result);
        history.add(sb.toString());
    }

    public void clear() {
        values.clear();
    }

    public void toggleMode() {
        isAdvanceMode = !isAdvanceMode;
    }

    public boolean isAdvanceMode() {
        return isAdvanceMode;
    }

    public String getHistory() {
        StringBuilder sb = new StringBuilder();
        for (String entry : history) sb.append(entry).append("\n");
        return sb.toString();
    }
}
