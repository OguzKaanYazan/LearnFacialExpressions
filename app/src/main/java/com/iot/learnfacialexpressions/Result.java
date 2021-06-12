package com.iot.learnfacialexpressions;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;
import android.widget.ImageButton;
import android.content.res.Configuration;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Result extends AppCompatActivity {
    TextView score;
    Button homeBtn;
    Button saveBtn;
    Button tryAgainBtn;

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


}