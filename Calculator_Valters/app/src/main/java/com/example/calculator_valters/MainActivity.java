package com.example.calculator_valters;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText etInput;
    private double memoryValue = 0;
    private String currentOperation = "";
    private double firstValue = 0;
    private boolean resultShown = false;

    private String prevInput = "";
    private String prevOperation = "";
    private double prevFirstValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etInput = findViewById(R.id.etInput);

        etInput.setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(etInput.getWindowToken(), 0);
            }
        });

        findViewById(R.id.btnMS).setOnClickListener(v -> {
            try {
                memoryValue = Double.parseDouble(etInput.getText().toString());
            } catch (NumberFormatException ignored) {
            }
        });

        findViewById(R.id.btnMR).setOnClickListener(v -> etInput.setText(String.valueOf(memoryValue)));

        findViewById(R.id.btnMC).setOnClickListener(v -> {
            memoryValue = 0;
            etInput.setText("");
        });

        findViewById(R.id.btnUndo).setOnClickListener(v -> handleUndo());

        int[] numberButtons = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};
        for (int id : numberButtons) {
            findViewById(id).setOnClickListener(v -> {
                if (resultShown) {
                    etInput.setText("");
                    resultShown = false;
                }
                Button b = (Button) v;
                etInput.append(b.getText().toString());
            });
        }

        findViewById(R.id.btnAdd).setOnClickListener(v -> handleOperation("+"));
        findViewById(R.id.btnSubtract).setOnClickListener(v -> handleOperation("-"));
        findViewById(R.id.btnMultiply).setOnClickListener(v -> handleOperation("*"));
        findViewById(R.id.btnDivide).setOnClickListener(v -> handleOperation("/"));

        findViewById(R.id.btnEquals).setOnClickListener(v -> computeResult());
    }

    private void handleOperation(String operation) {
        // Storing previous state
        prevInput = etInput.getText().toString();
        prevOperation = currentOperation;
        prevFirstValue = firstValue;

        currentOperation = operation;
        try {
            firstValue = Double.parseDouble(etInput.getText().toString());
            etInput.setText("");
        } catch (NumberFormatException e) {
            etInput.setText("Error");
        }
    }

    private void handleUndo() {
        etInput.setText(prevInput);
        currentOperation = prevOperation;
        firstValue = prevFirstValue;
    }

    private void computeResult() {
        double secondValue, result = 0;
        try {
            secondValue = Double.parseDouble(etInput.getText().toString());
            switch (currentOperation) {
                case "+":
                    result = firstValue + secondValue;
                    break;
                case "-":
                    result = firstValue - secondValue;
                    break;
                case "*":
                    result = firstValue * secondValue;
                    break;
                case "/":
                    if (secondValue == 0) {
                        etInput.setText("Error");
                        return;
                    }
                    result = firstValue / secondValue;
                    break;
            }
            etInput.setText(String.valueOf(result));
            resultShown = true;
        } catch (NumberFormatException e) {
            etInput.setText("Error");
        }
    }
}
