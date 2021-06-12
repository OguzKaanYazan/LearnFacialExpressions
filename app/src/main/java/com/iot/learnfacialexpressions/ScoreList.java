package com.iot.learnfacialexpressions;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ScoreList extends AppCompatActivity {
    private ArrayList<Score> scoreList;
    private ListView listView;
    private ScoreListAdapter scoreListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_list);
        getScores();
    }

    private void setScoreList(ArrayList<Score> scores) {
        scoreList = scores;
        listView = (ListView) findViewById(R.id.listView);
        scoreListAdapter = new ScoreListAdapter(ScoreList.this, scores);
        listView.setAdapter(scoreListAdapter);
    }

    public void getScores() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Score/");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Score> map = new ArrayList<Score>();
                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                    Score scoreModel = userSnapshot.getValue(Score.class);
                    map.add(scoreModel);
                }
                setScoreList(map);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("VALUE", "Failed to read value.", error.toException());
            }
        });
    }

}
