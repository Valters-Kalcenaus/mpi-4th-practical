package com.firstapp.calculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText userInput;
    private double memoryValue = 0;
    private double firstValue = 0;
    private String currentOperation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInput = findViewById(R.id.etInput);

        findViewById(R.id.btnMS).setOnClickListener(view -> storeToMemory());
        findViewById(R.id.btnMR).setOnClickListener(view -> recallFromMemory());
        findViewById(R.id.btnMC).setOnClickListener(view -> clearMemory());
        findViewById(R.id.btnClearInput).setOnClickListener(view -> clearInput());

        setNumberButtonListeners();

        findViewById(R.id.btnAdd).setOnClickListener(view -> handleOperation("+"));
        findViewById(R.id.btnSubtract).setOnClickListener(view -> handleOperation("-"));
        findViewById(R.id.btnMultiply).setOnClickListener(view -> handleOperation("*"));
        findViewById(R.id.btnDivide).setOnClickListener(view -> handleOperation("/"));
        findViewById(R.id.btnEquals).setOnClickListener(view -> computeResult());
    }

    private void setNumberButtonListeners() {
        int[] numberButtonIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };

        for (int buttonId : numberButtonIds) {
            Button button = findViewById(buttonId);
            button.setOnClickListener(view -> appendToInput(button.getText().toString()));
        }
    }

    private void appendToInput(String text) {
        String currentText = userInput.getText().toString();
        if (currentText.equals("0") || currentText.equals("Error")) {
            userInput.setText(text);
        } else {
            userInput.append(text);
        }
    }

    private void storeToMemory() {
        try {
            memoryValue = Double.parseDouble(userInput.getText().toString());
        } catch (NumberFormatException ignored) {
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
        }
    }

    private void recallFromMemory() {
        userInput.setText(String.valueOf(memoryValue));
    }

    private void clearMemory() {
        memoryValue = 0;
        userInput.setText("0");
    }

    private void clearInput() {
        userInput.setText("0");
    }

    private void handleOperation(String operation) {
        currentOperation = operation;
        try {
            firstValue = Double.parseDouble(userInput.getText().toString());
            userInput.setText("");
        } catch (NumberFormatException e) {
            userInput.setText("Error");
        }
    }

    private void computeResult() {
        double secondValue, result = 0;
        try {
            secondValue = Double.parseDouble(userInput.getText().toString());
            if (currentOperation.equals("+")) {
                result = firstValue + secondValue;
            } else if (currentOperation.equals("-")) {
                result = firstValue - secondValue;
            } else if (currentOperation.equals("*")) {
                result = firstValue * secondValue;
            } else if (currentOperation.equals("/")) {
                if (secondValue == 0) {
                    userInput.setText("Error");
                    return;
                }
                result = firstValue / secondValue;
            }
            userInput.setText(String.valueOf(result));
        } catch (NumberFormatException e) {
            userInput.setText("Error");
        }
    }
}
