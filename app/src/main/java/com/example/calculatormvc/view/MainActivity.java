package com.example.calculatormvc.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.calculatormvc.R;
import com.example.calculatormvc.controller.CalculatorController;

public class MainActivity extends AppCompatActivity {

    TextView tvResult;
    StringBuilder inputBuilder = new StringBuilder();
    String operator = "";
    double firstOperand = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvResult);

        setNumberClickListeners();
        setOperatorClickListeners();
        new CalculatorController(this);
    }
    public void setupListeners(CalculatorController controller) {
        int[] digitButtonIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot
        };

        for (int id : digitButtonIds) {
            Button btn = findViewById(id);
            btn.setOnClickListener(view -> controller.onDigitClicked(btn.getText().toString()));
        }

        findViewById(R.id.btnAdd).setOnClickListener(v -> controller.onOperatorClicked("+"));
        findViewById(R.id.btnSub).setOnClickListener(v -> controller.onOperatorClicked("-"));
        findViewById(R.id.btnMul).setOnClickListener(v -> controller.onOperatorClicked("*"));
        findViewById(R.id.btnDiv).setOnClickListener(v -> controller.onOperatorClicked("/"));
        findViewById(R.id.btnClear).setOnClickListener(v -> controller.onClearClicked());
        findViewById(R.id.btnEqual).setOnClickListener(v -> controller.onEqualClicked());
        findViewById(R.id.btnDel).setOnClickListener(v -> controller.onDeleteClicked());
    }

    public void showResult(String result) {
        tvResult.setText(result);
    }

    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    private void setNumberClickListeners() {
        int[] numButtonIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot
        };

        View.OnClickListener listener = view -> {
            Button btn = (Button) view;
            inputBuilder.append(btn.getText().toString());
            tvResult.setText(inputBuilder.toString());
        };

        for (int id : numButtonIds) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setOperatorClickListeners() {
        findViewById(R.id.btnAdd).setOnClickListener(v -> handleOperator("+"));
        findViewById(R.id.btnSub).setOnClickListener(v -> handleOperator("-"));
        findViewById(R.id.btnMul).setOnClickListener(v -> handleOperator("*"));
        findViewById(R.id.btnDiv).setOnClickListener(v -> handleOperator("/"));
        findViewById(R.id.btnClear).setOnClickListener(v -> clearInput());
        findViewById(R.id.btnEqual).setOnClickListener(v -> calculateResult());
        findViewById(R.id.btnDel).setOnClickListener(v -> deleteLast());
    }

    private void handleOperator(String op) {
        try {
            firstOperand = Double.parseDouble(inputBuilder.toString());
            operator = op;
            inputBuilder.setLength(0);
            tvResult.setText(operator); // You can also clear screen instead
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
        }
    }

    private void calculateResult() {
        try {
            double secondOperand = Double.parseDouble(inputBuilder.toString());
            double result = 0;

            switch (operator) {
                case "+": result = firstOperand + secondOperand; break;
                case "-": result = firstOperand - secondOperand; break;
                case "*": result = firstOperand * secondOperand; break;
                case "/":
                    if (secondOperand == 0) {
                        tvResult.setText("Error");
                        return;
                    }
                    result = firstOperand / secondOperand;
                    break;
            }

            inputBuilder.setLength(0);
            inputBuilder.append(result);
            tvResult.setText(String.valueOf(result));
        } catch (NumberFormatException e) {
            tvResult.setText("Error");
        }
    }

    private void clearInput() {
        inputBuilder.setLength(0);
        operator = "";
        firstOperand = 0;
        tvResult.setText("0");
    }

    private void deleteLast() {
        int len = inputBuilder.length();
        if (len > 0) {
            inputBuilder.deleteCharAt(len - 1);
            tvResult.setText(inputBuilder.toString());
        }
    }
}
