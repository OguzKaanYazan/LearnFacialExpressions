package com.iot.learnfacialexpressions;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.widget.ImageButton;
import android.content.res.Configuration;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button newGame;
    private ImageButton bEn,bTr;
    private Button scores;
    private Button adminLogin;

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
        scores = (Button) findViewById((R.id.scores));
        scores.setOnClickListener(v -> navigateScores());
        adminLogin = (Button) findViewById(R.id.admin);
        adminLogin.setOnClickListener(v -> navigateAdminScreen());

        bEn = (ImageButton) findViewById(R.id.en);
        bTr = (ImageButton) findViewById(R.id.tr);

        bEn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setLocale(MainActivity.this, "");
                recreate();
            }
        });

        bTr.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setLocale(MainActivity.this, "tr");
                recreate();
            }
        });
    }

    public void startNewGame() {
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }

    public void navigateScores(){
        Intent intent = new Intent(this, ScoreList.class);
        startActivity(intent);
    }

    public void navigateAdminScreen(){
        Intent intent = new Intent(this, FileUpload.class);
        startActivity(intent);
    }

    public static void setLocale(Activity activity, String languageCode) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("LANG", Context.MODE_PRIVATE);
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lang", languageCode);
        editor.commit();
    }

}