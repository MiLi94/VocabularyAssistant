package com.limi.andorid.vocabularyassistant.acti;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.limi.andorid.vocabularyassistant.R;
import com.limi.andorid.vocabularyassistant.helper.UserWord;
import com.limi.andorid.vocabularyassistant.helper.Word;
import com.limi.andorid.vocabularyassistant.helper.WordImportHandler;

public class ViewWord extends AppCompatActivity implements View.OnClickListener {

    TextView wordTextView;
    TextView meaningTextView;
    TextView phoneticTextView;
    Button returnButton;
    Button lastButton;
    Button favourite;
    Button nextButton;
    UserWord userWord;
    int startID;
    int currentID;
    int endID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciting);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String strContentString = bundle.getString("ID");
        assert strContentString != null;
        Toast.makeText(getApplicationContext(), strContentString, Toast.LENGTH_SHORT).show();
        currentID = Integer.parseInt(strContentString);
        Toast.makeText(getApplicationContext(), String.valueOf(currentID), Toast.LENGTH_SHORT).show();


        wordTextView = (TextView) findViewById(R.id.word);
        meaningTextView = (TextView) findViewById(R.id.meaning);
        phoneticTextView = (TextView) findViewById(R.id.phonetic);
        returnButton = (Button) findViewById(R.id.title_bar_left_menu);
        lastButton = (Button) findViewById(R.id.last_button);
        favourite = (Button) findViewById(R.id.fav);
        returnButton.setOnClickListener(this);
        assert favourite != null;
        favourite.setVisibility(View.INVISIBLE);

        initView();


    }

    private void initView() {
//        currentID = startID;
        Word word = WordImportHandler.threeKArrayList.get(currentID);
        updateView(word);
    }

    private void updateView(Word word) {
        wordTextView.setText(word.getWord());
        phoneticTextView.setText(word.getPhonetic());
        meaningTextView.setText(word.getTrans());
    }

    private void setNextButton() {

    }

    private void setLastButtonButton() {

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


        }
    }

    private void finishReciting() {
        finish();
    }
}
