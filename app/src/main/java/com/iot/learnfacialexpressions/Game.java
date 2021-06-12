package com.iot.learnfacialexpressions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

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

public class Game extends AppCompatActivity {
    ArrayList<File> questions;
    ArrayList<File> data;
    ImageView questionImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);
        questionImage = findViewById(R.id.questionImage);
        FirebaseApp.initializeApp(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("File/");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                ArrayList<File> map = new ArrayList<File>();
                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                    File userModel = userSnapshot.getValue(File.class);
                    map.add(userModel);
                    Log.d("usermodel", "Value is: " + userModel.getFileName());
                }
                data = map;
                questions = getQuestions();
              Picasso.with(Game.this).load(questions.get(0).getFileName()).into(questionImage);
                Log.d("VALUE", "Value is: " + map.toString());
                Log.d("QUESTIONS", questions.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("VALUE", "Failed to read value.", error.toException());
            }
        });
    }

    private ArrayList<File> getQuestions(){
        ArrayList<File> randomFiles = new ArrayList<>();
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
}