package com.limi.andorid.vocabularyassistant.acti;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.limi.andorid.vocabularyassistant.R;
import com.limi.andorid.vocabularyassistant.data.UserWord;
import com.limi.andorid.vocabularyassistant.data.Word;
import com.limi.andorid.vocabularyassistant.helper.MySQLiteHandler;
import com.limi.andorid.vocabularyassistant.helper.WordImportHandler;

public class ViewFavWord extends AppCompatActivity implements View.OnClickListener {

    TextView wordTextView;
    TextView meaningTextView;
    TextView phoneticTextView;
    Button returnButton;
    Button lastButton;
    Button favourite;
    Button nextButton;
    TextView titleTextView;
    //    ArrayList<Integer> integers = new ArrayList<>();
    int startIndex;
    int currentIndex;
    int endIndex;
    int wordCurrentID;
    private MySQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciting);
        db = new MySQLiteHandler(getApplicationContext());
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String strContentString = bundle.getString("ID");
//        integers = bundle.getIntegerArrayList("list");

        assert strContentString != null;
//        Toast.makeText(getApplicationContext(), strContentString, Toast.LENGTH_SHORT).show();
        wordCurrentID = Integer.parseInt(strContentString);
//        Toast.makeText(getApplicationContext(), String.valueOf(currentIndex), Toast.LENGTH_SHORT).show();


        wordTextView = (TextView) findViewById(R.id.word);
        meaningTextView = (TextView) findViewById(R.id.meaning);
        phoneticTextView = (TextView) findViewById(R.id.phonetic);
        returnButton = (Button) findViewById(R.id.title_bar_left_menu);
        nextButton = (Button) findViewById(R.id.next_button);
        lastButton = (Button) findViewById(R.id.last_button);
        favourite = (Button) findViewById(R.id.fav);
        titleTextView = (TextView) findViewById(R.id.title_rec);
        titleTextView.setText("View Meaning");
        favourite.setVisibility(View.INVISIBLE);
        nextButton.setOnClickListener(this);
        returnButton.setOnClickListener(this);
        lastButton.setOnClickListener(this);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar_recite);
        assert progressBar != null;
        progressBar.setVisibility(View.INVISIBLE);
        startIndex = 0;
        endIndex = FavouriteWordFragment.wordFav.size() - 1;
        currentIndex = findindex(wordCurrentID);

        initView(wordCurrentID);


    }

    private int findindex(int wordID) {
        for (int i = 0; i < FavouriteWordFragment.wordFav.size(); i++) {
            if (FavouriteWordFragment.wordFav.get(i) == wordID) {
                return i;
            }
        }
        return -1;
    }

    private void initView(int wordCurrentID) {
        Word word = WordImportHandler.systemWordBaseArrayList.get(wordCurrentID);
        updateView(word);
    }

    private void updateView(Word word) {
        wordTextView.setText(word.getWord());
        phoneticTextView.setText(word.getPhonetic());
        meaningTextView.setText(word.getTrans());

    }

    private void setNextButton() {
        if (currentIndex >= endIndex) {

        } else {
            currentIndex++;
            int id = FavouriteWordFragment.wordFav.get(currentIndex);
            Word word = WordImportHandler.systemWordBaseArrayList.get(id);
            updateView(word);

        }

    }

    private void setLastButtonButton() {
        if (currentIndex <= startIndex) {

        } else {
            currentIndex--;
            int id = FavouriteWordFragment.wordFav.get(currentIndex);
            Word word = WordImportHandler.systemWordBaseArrayList.get(id);
            updateView(word);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.last_button:
                setLastButtonButton();
                break;
            case R.id.next_button:
                setNextButton();
                break;
            case R.id.title_bar_left_menu:
                finishReciting();
                break;
            case R.id.fav:
                UserWord userWord = UserWord.userWordHashMap.get(wordCurrentID);
                if (userWord.isFavourite()) {
                    userWord.setIsFavourite(false);
                    favourite.setBackgroundDrawable(getResources().getDrawable(R.mipmap.star3));
                } else {
                    userWord.setIsFavourite(true);
                    favourite.setBackgroundDrawable(getResources().getDrawable(R.mipmap.star2));
                }
                db.changeFav(userWord);
                break;


        }
    }

    private void finishReciting() {
        finish();
    }
}
