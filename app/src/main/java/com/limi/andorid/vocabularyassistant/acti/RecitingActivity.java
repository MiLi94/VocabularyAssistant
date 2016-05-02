package com.limi.andorid.vocabularyassistant.acti;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.limi.andorid.vocabularyassistant.R;
import com.limi.andorid.vocabularyassistant.helper.UserWord;
import com.limi.andorid.vocabularyassistant.helper.Word;
import com.limi.andorid.vocabularyassistant.helper.WordImportHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class RecitingActivity extends AppCompatActivity implements View.OnClickListener {

    static ArrayList<UserWord> userWordArrayList = new ArrayList<>();
    TextView wordTextView;
    TextView meaningTextView;
    TextView phoneticTextView;
    Button lastButton;
    Button favourite;
    Button nextButton;
    UserWord userWord;
    int startID;
    int currentID;
    int userID;
    int endID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciting);
        wordTextView = (TextView) findViewById(R.id.word);
        meaningTextView = (TextView) findViewById(R.id.meaning);
        phoneticTextView = (TextView) findViewById(R.id.phonetic);
        lastButton = (Button) findViewById(R.id.last_button);
        favourite = (Button) findViewById(R.id.fav);
        nextButton = (Button) findViewById(R.id.next_button);
//        nextButton.setVisibility();
        try {
            InputStream inputStream = getAssets().open("threek.xml");
            WordImportHandler.getDataFromXml(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        lastButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        favourite.setOnClickListener(this);
        startID = 0;
        endID = 10;
        userID = 1;
        init();


    }

    private void init() {
        currentID = startID;
        Word word = WordImportHandler.threeKArrayList.get(currentID);
        userWord = new UserWord(word.getID(), userID, word.getWordBase());
        updateView(word);
    }

    public Word getNextWord() {
//        String isRan=getPref(iWord.PREFS_NAME,iWord.IS_RANDOM,"true");
//        if (isRan=="true") return iWord.wordList.get(random.nextInt(iWord.wordList.size()));
//        else  return iWord.wordList.get((wordId++)%iWord.wordList.size());
        if (currentID >= endID) {
            //button set finish or review
        } else {
            currentID++;
        }

        Word word = WordImportHandler.threeKArrayList.get(currentID);
        wordTextView.setText(word.getWord());
        phoneticTextView.setText(word.getPhonetic());
        meaningTextView.setText(word.getTrans());
        userWord = new UserWord(word.getID(), userID, word.getWordBase());
        return word;
    }

    public Word getLastWord() {
//        String isRan=getPref(iWord.PREFS_NAME,iWord.IS_RANDOM,"true");
//        if (isRan=="true") return iWord.wordList.get(random.nextInt(iWord.wordList.size()));
//        else  return iWord.wordList.get((wordId++)%iWord.wordList.size());

        if (currentID <= startID) {

        } else {
            currentID--;
        }
        Word word = WordImportHandler.threeKArrayList.get(currentID);
        wordTextView.setText(word.getWord());
        phoneticTextView.setText(word.getPhonetic());
        meaningTextView.setText(word.getTrans());
        userWord = new UserWord(word.getID(), userID, word.getWordBase());
        return word;
    }

    public void updateView(Word word) {
        wordTextView.setText(word.getWord());
        phoneticTextView.setText(word.getPhonetic());
        meaningTextView.setText(word.getTrans());

    }

    @Override
    public void onClick(View v) {
        Word currentWord = null;
        switch (v.getId()) {
            case R.id.last_button:
                currentWord = getLastWord();
                break;
            case R.id.next_button:
                currentWord = getNextWord();
                break;
            case R.id.fav:

                userWord.setFavourite();
                break;
        }
    }
}


