package com.limi.andorid.vocabularyassistant.acti;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.limi.andorid.vocabularyassistant.R;
import com.limi.andorid.vocabularyassistant.app.AppConfig;
import com.limi.andorid.vocabularyassistant.app.AppController;
import com.limi.andorid.vocabularyassistant.data.Record;
import com.limi.andorid.vocabularyassistant.data.UserAccount;
import com.limi.andorid.vocabularyassistant.data.UserWord;
import com.limi.andorid.vocabularyassistant.data.Word;
import com.limi.andorid.vocabularyassistant.helper.MySQLiteHandler;
import com.limi.andorid.vocabularyassistant.helper.WordImportHandler;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;


public class MainActivity extends FragmentActivity implements View.OnClickListener {
    public static int currentUserID;
    public static int wordStartID;
    public static int wordEndID;
    public static int bookID;
    public static ArrayList<Record> records = new ArrayList<>();
    String titles[] = {"Home", "My Notebook", "Learning Trace", "Message", "Top Five Ranking", "Settings"};
    int icon[] = {R.mipmap.icon_home, R.mipmap.icon_notebook, R.mipmap.icon_record2, R.mipmap.icon_message, R.mipmap.icon_forum, R.mipmap.icon_settings};
    ResideMenuItem item[] = new ResideMenuItem[titles.length];
    private ResideMenu resideMenu;
    private MySQLiteHandler db;
    private Bundle SsavedInstanceState = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bookID = 0;


        Button menuBtn = (Button) findViewById(R.id.title_bar_left_menu);
//        Button favBtn = (Button) findViewById(R.id.title_bar_right_menu);
        menuBtn.setOnClickListener(this);
//        favBtn.setOnClickListener(this);
        try {
            InputStream inputStream1 = getAssets().open("threek.xml");
            WordImportHandler.getDataFromXml(inputStream1, "GRE threek Words");
            inputStream1.close();

            InputStream inputStream2 = getAssets().open("toefl.xml");
            WordImportHandler.getDataFromXml(inputStream2, "TOEFL");
            inputStream2.close();

            Toast.makeText(getApplicationContext(), WordImportHandler.systemWordBaseArrayList.get(Word.getLastID() - 1).toString(), Toast.LENGTH_LONG).show();

            InputStream inputStream3 = getAssets().open("ietls.xml");
            WordImportHandler.getDataFromXml(inputStream3, "IETLS");
            inputStream3.close();
            Word.setLastID();

        } catch (IOException e) {
            e.printStackTrace();
        }


        ArrayList<Word> words = WordImportHandler.systemWordBaseArrayList;

        switch (bookID) {

            case 0:
                wordStartID = 0;
                wordEndID = Word.idWordBase.get("GRE threek Words") - 1;
                break;
            case 1:
                wordStartID = Word.idWordBase.get("GRE threek Words");
                wordEndID = Word.idWordBase.get("TOEFL") - 1;
                break;
            case 2:
                wordStartID = Word.idWordBase.get("TOEFL");
                wordEndID = Word.idWordBase.get("IETLS") - 1;
                break;
        }

        Log.d("Start ID", String.valueOf(wordStartID));
        Log.d("End ID", String.valueOf(wordEndID));
        db = new MySQLiteHandler(getApplicationContext());
        HashMap<String, String> userDetails = db.getUserDetails();
        currentUserID = Integer.parseInt(userDetails.get("userID"));

//       int frequency= db.getFrequency("2016-05-12",1);
//       MySQLiteHandler database = new MySQLiteHandler(getApplicationContext());
        records = getTopFive();
//        System.out.println(records);


        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.mipmap.menu_background);
        resideMenu.attachToActivity(this);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        // create menu items;
        for (int i = 0; i < titles.length; i++) {
            item[i] = new ResideMenuItem(this, icon[i], titles[i]);
            item[i].setOnClickListener(this);
            resideMenu.addMenuItem(item[i], ResideMenu.DIRECTION_LEFT);
        }

        requestDataFromDB();
        getTop5();
        if (savedInstanceState == null)
            changeFragment(new HomeFragment());
        else {
            SsavedInstanceState = savedInstanceState;
        }


