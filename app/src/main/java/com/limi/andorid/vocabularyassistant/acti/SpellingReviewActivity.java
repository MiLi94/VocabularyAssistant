package com.limi.andorid.vocabularyassistant.acti;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.limi.andorid.vocabularyassistant.R;
import com.limi.andorid.vocabularyassistant.data.UserWord;
import com.limi.andorid.vocabularyassistant.data.Word;
import com.limi.andorid.vocabularyassistant.helper.MySQLiteHandler;
import com.limi.andorid.vocabularyassistant.helper.WordImportHandler;

import java.util.ArrayList;
import java.util.Collections;

public class SpellingReviewActivity extends AppCompatActivity implements View.OnClickListener {

    private int correctID;
    private int startID;
    private int endID;
    private int length;
    private int currentID;
    private TextView meaningView;
    private TextView hintView;
    private EditText editText;
    private Button showAnswer;
    private Button nextButton;
    private Button submitBtn;
    private Button quit_btn;
    private ArrayList<Word> testWords;
    private boolean isWrong = false;
    private Word currentWord;

    private MySQLiteHandler mySQLiteHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelling_review);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        quit_btn = (Button) findViewById(R.id.test_quit);

        startID = bundle.getInt("StartID");
        endID = bundle.getInt("EndID");

        length = endID - startID + 1;

        testWords = new ArrayList<Word>();

        for (int i = startID; i <= endID; i++) {
            testWords.add(WordImportHandler.systemWordBaseArrayList.get(i));
        }
        Collections.shuffle(testWords);


        currentID = 0;

        meaningView = (TextView) findViewById(R.id.meaningSpell);
        hintView = (TextView) findViewById(R.id.spellHint);
        editText = (EditText) findViewById(R.id.spellText);
        nextButton = (Button) findViewById(R.id.next_button_r);
        showAnswer = (Button) findViewById(R.id.spellShowAns);
        submitBtn = (Button) findViewById(R.id.submit_btn);


        assert submitBtn != null;
        submitBtn.setOnClickListener(this);

        nextButton.setOnClickListener(this);
        showAnswer.setOnClickListener(this);
        quit_btn.setOnClickListener(this);

        mySQLiteHandler = new MySQLiteHandler(getApplicationContext());

        updateView();
    }

    private void updateView() {

        isWrong = false;
        nextButton.setVisibility(View.INVISIBLE);


        currentWord = testWords.get(currentID);
        String meaning = currentWord.getTrans();
        String showMeaning = "";
        if (meaning.contains("\n")) {
            String[] meanings = meaning.split("\n");
            if (meanings[0].contains("1")) {
                showMeaning = meanings[0].substring(3);
            }
        } else {
            if (meaning.contains("1")) {
                showMeaning = meaning.substring(3);
            } else {
                showMeaning = meaning;
            }
        }
        meaningView.setText(showMeaning);
        int wordLength = currentWord.getWord().length();
        String hintText = "Word length is " + wordLength;
        hintView.setText(hintText);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_button_r:
                showNextWordView();
                break;
            case R.id.spellShowAns:
                editText.setText(currentWord.getWord());
                nextButton.setVisibility(View.VISIBLE);
                break;

            case R.id.submit_btn:
                editText.setTextColor(getResources().getColor(R.color.red));
                if (editText.getText().equals(currentWord.getWord())) {
                    nextButton.setVisibility(View.VISIBLE);
                } else {
                    editText.setText(currentWord.getWord());
                    nextButton.setVisibility(View.VISIBLE);
                    isWrong = true;
                }
                editText.setText(currentWord.getWord());
                nextButton.setVisibility(View.VISIBLE);
                break;
            case R.id.test_quit:
                Intent intent2 = new Intent(SpellingReviewActivity.this, SummaryActivity.class);
                setResult(0, intent2);
                finish();
                break;


        }
    }

    private void showNextWordView() {
        if (isWrong) {
            UserWord.userWordHashMap.get(testWords.get(correctID).getId()).inWrongTime();
            mySQLiteHandler.increaseWrongTime(UserWord.userWordHashMap.get(testWords.get(correctID).getId()));
        }
        if (currentID + startID < endID) {
            currentID++;
            updateView();
        } else {
            Intent intent2 = new Intent(SpellingReviewActivity.this, SummaryActivity.class);
            setResult(0, intent2);
            finish();
        }
        editText.setText("");
        editText.setTextColor(getResources().getColor(R.color.black));
    }
}
