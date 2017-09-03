package com.example.nhem.caculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.view.Window;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private TextView screen;
    private String display = "";
    private String currentOperator = "";
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        screen = (TextView) findViewById(R.id.tv_text);
        screen.setText(display);
    }

    private void updateScreen() {
        screen.setText(display);
    }

    public void onClickNumber(View view) {
        if (result != "") {
            reset();
            updateScreen();
        }

        Button b = (Button) view;
        display += b.getText();
        updateScreen();
    }

    private void reset() {
        display = "";
        currentOperator = "";
        result = "";
    }

    public void onClickReset(View view) {
        reset();
        updateScreen();
    }

    private boolean isOperator(char op) {
        switch (op) {
            case '+':
            case '-':
            case 'x':
            case '/': return true;
            default: return false;
        }
    }

    public void onClickOperator(View view) {
        Button b = (Button) view;

        if (display == "") return;

        if (result != "") {
            String oldDisplay = result;
            reset();
            display = oldDisplay;
        }

        if (currentOperator != "") {
           if ( isOperator(display.charAt(display.length() - 1)) ) {
               display = display.replace(display.charAt(display.length() - 1), b.getText().charAt(0));
               updateScreen();
               currentOperator = b.getText().toString();
               return;
           } else {
               getResult();
               display = result;
               result = "";
           }
        }

        display += b.getText();
        currentOperator = b.getText().toString();
        updateScreen();
    }

    private double operate(String a, String b, String op) {
        switch (op) {
            case "+":
                return Double.valueOf(a) + Double.valueOf(b);
            case "-":
                return Double.valueOf(a) - Double.valueOf(b);
            case "x":
                return Double.valueOf(a) * Double.valueOf(b);
            case "/":
                try {
                    return Double.valueOf(a) / Double.valueOf(b);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            default: return -1;
        }
    }

    private boolean getResult() {
        if (currentOperator == "") return false;
        String[] operation = display.split(Pattern.quote(currentOperator));
        if (operation.length < 2) return false;
        result = String.valueOf(operate(operation[0], operation[1], currentOperator));
        return true;
    }

    public void onClickResult(View view) {
        if (display == "") return;
        if (!getResult()) return;
        screen.setText(display + "\n" + String.valueOf(result));
    }
}
