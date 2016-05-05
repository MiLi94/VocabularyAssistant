package com.limi.andorid.vocabularyassistant.acti;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.limi.andorid.vocabularyassistant.R;
import com.limi.andorid.vocabularyassistant.helper.MySQLiteHandler;
import com.limi.andorid.vocabularyassistant.helper.WordImportHandler;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    public static int currentUserID;
    ResideMenu resideMenu;
    String titles[] = {"Home", "My Notebook", "Learning Trace", "Message", "Forms", "Settings"};
    int icon[] = {R.mipmap.icon_home, R.mipmap.icon_notebook, R.mipmap.icon_record2, R.mipmap.icon_message, R.mipmap.icon_forum, R.mipmap.icon_settings};
    ResideMenuItem item[] = new ResideMenuItem[titles.length];
    private MySQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button menuBtn = (Button) findViewById(R.id.title_bar_left_menu);
        Button favBtn = (Button) findViewById(R.id.title_bar_right_menu);
        menuBtn.setOnClickListener(this);
        favBtn.setOnClickListener(this);
        try {
            InputStream inputStream = getAssets().open("threek.xml");
            WordImportHandler.getDataFromXml(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        db = new MySQLiteHandler(getApplicationContext());
        HashMap<String, String> userDetails = db.getUserDetails();
        currentUserID = Integer.parseInt(userDetails.get("userID"));
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.mipmap.menu_background);
        resideMenu.attachToActivity(this);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        // create menu items;
        for (int i = 0; i < titles.length; i++) {
            item[i] = new ResideMenuItem(this, icon[i], titles[i]);
            item[i].setOnClickListener(this);
            resideMenu.addMenuItem(item[i], ResideMenu.DIRECTION_LEFT); // or  ResideMenu.DIRECTION_RIGHT
        }
        if (savedInstanceState == null)
            changeFragment(new HomeFragment());

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_left_menu:
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
                break;
            case R.id.title_bar_right_menu:
                changeFragment(new FavouriteWordFragment());
                break;

        }
        if (v == item[0]) {
            TextView textView = (TextView) findViewById(R.id.title_main);
            textView.setText("Vocabulary Assistant");
            changeFragment(new HomeFragment());
            resideMenu.closeMenu();
        }
        if (v == item[1]) {
            TextView textView = (TextView) findViewById(R.id.title_main);
            textView.setText("My Notebook");
            changeFragment(new MyNoteBookFragment());
            resideMenu.closeMenu();
        }
        if (v == item[5]) {
            TextView textView = (TextView) findViewById(R.id.title_main);
            textView.setText("Setting");
            changeFragment(new SettingFragment());
            resideMenu.closeMenu();
        }


    }

    @Override
    public void onBackPressed() {
        // Disable going back to the LoginActivity
        moveTaskToBack(true);
    }


    public ResideMenu getResideMenu() {
        return resideMenu;
    }

    private void changeFragment(Fragment targetFragment) {
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
