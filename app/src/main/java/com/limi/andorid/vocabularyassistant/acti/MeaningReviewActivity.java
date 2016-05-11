package com.limi.andorid.vocabularyassistant.acti;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.limi.andorid.vocabularyassistant.R;
import com.limi.andorid.vocabularyassistant.data.Word;
import com.limi.andorid.vocabularyassistant.helper.WordImportHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class MeaningReviewActivity extends AppCompatActivity implements View.OnClickListener {

    private int startID;
    private int endID;
    private int length;
    private int currentID;
    private TextView meaningView;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    //    private int[] randomArray = new int[4];
    private HashSet<Integer> randomSet;
    private ArrayList<Integer> choice;
    private Button nextButton;
    private ArrayList<Word> testWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meaning_review);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        startID = bundle.getInt("StartID");
        endID = bundle.getInt("EndID");

        length = endID - startID + 1;

        testWords = new ArrayList<Word>();

        for (int i = startID; i <= endID; i++) {
            testWords.add(WordImportHandler.systemWordBaseArrayList.get(i));
        }
        Collections.shuffle(testWords);


        currentID = 0;

        meaningView = (TextView) findViewById(R.id.meaningWord);

        button1 = (Button) findViewById(R.id.meaning1);
        button2 = (Button) findViewById(R.id.meaning2);
        button3 = (Button) findViewById(R.id.meaning3);
        button4 = (Button) findViewById(R.id.meaning4);
        button5 = (Button) findViewById(R.id.meaning5);
        nextButton = (Button) findViewById(R.id.next_button_m);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        nextButton.setOnClickListener(this);

        randomSet = new HashSet<>();
        randomSet.add(currentID);
        getRandomNumber();
        choice = new ArrayList<>(randomSet);
//        choice.add(testWords.get(currentID).getId());
        Collections.shuffle(choice);


        updateView();

    }

    private void updateView() {
        Word currentWord = testWords.get(currentID);
        String meaning = currentWord.getTrans();
        String showMeaning = "";
        if (meaning.contains("\n")) {
            String[] meanings = meaning.split("\n");
            if (meanings[0].contains("1")) {
                showMeaning = meanings[0].substring(2);
            }
        } else {
            showMeaning = meaning;
        }
        meaningView.setText(showMeaning);


        button1.setText(testWords.get(choice.get(0)).getWord());
        button2.setText(testWords.get(choice.get(1)).getWord());
        button3.setText(testWords.get(choice.get(2)).getWord());
        button4.setText(testWords.get(choice.get(3)).getWord());
    }

    private void getRandomNumber() {
        while (randomSet.size() < 4) {
            int randomNumber = (int) Math.round(Math.random() * length - 1);
            randomSet.add(randomNumber);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.meaning1:

            case R.id.meaning2:
            case R.id.meaning3:
            case R.id.meaning4:
            case R.id.meaning5:
            case R.id.next_button_m:
        }

    }
}
