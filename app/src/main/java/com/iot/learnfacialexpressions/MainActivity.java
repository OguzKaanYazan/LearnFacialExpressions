package com.iot.learnfacialexpressions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button newGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newGame = (Button) findViewById(R.id.start);
        newGame.setOnClickListener(v -> startNewGame());
    }

    public void startNewGame() {
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }
}