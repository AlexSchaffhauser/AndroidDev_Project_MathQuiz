package com.example.a2020_19_06_project_alexsb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2020_19_06_project_alexsb.models.QuestionAnswer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    //Buttons and TextViews
    TextView titleView, questionView;
    EditText numView;
    Button btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine, btnZero, btnDecimal, btnNegative, btnGenerate, btnValidate,  btnClear, btnScore, btnFinish;

    //Q&A Object
    QuestionAnswer QnA;

    //Array of QnA
    List<QuestionAnswer> QnAList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {

        //Initialize TextView and EditText
        numView = findViewById(R.id.numView);
        numView.addTextChangedListener(this);
        titleView = findViewById(R.id.titleView);
        questionView = findViewById(R.id.questionView);

        //Initialize Button Views-------------------------------------
        btnOne = findViewById(R.id.btnOne);
        btnOne.setOnClickListener(this);
        btnTwo = findViewById(R.id.btnTwo);
        btnTwo.setOnClickListener(this);
        btnThree = findViewById(R.id.btnThree);
        btnThree.setOnClickListener(this);
        btnFour = findViewById(R.id.btnFour);
        btnFour.setOnClickListener(this);
        btnFive = findViewById(R.id.btnFive);
        btnFive.setOnClickListener(this);
        btnSix = findViewById(R.id.btnSix);
        btnSix.setOnClickListener(this);
        btnSeven = findViewById(R.id.btnSeven);
        btnSeven.setOnClickListener(this);
        btnEight = findViewById(R.id.btnEight);
        btnEight.setOnClickListener(this);
        btnNine = findViewById(R.id.btnNine);
        btnNine.setOnClickListener(this);
        btnZero = findViewById(R.id.btnZero);
        btnZero.setOnClickListener(this);
        btnDecimal = findViewById(R.id.btnDecimal);
        btnDecimal.setOnClickListener(this);
        btnNegative = findViewById(R.id.btnNegative);
        btnNegative.setOnClickListener(this);
        btnGenerate = findViewById(R.id.btnGenerate);
        btnGenerate.setOnClickListener(this);
        btnValidate = findViewById(R.id.btnValidate);
        btnValidate.setOnClickListener(this);
        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);
        btnScore = findViewById(R.id.btnScore);
        btnScore.setOnClickListener(this);
        btnFinish = findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(this);
        //----------------------------------------------------------
        //Generate first Question immediately
        generateQuestion();

        //Receive intent
        Intent i = getIntent();
        if (i.getStringExtra("NAME") != null){
            titleView.setText( i.getStringExtra("NAME"));
        }
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btnOne:
                editNumView("1");
                break;
            case R.id.btnTwo:
                editNumView("2");
                break;
            case R.id.btnThree:
                editNumView("3");
                break;
            case R.id.btnFour:
                editNumView("4");
                break;
            case R.id.btnFive:
                editNumView("5");
                break;
            case R.id.btnSix:
                editNumView("6");
                break;
            case R.id.btnSeven:
                editNumView("7");
                break;
            case R.id.btnEight:
                editNumView("8");
                break;
            case R.id.btnNine:
                editNumView("9");
                break;
            case R.id.btnZero:
                editNumView("0");
                break;
            case R.id.btnDecimal:
                editNumView(".");
                break;
            case R.id.btnNegative:
                editNumView("-");
                break;
            case R.id.btnGenerate:
                generateQuestion();
                break;
            case R.id.btnValidate:
                validateAnswer();
                break;
            case R.id.btnClear:
                numView.setText("");
                break;
            case R.id.btnScore:
                gotoResults();
                break;
            case R.id.btnFinish:
                finish();
                System.exit(0);
                break;
            default:
                break;
                
        }
    }

    private void editNumView(String s) {

        //Checks if user has entered decimal. If true and decimal already exists in the string return a message
        if (s.equals(".")){
            if(numView.getText().toString().contains(".")){
                Toast.makeText(this, "Only one decimal can be placed", Toast.LENGTH_SHORT).show();
            } else {
                // If Empty add 0
                if (numView.getText().toString().equals("") || numView.getText().toString().equals("-") ){
                    numView.setText(numView.getText().toString() + "0.");
                } else {
                    numView.setText(numView.getText().toString() + s);
                }
            }
        } else {

            if (s.equals("-")){
                if (numView.getText().toString().contains("-")){
                    numView.setText(numView.getText().toString().substring(1));
                } else {
                    numView.setText(s + numView.getText().toString());
                }
            } else {
                numView.setText(numView.getText().toString() + s);
            }
        }

    }

    private void generateQuestion() {

        //Create Random numbers for operation variables
        Random rnd = new Random();
        int operator = rnd.nextInt(4);
        String operatorStr;
        double num1 = rnd.nextInt(20);
        double num2 = rnd.nextInt(20);

        //Check for divide by 0
        while (operator == 3 && num2 == 0){
            num2 = rnd.nextInt(20);
        }

        //Get Operator
        switch (operator){
            case 1:
                operatorStr = "-";
                break;
            case 2:
                operatorStr = "*";
                break;
            case 3:
                operatorStr = "/";
                break;
            default:
                operatorStr = "+";
                break;
        }

        //Create question String
        String question = (int)num1 + " " + operatorStr + " " + (int)num2;

        //Send string to view
        questionView.setText(question);

        //Create new object and insert Question
        QnA = new QuestionAnswer();
        QnA.setNum1(num1);
        QnA.setNum2(num2);
        QnA.setOperator(operatorStr);
        QnA.setQuestion(question);
    }

    private void validateAnswer() {

        //Validate if number was entered
        if (numView.getText().toString().equals("") || numView.getText().toString().equals("-") || numView.getText().toString().equals("0.") || numView.getText().toString().equals("-.") || numView.getText().toString().equals("-0.")){
            Toast.makeText(this, "Please enter a valid answer.", Toast.LENGTH_SHORT).show();
        } else {

            //Enter answer into object
            QnA.setAnswer(numView.getText().toString());

            //Validate answer | First get operator
            double trueAnswer = 0;
            switch (QnA.getOperator()){
                case "+":
                    trueAnswer = QnA.getNum1() + QnA.getNum2();
                    break;
                case "-":
                    trueAnswer = QnA.getNum1() - QnA.getNum2();
                    break;
                case "*":
                    trueAnswer = QnA.getNum1() * QnA.getNum2();
                    break;
                case "/":
                    trueAnswer = QnA.getNum1() / QnA.getNum2();
                    break;
            }
            //Round true answer to two decimal places
            trueAnswer = Math.round(trueAnswer * 100.00) / 100.00;
            QnA.setTrueAnswer(Double.toString(trueAnswer));

            if (Double.parseDouble(QnA.getAnswer()) == trueAnswer){
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                QnA.setCorrect(true);
            } else {
                Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
                QnA.setCorrect(false);
            }
            QnAList.add(QnA);
            numView.setText("");
            generateQuestion();
        }

    }

    private void gotoResults() {
        Intent i = new Intent(MainActivity.this, ResultsActivity.class);
        i.putExtra("LIST", (Serializable) QnAList);
        startActivity(i);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (numView.getText().toString().length() > 6){
            Toast.makeText(this, "Answer is wrong", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}