package com.example.calculatormvc.controller;

import com.example.calculatormvc.model.CalculatorModel;
import com.example.calculatormvc.view.MainActivity;

public class CalculatorController {

    private final MainActivity view;
    private final CalculatorModel model;

    private StringBuilder currentInput = new StringBuilder();
    private double firstOperand = 0;
    private String operator = "";
    private boolean isOperatorClicked = false;

    public CalculatorController(MainActivity view) {
        this.view = view;
        this.model = new CalculatorModel();
        this.view.setupListeners(this); // Pass controller reference for button interactions
    }

    public void onDigitClicked(String digit) {
        if (isOperatorClicked) {
            currentInput.setLength(0); // Clear previous input
            isOperatorClicked = false;
        }
        currentInput.append(digit);
        view.showResult(currentInput.toString());
    }

    public void onOperatorClicked(String op) {
        try {
            firstOperand = Double.parseDouble(currentInput.toString());
            operator = op;
            isOperatorClicked = true;
        } catch (NumberFormatException e) {
            view.showError("Invalid input");
        }
    }

    public void onEqualClicked() {
        try {
            double secondOperand = Double.parseDouble(currentInput.toString());
            double result = 0;

            switch (operator) {
                case "+":
                    result = model.add(firstOperand, secondOperand);
                    break;
                case "-":
                    result = model.subtract(firstOperand, secondOperand);
                    break;
                case "*":
                    result = model.multiply(firstOperand, secondOperand);
                    break;
                case "/":
                    if (secondOperand == 0) {
                        view.showError("Cannot divide by zero");
                        return;
                    }
                    result = model.divide(firstOperand, secondOperand);
                    break;
                default:
                    view.showError("No operation selected");
                    return;
            }

            currentInput = new StringBuilder(String.valueOf(result));
            view.showResult(currentInput.toString());
            operator = "";
            isOperatorClicked = false;

        } catch (NumberFormatException e) {
            view.showError("Invalid input");
        }
    }

    public void onClearClicked() {
        currentInput.setLength(0);
        firstOperand = 0;
        operator = "";
        isOperatorClicked = false;
        view.showResult("0");
    }

    public void onDeleteClicked() {
        int len = currentInput.length();
        if (len > 0) {
            currentInput.deleteCharAt(len - 1);
            view.showResult(currentInput.toString());
        }
    }
}
