package com.example.swlab.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Mood_Detection_Activity extends AppCompatActivity{

    private SimpleDateFormat dtFormat;
    private String nowTime;
    private Date date;
    private QuestionBank mQuestionLibrary = new QuestionBank();
    private TextView mQuestionView;
    private Button mButtonChoice1;
    private Button mButtonChoice2;
    private Button mButtonChoice3;
    private Button mButtonChoice4;
    private String mAnswer;
    private int mQuestionNumber = 0 ;
    private int mScore=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mood_detection);
        processView();
        updateQuestion();
        processControl();
    }

    private void processView() {
        mQuestionView = (TextView) findViewById(R.id.question);
        mButtonChoice1 = (Button) findViewById(R.id.choice1);
        mButtonChoice2 = (Button) findViewById(R.id.choice2);
        mButtonChoice3 = (Button) findViewById(R.id.choice3);
        mButtonChoice4 = (Button) findViewById(R.id.choice4);
    }

    private void processControl() {

    }

    private void updateQuestion(){
        if (mQuestionNumber<mQuestionLibrary.getLength() ){
            mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestionNumber));
            mButtonChoice1.setText(mQuestionLibrary.getChoice(mQuestionNumber, 1));
            mButtonChoice2.setText(mQuestionLibrary.getChoice(mQuestionNumber, 2));
            mButtonChoice3.setText(mQuestionLibrary.getChoice(mQuestionNumber, 3));
            mButtonChoice4.setText(mQuestionLibrary.getChoice(mQuestionNumber, 4));
            //mAnswer = mQuestionLibrary.getCorrectAnswer(mQuestionNumber);
            addScore();
            mQuestionNumber++;
            }
        else if(mQuestionNumber==mQuestionLibrary.getLength()) {
            addScore();
            finishDialog();
        }
    }

    private void finishDialog() {
        AlertDialog.Builder finishDialog=new AlertDialog.Builder(this);
        finishDialog.setTitle("最終結果");
        finishDialog.setMessage("您的快樂指數為"+mScore+"分");
        DialogInterface.OnClickListener confirmClick =new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dtFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                date = new Date();
                nowTime = dtFormat.format(date);
                Firebase myFirebaseRef = new Firebase("https://swlabapp.firebaseio.com").child("moodDetection");
                DB_Mood_Detiction data = new DB_Mood_Detiction(mScore+"",nowTime);
                myFirebaseRef.push().setValue(data);

                Intent intent=new Intent();
                intent.setClass(Mood_Detection_Activity.this,Mood_Activity.class);
                startActivity(intent);
            }
        };
        DialogInterface.OnClickListener redoClick =new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent();
                intent.setClass(Mood_Detection_Activity.this,Mood_Detection_Activity.class);
                startActivity(intent);
            }
        };
        finishDialog.setNegativeButton("確定",confirmClick);
        finishDialog.setPositiveButton("重作",redoClick);
        finishDialog.show();
    }

    private void addScore() {
        mButtonChoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScore += 1;
                updateQuestion();
            }
        });
        mButtonChoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScore += 2;
                updateQuestion();
            }
        });
        mButtonChoice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScore += 3;
                updateQuestion();
            }
        });
        mButtonChoice4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScore += 4;
                updateQuestion();
            }
        });

    }
}
