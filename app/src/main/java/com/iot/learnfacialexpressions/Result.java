package com.iot.learnfacialexpressions;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Result extends AppCompatActivity {
    TextView score;
    Button homeBtn;
    Button saveBtn;
    Button tryAgainBtn;
    Boolean isSaved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        score = findViewById(R.id.score);
        score.setText(getIntent().getStringExtra("SCORE"));

        //HOME BUTTON ON CLICK
        homeBtn = findViewById(R.id.home);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateHome();
            }
        });

        //SAVE BUTTON ON CLICK
        saveBtn = findViewById(R.id.save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveScore();
            }
        });
        //TRY AGAIN BUTTON ON CLICK
        tryAgainBtn = findViewById(R.id.tryAgain);
        tryAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame();
            }
        });
    }

    private void navigateHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void startGame() {
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }

    private void saveScore(){
        if(isSaved){
            Toast.makeText(getApplicationContext(),
                    "You have already saved your score.",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        isSaved = true;
        Random random = new Random();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY HH:MM");
        String format = formatter.format(new Date());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Score/");
        Score score = new Score(getIntent().getStringExtra("SCORE"), format);
        myRef.child(random.nextInt()+""+random.nextInt()).setValue(score);
        Toast.makeText(getApplicationContext(),
                "Score Saved.",
                Toast.LENGTH_LONG)
                .show();
    }

}