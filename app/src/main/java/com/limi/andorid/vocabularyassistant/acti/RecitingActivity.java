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

public class RecitingActivity extends AppCompatActivity implements View.OnClickListener {

    TextView wordTextView;
    TextView meaningTextView;
    TextView phoneticTextView;
    Button returnButton;
    Button lastButton;
    Button favourite;
    Button nextButton;
    UserWord userWord;
    int unit;
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
        returnButton = (Button) findViewById(R.id.title_bar_left_menu);
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
        returnButton.setOnClickListener(this);
        unit = 0;
        startID = unit * 10;
        endID = unit * 10 + 9;
        userID = 1;
        init();


    }

    private void init() {
        currentID = startID;
        Word word = WordImportHandler.threeKArrayList.get(currentID);
        if (UserWord.userWordHashMap.containsKey(currentID))
            userWord = UserWord.userWordHashMap.get(currentID);
        else {
            userWord = new UserWord(word.getID(), userID, word.getWordBase());
            UserWord.userWordHashMap.put(word.getID(), userWord);

        }
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
        if (UserWord.userWordHashMap.containsKey(currentID))
            userWord = UserWord.userWordHashMap.get(currentID);
        else {
            userWord = new UserWord(word.getID(), userID, word.getWordBase());
            UserWord.userWordHashMap.put(word.getID(), userWord);

        }
        updateView(word);

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
        if (UserWord.userWordHashMap.containsKey(currentID))
            userWord = UserWord.userWordHashMap.get(currentID);
        else {
            userWord = new UserWord(word.getID(), userID, word.getWordBase());
            UserWord.userWordHashMap.put(word.getID(), userWord);

        }


        updateView(word);

        return word;
    }

    public void updateView(Word word) {
        wordTextView.setText(word.getWord());
        phoneticTextView.setText(word.getPhonetic());
        meaningTextView.setText(word.getTrans());
        if (userWord.isFavourite()) {
//            userWord.setFavourite();
            favourite.setBackgroundDrawable(getResources().getDrawable(R.mipmap.star2));

        } else {
//            userWord.setFavourite();
            favourite.setBackgroundDrawable(getResources().getDrawable(R.mipmap.star3));

        }

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
                userWord = UserWord.userWordHashMap.get(currentID);
                if (userWord.isFavourite()) {
                    userWord.setIsFavourite(false);
                    favourite.setBackgroundDrawable(getResources().getDrawable(R.mipmap.star3));
                } else {
                    userWord.setIsFavourite(true);
                    favourite.setBackgroundDrawable(getResources().getDrawable(R.mipmap.star2));
                }
//                favourite.setBackgroundDrawable(getResources().getDrawable(R.mipmap.star2));

                break;
            case R.id.title_bar_left_menu:
                finishReciting();
                break;
        }
    }

    private void finishReciting() {


        finish();
    }
}


