package com.iot.learnfacialexpressions;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;
import android.widget.ImageButton;
import android.content.res.Configuration;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button newGame;
    private ImageButton bEn,bTr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Locale locale = Locale.getDefault();
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_main);

        newGame = (Button) findViewById(R.id.start);
        newGame.setOnClickListener(v -> startNewGame());

        bEn = (ImageButton) findViewById(R.id.en);
        bTr = (ImageButton) findViewById(R.id.tr);

        bEn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Locale locale = new Locale("");
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                finish();
                startActivity(getIntent());
            }
        });

        bTr.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Locale locale = new Locale("tr");
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                finish();
                startActivity(getIntent());
            }
        });
    }

    public void startNewGame() {
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }
}