package com.limi.andorid.vocabularyassistant.acti;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.limi.andorid.vocabularyassistant.R;
import com.limi.andorid.vocabularyassistant.helper.MySQLiteHandler;
import com.limi.andorid.vocabularyassistant.helper.SessionManager;

import java.util.HashMap;

public class SelectBookActivity extends AppCompatActivity implements View.OnClickListener {

    private MySQLiteHandler db;
    private int currentUserID;
    private SessionManager sessionManager;
    private Button greBtn;
    private Button ietlsBtn;
    private Button toeflBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_book);
        db = new MySQLiteHandler(getApplicationContext());
        HashMap<String, String> userDetails = db.getUserDetails();
        currentUserID = Integer.parseInt(userDetails.get("userID"));
        sessionManager = new SessionManager(getApplicationContext());
        greBtn = (Button) findViewById(R.id.GRE);
        ietlsBtn = (Button) findViewById(R.id.IELTS);
        toeflBtn = (Button) findViewById(R.id.TOEFL);
        greBtn.setOnClickListener(this);
        ietlsBtn.setOnClickListener(this);
        toeflBtn.setOnClickListener(this);

        toeflBtn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    toeflBtn.setBackgroundDrawable(getResources().getDrawable(R.mipmap.categoryt));
                } else {
                    toeflBtn.setBackgroundDrawable(getResources().getDrawable(R.mipmap.categoryte));
                }
            }
        });

        ietlsBtn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ietlsBtn.setBackgroundDrawable(getResources().getDrawable(R.mipmap.categoryi));
                } else {
                    ietlsBtn.setBackgroundDrawable(getResources().getDrawable(R.mipmap.categoryie));
                }
            }
        });

        greBtn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    greBtn.setBackgroundDrawable(getResources().getDrawable(R.mipmap.categoryg));
                } else {
                    greBtn.setBackgroundDrawable(getResources().getDrawable(R.mipmap.categoryge));
                }

            }
        });

        String bookID = sessionManager.getBook(currentUserID);

        if (!bookID.equals("-1")) {
            Intent intent = new Intent(SelectBookActivity.this,
                    MainActivity.class);
            startActivity(intent);
            finish();
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.GRE:
                sessionManager.setKeySelectBook(currentUserID, 0);
                greBtn.setBackgroundDrawable(getResources().getDrawable(R.mipmap.categoryg));
                startActivity();
                break;
            case R.id.TOEFL:
                sessionManager.setKeySelectBook(currentUserID, 1);
                toeflBtn.setBackgroundDrawable(getResources().getDrawable(R.mipmap.categoryt));
                startActivity();
                break;
            case R.id.IELTS:
                sessionManager.setKeySelectBook(currentUserID, 2);
                ietlsBtn.setBackgroundDrawable(getResources().getDrawable(R.mipmap.categoryi));

                startActivity();
                break;
        }

    }

    private void startActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
