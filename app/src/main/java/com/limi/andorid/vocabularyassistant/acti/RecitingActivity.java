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
    Button nextButton;
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
        nextButton = (Button) findViewById(R.id.next_button);
//        nextButton.setVisibility();
        try {
            InputStream inputStream = getAssets().open("threek.xml");
            WordImportHandler.getDataFromXml(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Word word = WordImportHandler.threeKArrayList.get(11);
        lastButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        startID = 0;
        endID = 10;
        userID = 1;
        init();


    }

    private void init() {
        currentID = startID;
        updateView(WordImportHandler.threeKArrayList.get(currentID));
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
        UserWord userWord = new UserWord(userID, word.getID(), word.getWordBase());
        userWordArrayList.add(userWord);
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
        UserWord userWord = new UserWord(userID, word.getID(), word.getWordBase());
        userWordArrayList.add(userWord);
        return word;
    }

    public void updateView(Word word) {
        wordTextView.setText(word.getWord());
        phoneticTextView.setText(word.getPhonetic());
        meaningTextView.setText(word.getTrans());

    }

    @Override
    public void onClick(View v) {
        Word currentWord;
        switch (v.getId()) {
            case R.id.last_button:
                currentWord = getLastWord();

                break;
            case R.id.next_button:
                currentWord = getNextWord();
                break;
        }
    }
}


