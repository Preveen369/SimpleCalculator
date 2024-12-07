package com.example.simplecalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView inputTextView, resultTextView;
    private String input = "";
    private String operator = "";
    private boolean isOperatorClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize TextViews
        inputTextView = findViewById(R.id.inputTextView);
        resultTextView = findViewById(R.id.resultTextView);

        // Find all digit and operator buttons and set click listeners
        int[] buttonIds = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9,
                R.id.buttonPlus, R.id.buttonMinus, R.id.buttonMultiply, R.id.buttonDivide,
                R.id.buttonEquals, R.id.buttonClear, R.id.buttonDecimal,
                R.id.buttonLeftParen, R.id.buttonRightParen
        };

        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(buttonClickListener);
        }
    }

    private final View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button button = (Button) view;
            String buttonText = button.getText().toString();

            // Use if-else instead of switch to handle button clicks
            if (view.getId() == R.id.buttonClear) {
                input = "";
                operator = "";
                isOperatorClicked = false;
                resultTextView.setText("");
            } else if (view.getId() == R.id.buttonEquals) {
                calculateResult();
            } else if (view.getId() == R.id.buttonPlus ||
                    view.getId() == R.id.buttonMinus ||
                    view.getId() == R.id.buttonMultiply ||
                    view.getId() == R.id.buttonDivide) {
                if (!isOperatorClicked) {
                    operator = buttonText;
                    input += " " + operator + " ";
                    isOperatorClicked = true;
                }
            } else if (view.getId() == R.id.buttonDecimal ||
                    view.getId() == R.id.buttonLeftParen ||
                    view.getId() == R.id.buttonRightParen) {
                input += buttonText;
            } else { // Handles digit buttons
                input += buttonText;
                isOperatorClicked = false;
            }

            inputTextView.setText(input);
        }
    };


    private void calculateResult() {
        try {
            // Split the input by spaces to separate numbers and operators
            String[] tokens = input.split(" ");
            if (tokens.length < 3) {
                resultTextView.setText("Error");
                return;
            }

            double operand1 = Double.parseDouble(tokens[0]);
            String operator = tokens[1];
            double operand2 = Double.parseDouble(tokens[2]);

            double result = 0;

            // Perform the corresponding operation
            switch (operator) {
                case "+":
                    result = operand1 + operand2;
                    break;
                case "-":
                    result = operand1 - operand2;
                    break;
                case "*":
                    result = operand1 * operand2;
                    break;
                case "/":
                    if (operand2 != 0) {
                        result = operand1 / operand2;
                    } else {
                        resultTextView.setText("Cannot divide by 0");
                        input = "";
                        return;
                    }
                    break;
                default:
                    resultTextView.setText("Error");
                    input = "";
                    return;
            }

            // Display the result and prepare for the next calculation
            resultTextView.setText(String.valueOf(result));
            input = "";

        } catch (NumberFormatException e) {
            resultTextView.setText("Error");
            input = "";
        }
    }
}
