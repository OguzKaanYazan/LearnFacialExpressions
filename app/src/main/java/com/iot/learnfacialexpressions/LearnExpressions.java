package com.iot.learnfacialexpressions;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LearnExpressions extends AppCompatActivity {
    ArrayList<File> expressionList;
    ImageView expressionImg;
    ImageView nextExpression;
    ImageView previousExpression;
    TextView expressionTxt;
    int currentExpression = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facial_expression);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(R.string.learn_expressions);
        }
        expressionImg = findViewById(R.id.expressionImg);
        nextExpression = findViewById(R.id.nextExpression);
        previousExpression = findViewById(R.id.previousExpression);
        expressionTxt = findViewById(R.id.expressionTxt);
        nextExpression.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                nextExpression();
            }
        });
        previousExpression.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                previousExpression();
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
                expressionList = map;
                setExpressionView();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("VALUE", "Failed to read value.", error.toException());
            }
        });

    }

    private void nextExpression(){
        currentExpression = currentExpression + 1;
        setExpressionView();
    }

    private void previousExpression(){
        currentExpression = currentExpression - 1;
        setExpressionView();
    }

    private void navigateHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void setExpressionView(){
        File newExpression = expressionList.get(currentExpression);
        Picasso.with(LearnExpressions.this).load(newExpression.getFileName()).into(expressionImg);
        int text = this.getResources().getIdentifier((newExpression.getExpression()), "string", this.getPackageName());
        expressionTxt.setText(text);
            if(currentExpression == expressionList.size() - 1){
                nextExpression.setVisibility(View.INVISIBLE);
            }else{
                nextExpression.setVisibility(View.VISIBLE);
            }

            if(currentExpression == 0){
                previousExpression.setVisibility(View.INVISIBLE);
            }else{
                previousExpression.setVisibility(View.VISIBLE);
            }
    }
}