//        updateToDatabase();
    }

    private ArrayList<Record> getTopFive() {
        ArrayList<Record> records = new ArrayList<>();


        LinkedHashSet<Integer> userID = db.getAllUserID();
        // return user
        Log.d("MMMM", "Fetching users  " + userID.toString());


        for (int user : userID) {
            String sUserID = String.valueOf(user);
            String sDateFormat = "yyyy-MM-dd";
            DateFormat dateFormat = new SimpleDateFormat(sDateFormat, Locale.ENGLISH);
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, -0);
            date = calendar.getTime();


            Date yes = new Date();
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(yes);
            calendar1.add(Calendar.DAY_OF_MONTH, -1);
            yes = calendar1.getTime();

            String sDate = dateFormat.format(date);
            String sYes = dateFormat.format(yes);

            int count_today = 0;
            int cout_yes = 0;

            count_today = db.getFrequency(sDate, user);
            cout_yes = db.getFrequency(sYes, user);

            Record record = new Record(user, count_today, cout_yes);
            records.add(record);

        }


        Comparator<Record> comparator_today = new Comparator<Record>() {
            @Override
            public int compare(Record lhs, Record rhs) {
                return rhs.getThisCount() - lhs.getThisCount();
            }
        };
        Comparator<Record> comparator_yest = new Comparator<Record>() {
            @Override
            public int compare(Record lhs, Record rhs) {
                return lhs.getLastCount() - rhs.getLastRank();
            }
        };


        Collections.sort(records, comparator_yest);

        for (int i = 0; i < records.size(); i++) {
            Record record = records.get(i);
            int j = i + 1;
            record.setLastRank(j);
        }

        Collections.sort(records, comparator_today);

        for (
                int i = 0;
                i < records.size(); i++)

        {
            Record record = records.get(i);
            int j = i + 1;
            record.setTodayRank(j);
        }

        return records;

    }

    private void downloadDataFromDB(final int uploadStartID) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GETWORD, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("downloadDataFromDB", "downloadDataFromDB " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        JSONArray jsonArray = jObj.getJSONArray("userword");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject userWord = jsonArray.getJSONObject(i);
                            int wordID = userWord.getInt("wordID");
                            int userID = userWord.getInt("userID");
                            String createDate = userWord.getString("created_at");
                            String s_isFavourite = userWord.getString("is_favourite");
                            boolean isFavourite = false;
                            if (s_isFavourite.equals("1")) {
                                isFavourite = true;
                            }
                            int wrongTime = userWord.getInt("wrong_time");
                            String wordBase = userWord.getString("word_base");
                            UserWord userWord1 = new UserWord(wordID, userID, createDate, isFavourite, wrongTime, wordBase);
                            db.addUserWord(userWord1);
                        }
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Update", "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("startid", String.valueOf(uploadStartID));
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, "Download from DB");
    }

    private void requestDataFromDB() {
        final ArrayList<UserWord> allUserWord = db.getAllUserWordData();


        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_COUNT, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d("COUNT", "COUNT Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        ArrayList<UserWord> newWords = new ArrayList<>();
                        Integer count = jObj.getInt("count");
                        if (count > allUserWord.size()) {
                            downloadDataFromDB(allUserWord.size());
                        }

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

//                changeFragment(new HomeFragment());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Download", "Download Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {


        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, "Download");

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
        if (v == item[2]) {
            TextView textView = (TextView) findViewById(R.id.title_main);
            textView.setText("Learning Trace");
            changeFragment(new LearningTraceFragment());
            resideMenu.closeMenu();
        }
        if (v == item[3]) {
            TextView textView = (TextView) findViewById(R.id.title_main);
            textView.setText("Message");
            changeFragment(new SettingFragment());
            resideMenu.closeMenu();
        }
        if (v == item[4]) {
            TextView textView = (TextView) findViewById(R.id.title_main);
            textView.setText("Top 5 Ranking");
            changeFragment(new RankFragment());
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

        updateToDatabase();
        new AlertDialog.Builder(this).setTitle("CONFIRM EXIT")
                .setMessage("Are you sure to exit?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                moveTaskToBack(true);
                finish();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }


//    private void finishaaa() {
//        super.finish();
//    }

    private void updateToDatabase() {

        final ArrayList<UserWord> allUserWord = db.getAllUserWordData();


        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_COUNT, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d("COUNT", "COUNT Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        ArrayList<UserWord> newWords = new ArrayList<>();
                        Integer count = jObj.getInt("count");
                        System.out.println(count);
                        if (allUserWord.size() > count) {
                            for (int i = count; i < allUserWord.size(); i++) {
                                newWords.add(allUserWord.get(i));
                            }
                            requestToDatabase(newWords);

                        }

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
//                    finishaaa();
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Update", "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, "Update");
    }

    private void requestToDatabase(ArrayList newList) {
        Gson gson = new Gson();
        final String out = gson.toJson(newList);
//        System.out.println(out);
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_UPDATE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Update", "Login Response: " + response.toString());

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Update", "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("update", out);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, "Update");

    }


    private void getTop5() {

        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_GETUSER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Update", "Login Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        JSONArray jsonArray = jObj.getJSONArray("users");
                        ArrayList<UserAccount> userAccounts = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject userWord = jsonArray.getJSONObject(i);
                            int userID = userWord.getInt("unique_id");
                            String name = userWord.getString("name");
                            String email = userWord.getString("email");
                            UserAccount userAccount = new UserAccount(userID, email, name);
                            userAccounts.add(userAccount);
                        }

                        records = getTopFive();

                        for (Record r : records) {
                            for (UserAccount u : userAccounts) {
                                if (r.getUserID() == u.getUserId()) {
                                    r.setUserName(u.getName());
                                }
                            }
                        }


                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
//                    finishaaa();
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("GET USER", "GET USER Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, "GETUSER");

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
