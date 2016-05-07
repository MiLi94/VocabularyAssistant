package com.limi.andorid.vocabularyassistant.acti;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.limi.andorid.vocabularyassistant.R;
import com.limi.andorid.vocabularyassistant.data.Word;
import com.limi.andorid.vocabularyassistant.helper.WordImportHandler;

import java.util.ArrayList;

public class SummaryActivity extends AppCompatActivity implements View.OnClickListener {

    private int startID;
    private int endID;
    private RecyclerView mRecyclerView;
    private SummaryAdapter myAdapter;
    private Button goingBtn;
    private Button exitBtn;
    private ImageButton reciteAgain;
    private ImageButton test;

    private ArrayList<Word> summaryList = new ArrayList<>();
    private int finishID = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        goingBtn = (Button) findViewById(R.id.going);
        exitBtn = (Button) findViewById(R.id.quit);
        reciteAgain = (ImageButton) findViewById(R.id.again);
        test = (ImageButton) findViewById(R.id.exercise);
        goingBtn.setOnClickListener(this);
        exitBtn.setOnClickListener(this);
        reciteAgain.setOnClickListener(this);
        test.setOnClickListener(this);

        startID = bundle.getInt("startID");
        endID = bundle.getInt("endID");

        for (int i = startID; i <= endID; i++) {
            summaryList.add(WordImportHandler.systemWordBaseArrayList.get(i));
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.summarylist);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setHasFixedSize(true);

        myAdapter = new SummaryAdapter(this, summaryList);

        mRecyclerView.setAdapter(myAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.going):
                if (endID == finishID) {
                    //finish
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), RecitingActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("nextStartID", ++endID);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }

                break;
            case (R.id.quit):
                quitActivity();
                break;
            case (R.id.again):
                Intent intent = new Intent(getApplicationContext(), RecitingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("nextStartID", startID);
                Toast.makeText(getApplicationContext(), String.valueOf(startID), Toast.LENGTH_SHORT).show();
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                break;
            case (R.id.exercise):
                break;

        }

    }

    private void quitActivity() {
//        updateData();
        finish();
    }

    private void updateData() {

    }
}
