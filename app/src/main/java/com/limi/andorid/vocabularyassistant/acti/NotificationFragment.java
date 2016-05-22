package com.limi.andorid.vocabularyassistant.acti;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.limi.andorid.vocabularyassistant.R;
import com.limi.andorid.vocabularyassistant.data.NotificationEntry;
import com.limi.andorid.vocabularyassistant.helper.MySQLiteHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;


public class NotificationFragment extends Fragment {


    private static RankFragment instance;
    private View parentView;
    //    private ListView listView;
    private RecyclerView mRecyclerView;
    private MySQLiteHandler db;

    private NotificationAdapter myAdapter;

    public static RankFragment getInstance() {
        if (instance == null) {
            synchronized (FavouriteWordFragment.class) {
                if (instance == null) instance = new RankFragment();
            }
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (parentView != null) {
            ViewGroup viewGroup = (ViewGroup) parentView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(parentView);
            }
            return parentView;
        }

//        ArrayList<NotificationEntry> recordArrayList = new ArrayList<>();

        parentView = inflater.inflate(R.layout.fragment_notification, container, false);

        mRecyclerView = (RecyclerView) parentView.findViewById(R.id.notification_m);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        for (int i = 0; i < 7; i++) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, -i);
            date = calendar.getTime();
            String sDate = simpleDateFormat.format(date);


            Date date1 = new Date();

            String sDate1 = simpleDateFormat.format(date1);


            db = new MySQLiteHandler(getContext());
            int fre = db.getFrequency(sDate, MainActivity.currentUserID);
            String content = "You have recited " + fre + " words, continue recite or review?";

//        NotificationEntry notificationEntry = new NotificationEntry(content, sDate);
            if (fre > 0) {
                MainActivity.notificationEntries.put(sDate, content);
            } else if (sDate1.equals(sDate)) {
                MainActivity.notificationEntries.put(sDate, content);
            }


        }

        ArrayList<NotificationEntry> notificationEntries = new ArrayList<>();

        for (Map.Entry<String, String> entry : MainActivity.notificationEntries.entrySet()) {

            String sSDate = entry.getKey();
            String value = entry.getValue();

            NotificationEntry notificationEntry = new NotificationEntry(value, sSDate);
            notificationEntries.add(notificationEntry);

        }


        mRecyclerView.setHasFixedSize(true);

        myAdapter = new NotificationAdapter(getContext(), notificationEntries);

        mRecyclerView.setAdapter(myAdapter);
        initView();
        return parentView;
    }

    private void initView() {


    }

}


