package com.example.assignment1;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

    private final List<String> tokens = new ArrayList<>();
    private final List<String> history = new ArrayList<>();
    private boolean advanceMode = false;

    public void push(String value) {
        tokens.add(value);
    }

    public int calculate() {
        if (tokens.isEmpty()) return 0;

        int result = parseInt(tokens.get(0));

        for (int i = 1; i < tokens.size(); i += 2) {
            if (i + 1 >= tokens.size()) break;

            String op = tokens.get(i);
            int operand = parseInt(tokens.get(i + 1));

            switch (op) {
                case "+": result += operand; break;
                case "-": result -= operand; break;
                case "*": result *= operand; break;
                case "/":
                    if (operand != 0) result /= operand;
                    break;
            }
        }

        saveToHistory(result);
        return result;
    }

    private int parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return 0;
        }
    }

    private void saveToHistory(int result) {
        StringBuilder sb = new StringBuilder();
        for (String t : tokens) sb.append(t).append(" ");
        sb.append("= ").append(result);
        history.add(sb.toString());
    }

    public void clear() {
        tokens.clear();
    }

    public void toggleMode() {
        advanceMode = !advanceMode;
    }

    public boolean isAdvanceMode() {
        return advanceMode;
    }

    public String getHistory() {
        StringBuilder sb = new StringBuilder();
        for (String entry : history) sb.append(entry).append("\n");
        return sb.toString();
    }
}
