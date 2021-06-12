package com.iot.learnfacialexpressions;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import info.hoang8f.widget.FButton;

public class Game extends AppCompatActivity {
    ArrayList<File> questions;
    ArrayList<File> data;
    ArrayList<String> optionList;
    ImageView questionImage;
    TextView questionNoTxt;
    Button option1;
    Button option2;
    Button option3;
    Button option4;
    int questionNo = 0;
    int correctAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);
        questionNoTxt = findViewById(R.id.questionNo);
        questionNoTxt.setText(questionNo+"");
        questionImage = findViewById(R.id.questionImage);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        option1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d("option1", option1.getText().toString());
                checkAnswer(option1.getText().toString());
            }
        });
        option2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                checkAnswer(option2.getText().toString());
            }
        });
        option3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                checkAnswer(option3.getText().toString());
            }
        });
        option4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                checkAnswer(option4.getText().toString());
            }
        });

        FirebaseApp.initializeApp(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("File/");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<File> map = new ArrayList<File>();
                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                    File userModel = userSnapshot.getValue(File.class);
                    map.add(userModel);
                }
                data = map;
                questions = getQuestions();
                getOptions(database);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("VALUE", "Failed to read value.", error.toException());
            }
        });
    }

    private ArrayList<File> getQuestions(){
        ArrayList<File> randomFiles = new ArrayList<>();
        //RANDOM NUM DAHA ÖNCE ÇEKİLDİYSE BAŞKA RANDOM ÜRET
        for(int i = 0; i < 10; i++){
            Random random = new Random();
            int randomNum = random.nextInt(data.size());
            while(randomFiles.contains(randomNum) || data.get(randomNum) == null){
                randomNum = random.nextInt(data.size());
            }
            randomFiles.add(data.get(randomNum));
        }
        return randomFiles;
    }

    public void showDialog(boolean isCorrect) {
        final Dialog dialogCorrect = new Dialog(Game.this);
        dialogCorrect.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogCorrect.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogCorrect.setContentView(R.layout.dialog_modal);
        dialogCorrect.setCancelable(false);
        FButton buttonNext = (FButton) dialogCorrect.findViewById(R.id.dialogNext);
        ImageView icon = (ImageView) dialogCorrect.findViewById(R.id.icon);
        if(isCorrect == true){
            icon.setImageResource(R.drawable.tick);
        }else{
            icon.setImageResource(R.drawable.cross);
        }

        dialogCorrect.show();
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCorrect.dismiss();
                if(questionNo < 10){
                    goNext();
                    if(questionNo == 9){
                        buttonNext.setText("Finish");
                    }
                }else{
                    navigateToResults();
                }
            }
        });
    }

    private void goNext(){
        questionNo++;
        questionNoTxt.setText(questionNo+"");
        Picasso.with(Game.this).load(questions.get(questionNo - 1).getFileName()).into(questionImage);
        setOptions(getRandomOptions(), questions.get(questionNo - 1).getExpression());
    }

    private void checkAnswer(String answer){
        int correct = this.getResources().getIdentifier(questions.get(questionNo-1).getExpression(), "string", this.getPackageName());
        if(getString(correct).equals(answer)) {
            correctAnswers++;
            showDialog(true);
        }else{
            showDialog(false);
        }
    }

    private void navigateToResults() {
        Intent intent = new Intent(this, Result.class);
        intent.putExtra("SCORE", correctAnswers+"/10");
        startActivity(intent);
    }

    private ArrayList<Integer> getRandomOptions(){
        ArrayList<Integer> selectedOptions = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < 3; i++){
            int randomOpt = random.nextInt(optionList.size());
            while(selectedOptions.contains(randomOpt)){
                randomOpt = random.nextInt(optionList.size());
            }
            selectedOptions.add(randomOpt);
        }
        return selectedOptions;
    }

    private void setOptions(ArrayList<Integer> randomOptions, String answer){
        Random random = new Random();
        int correctOpt = random.nextInt(4);
        int correctAnswer = this.getResources().getIdentifier(answer, "string", this.getPackageName());
        int answer1 = this.getResources().getIdentifier(optionList.get(randomOptions.get(0)), "string", this.getPackageName());
        int answer2 = this.getResources().getIdentifier(optionList.get(randomOptions.get(1)), "string", this.getPackageName());
        int answer3 = this.getResources().getIdentifier(optionList.get(randomOptions.get(2)), "string", this.getPackageName());

        switch(correctOpt){
            case 0:
                option1.setText(getString(correctAnswer));
                option2.setText(answer1);
                option3.setText(answer2);
                option4.setText(answer3);
                break;
            case 1:
                option2.setText(getString(correctAnswer));
                option1.setText(answer1);
                option3.setText(answer2);
                option4.setText(answer3);
                break;
            case 2:
                option3.setText(getString(correctAnswer));
                option1.setText(answer1);
                option2.setText(answer2);
                option4.setText(answer3);
                break;
            default:
                option4.setText(getString(correctAnswer));
                option2.setText(answer1);
                option3.setText(answer2);
                option1.setText(answer3);
                break;
        }
    }

    private void getOptions(FirebaseDatabase db){
        DatabaseReference myRef = db.getReference("Option/");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> options = new ArrayList<String>();
                for(DataSnapshot optionSnapshot : dataSnapshot.getChildren()){
                    String option = optionSnapshot.getValue(String.class);
                    Log.d("option", option);
                    options.add(option);
                }
                optionList = options;
                goNext();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("VALUE", "Failed to read value.", error.toException());
            }
        });
    }

}