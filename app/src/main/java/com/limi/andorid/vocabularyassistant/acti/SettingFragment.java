package com.limi.andorid.vocabularyassistant.acti;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.limi.andorid.vocabularyassistant.R;
import com.limi.andorid.vocabularyassistant.data.UserWord;
import com.limi.andorid.vocabularyassistant.dialog.BookPicker;
import com.limi.andorid.vocabularyassistant.helper.MySQLiteHandler;
import com.limi.andorid.vocabularyassistant.helper.SessionManager;

import java.util.ArrayList;

public class SettingFragment extends Fragment implements View.OnClickListener {
    private static SettingFragment instance;
    private View parentView;
    private MainActivity mainActivity;
    private ListView list;
    private MySQLiteHandler db;
    private SessionManager session;


    public static SettingFragment getInstance() {
        if (instance == null) {
            synchronized (SettingFragment.class) {
                if (instance == null) instance = new SettingFragment();
            }
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        parentView = inflater.inflate(R.layout.fragment_setting, container, false);
        list = (ListView) parentView.findViewById(R.id.listView);
        Button logout_button = (Button) parentView.findViewById(R.id.btn_logout);
        logout_button.setOnClickListener(this);
        db = new MySQLiteHandler(getActivity());

        // session manager
        session = new SessionManager(getActivity());
        initView();


        return parentView;
    }

    private void initView() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                getCalendarData());
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getActivity(), "Clicked item!", Toast.LENGTH_LONG).show();
                switch (i) {
                    case 0:
                        Intent intent = new Intent(getContext(), AccountInfoActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        new BookDialog(getContext(), MainActivity.bookID).show();
                        break;

                    case 2:
                        new AlertDialog.Builder(getActivity()).setTitle("Notification")
                                .setSingleChoiceItems(new String[]{"Open", "Close"}, 0, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0:
                                                mainActivity.setNotification(true);
                                                Toast.makeText(getActivity(), "Notification is open", Toast.LENGTH_SHORT).show();
                                                break;
                                            case 1:
                                                mainActivity.setNotification(false);
                                                Toast.makeText(getActivity(), "Notification is closed", Toast.LENGTH_SHORT).show();
                                                break;
                                        }
                                        dialog.dismiss();
                                    }
                                })
                                .setNegativeButton("CANCEL", null).show();
                        break;
                    case 3:
                        Intent intent2 = new Intent(getContext(), AboutUsActivity.class);
                        startActivity(intent2);
                        break;

                }

            }
        });
    }


    private ArrayList<String> getCalendarData() {
        ArrayList<String> wordList = new ArrayList<>();
        wordList.add("User Account Information");
        wordList.add("Vocabulary Selection");
        wordList.add("Notification");
        wordList.add("About us");
        return wordList;
    }

    private void logoutUser() {
        session.setLogin(false);
        session.setKeyIsSelected(false);

        db.deleteUsers();
//        db.deleteUserWord();
        UserWord.userWordHashMap.clear();
        // Launching the login activity
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logout:
                logoutUser();
                break;
        }
    }

    class BookDialog extends AlertDialog implements DialogInterface.OnClickListener {

        private BookPicker bookPicker;
        private int bookValue = 0;

        public BookDialog(Context context, int book) {
            super(context);

            bookPicker = new BookPicker(context, book);
            setView(bookPicker);
            bookPicker.setOnDataChangerListener(new BookPicker.onValueChangeListener() {
                @Override
                public void onDataChange(BookPicker view, int book) {
                    bookValue = BookPicker.book;
                }

            });
            setButton(BUTTON_POSITIVE, "SET", this);
            setButton(BUTTON_NEGATIVE, "CANCEL", (OnClickListener) null);

        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            MainActivity.bookID = bookValue;
            session.setKeySelectBook(MainActivity.currentUserID, bookValue);
        }

        public int getBookValue() {
            return bookValue;
        }
    }
}
