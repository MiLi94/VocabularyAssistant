package com.limi.andorid.vocabularyassistant.acti;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.limi.andorid.vocabularyassistant.R;
import com.limi.andorid.vocabularyassistant.data.UserWord;
import com.limi.andorid.vocabularyassistant.data.Word;
import com.limi.andorid.vocabularyassistant.helper.MySQLiteHandler;
import com.limi.andorid.vocabularyassistant.helper.WordImportHandler;

import java.util.ArrayList;

public class RecitingActivity extends AppCompatActivity implements View.OnClickListener {

    private MySQLiteHandler db;
    private TextView wordTextView;
    private TextView meaningTextView;
    private TextView phoneticTextView;
    private Button returnButton;
    private Button lastButton;
    private Button favourite;
    private Button nextButton;
    private UserWord userWord;
    private int nextStart;
    private int unit;
    private int startID;
    private int currentID;
    private int userID;
    private int endID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciting);

        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            nextStart = bundle.getInt("nextStartID", -1);
        } catch (Exception e) {
            nextStart = -1;

        }
        db = new MySQLiteHandler(getApplicationContext());

        wordTextView = (TextView) findViewById(R.id.word);
        meaningTextView = (TextView) findViewById(R.id.meaning);
        phoneticTextView = (TextView) findViewById(R.id.phonetic);

        returnButton = (Button) findViewById(R.id.title_bar_left_menu);
        lastButton = (Button) findViewById(R.id.last_button);
        favourite = (Button) findViewById(R.id.fav);
        nextButton = (Button) findViewById(R.id.next_button);

        lastButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        favourite.setOnClickListener(this);
        returnButton.setOnClickListener(this);

        unit = 0;
        if (nextStart == -1) {
            startID = 0;
        } else {
            startID = nextStart;
        }

        userID = MainActivity.currentUserID;
        init();


    }

    private void init() {

        if (nextStart == -1) {
            ArrayList<UserWord> words1 = db.getUserWordData(userID);
            if (UserWord.userWordHashMap.size() == 0) {

                for (UserWord word : words1) {
                    UserWord.userWordHashMap.put(word.getWordID(), word);
                }
            }
            if (words1.size() == 0) {
                startID = 0;
            } else {
                startID = words1.get(words1.size() - 1).getWordID() + 1;

            }

        }
        endID = startID + 9;
        currentID = startID;

        Toast.makeText(getApplicationContext(), String.valueOf(currentID), Toast.LENGTH_SHORT).show();

        Word word = WordImportHandler.systemWordBaseArrayList.get(currentID);
        if (UserWord.userWordHashMap.containsKey(currentID))
            userWord = UserWord.userWordHashMap.get(currentID);
        else {
            userWord = new UserWord(word.getId(), userID, word.getWordBase());
            UserWord.userWordHashMap.put(word.getId(), userWord);

        }
        if (!db.isWordExist(userWord)) {
            db.addUserWord(userWord);
        }
        updateView(word);
    }


    public Word getNextWord() {
        if (currentID >= endID) {
            //button set finish or review
            Intent intent = new Intent(RecitingActivity.this, SummaryActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("startID", startID);
            bundle.putInt("endID", endID);
//                bundle.putIntegerArrayList("list", wordFav);
            intent.putExtras(bundle);
            this.startActivity(intent);

            finishReciting();
        } else {
            currentID++;
        }

        Word word = WordImportHandler.systemWordBaseArrayList.get(currentID);
        if (UserWord.userWordHashMap.containsKey(currentID))
            userWord = UserWord.userWordHashMap.get(currentID);
        else {
            userWord = new UserWord(word.getId(), userID, word.getWordBase());
            UserWord.userWordHashMap.put(word.getId(), userWord);

        }
        if (!db.isWordExist(userWord)) {
            db.addUserWord(userWord);
        }
        updateView(word);

        return word;
    }

    public Word getLastWord() {

        if (currentID <= startID) {

        } else {
            currentID--;
        }
        Word word = WordImportHandler.systemWordBaseArrayList.get(currentID);
        if (UserWord.userWordHashMap.containsKey(currentID))
            userWord = UserWord.userWordHashMap.get(currentID);
        else {
            userWord = new UserWord(word.getId(), userID, word.getWordBase());
            UserWord.userWordHashMap.put(word.getId(), userWord);

        }
        if (!db.isWordExist(userWord)) {
            db.addUserWord(userWord);
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
                db.changeFav(userWord);
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


