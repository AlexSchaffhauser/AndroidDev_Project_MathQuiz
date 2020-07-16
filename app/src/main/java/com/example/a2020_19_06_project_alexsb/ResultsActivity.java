package com.example.a2020_19_06_project_alexsb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a2020_19_06_project_alexsb.models.QuestionAnswer;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    TextView percentView;
    EditText nameEdit;
    RadioGroup rbGroup;
    Button btnShow, btnBack;
    ListView listView;

    List<QuestionAnswer> QnAList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        initialize();
    }

    private void initialize() {
        //Text
        percentView = findViewById(R.id.percentView);
        nameEdit = findViewById(R.id.nameEdit);

        //Buttons
        btnShow = findViewById(R.id.btnShow);
        btnShow.setOnClickListener(this);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        //Radio group
        rbGroup = findViewById(R.id.rbGroup);
        rbGroup.setOnCheckedChangeListener(this);

        //Get intent
        Intent i = getIntent();
        QnAList = (List<QuestionAnswer>) i.getSerializableExtra("LIST");

        //Set up List View
        listView = findViewById(R.id.listView);

        sortAll();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnShow:
                calculateScore();
                break;
            case R.id.btnBack:
                sendBack();
                break;
        }

    }

    private void calculateScore() {
        double correct = 0;
        double total = 0;
        //Iterate through list
        for (QuestionAnswer qa : QnAList){
            if (qa.isCorrect())
                correct++;
            total++;
        }
        double percent = (correct/total)*100;
        percentView.setText((int)percent + "%");
    }

    private void sendBack() {
        if (nameEdit.getText().toString().equals("")){
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(ResultsActivity.this, MainActivity.class);
            String sendText = nameEdit.getText().toString() + " | " + percentView.getText().toString();
            i.putExtra("NAME", sendText);
            finishAffinity();
            startActivity(i);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId){
            case R.id.rbAll:
                sortAll();
                break;
            case R.id.rbRight:
                sortRight();
                break;
            case R.id.rbWrong:
                sortWrong();
                break;
            case R.id.rbAsc:
                sortAsc();
                break;
            case R.id.rbDesc:
                sortDesc();
                break;
            default:
                Toast.makeText(this, "None Selected", Toast.LENGTH_SHORT).show();
        }

    }

    private void sortAll() {
        List<String> allArray = new ArrayList<>();

        for (QuestionAnswer qa : QnAList){
            String resultString = toResultString(qa);

            allArray.add(resultString);
        }
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allArray);
        listView.setAdapter(listAdapter);
    }

    private void sortRight() {
        List<String> rightArray = new ArrayList<>();

        for (QuestionAnswer qa : QnAList){
            if (qa.isCorrect()){
                String resultString = toResultString(qa);

                rightArray.add(resultString);
            }
        }
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rightArray);
        listView.setAdapter(listAdapter);
    }

    private void sortWrong() {
        List<String> wrongArray = new ArrayList<>();

        for (QuestionAnswer qa : QnAList){
            if (!qa.isCorrect()){
                String resultString = toResultString(qa);

                wrongArray.add(resultString);
            }
        }
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, wrongArray);
        listView.setAdapter(listAdapter);
    }

    private void sortAsc() {
        List<String> ascArray = new ArrayList<>();

        for (QuestionAnswer qa : QnAList){
            if (qa.isCorrect()){
                String resultString = toResultString(qa);

                ascArray.add(resultString);
            }
        }
        for (QuestionAnswer qa : QnAList){
            if (!qa.isCorrect()){
                String resultString = toResultString(qa);

                ascArray.add(resultString);
            }
        }
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ascArray);
        listView.setAdapter(listAdapter);
    }

    private void sortDesc() {
        List<String> ascArray = new ArrayList<>();

        for (QuestionAnswer qa : QnAList){
            if (!qa.isCorrect()){
                String resultString = toResultString(qa);

                ascArray.add(resultString);
            }
        }
        for (QuestionAnswer qa : QnAList){
            if (qa.isCorrect()){
                String resultString = toResultString(qa);

                ascArray.add(resultString);
            }
        }
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ascArray);
        listView.setAdapter(listAdapter);
    }

    private String toResultString(QuestionAnswer qa) {
        if (qa.isCorrect()){
            return "Right | " + qa.getQuestion() + " = " + qa.getAnswer();
        } else {
            return "Wrong | " + qa.getQuestion() + " = " + qa.getAnswer() + " | Correct Answer is: " + qa.getTrueAnswer();
        }
    }
}
