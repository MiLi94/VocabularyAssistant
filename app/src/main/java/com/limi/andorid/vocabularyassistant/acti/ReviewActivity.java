package com.limi.andorid.vocabularyassistant.acti;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.limi.andorid.vocabularyassistant.R;
import com.limi.andorid.vocabularyassistant.helper.MySQLiteHandler;
import com.limi.andorid.vocabularyassistant.helper.WordImportHandler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ReviewActivity extends AppCompatActivity {

    public static ArrayList<Integer> today;
    private TextView needToReview;
    private TextView hasReviewed;
    private MySQLiteHandler mySQLiteHandler;
    private ExpandableListView reviewListView = null;
    private List<String> parent = null;
    private Map<String, List<String>> map = null;
    private Button startBtn;
    private boolean isSelected = false;
    private Button quit_Btn;
    private ArrayList<Integer> id1;
    private ArrayList<Integer> id2;
    private ArrayList<Integer> id3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        quit_Btn = (Button) findViewById(R.id.quit);
        quit_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        needToReview = (TextView) findViewById(R.id.number);
        hasReviewed = (TextView) findViewById(R.id.time_review);
        mySQLiteHandler = new MySQLiteHandler(getApplicationContext());
        today = new ArrayList<>();
        String sDateFormat = "yyyy-MM-dd";
        DateFormat dateFormat = new SimpleDateFormat(sDateFormat, Locale.ENGLISH);
        Date date = new Date();
        String sDate = dateFormat.format(date);
        int haveReviewed = mySQLiteHandler.getReviewedFrequency(sDate, MainActivity.currentUserID);
        int iNeedToReview = mySQLiteHandler.getFrequency(sDate, MainActivity.currentUserID) - haveReviewed;

        needToReview.setText(String.valueOf(iNeedToReview));
        hasReviewed.setText(String.valueOf(haveReviewed));
        initData();
        reviewListView = (ExpandableListView) findViewById(R.id.myExpandableListView);
        reviewListView.setAdapter(new MyReviewListAdapter());
        startBtn = (Button) findViewById(R.id.start_review);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isSelected) {
                    Intent intent = new Intent(getApplicationContext(), EveryDayReviewActivity.class);
                    startActivity(intent);
//                    finish();
                }
            }
        });
        reviewListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

                isSelected = true;
                switch (groupPosition) {
                    case 0:
                        today = id1;
                        break;
                    case 1:
                        today = id2;
                        break;
                    case 2:
                        today = id3;
                        break;

                }
            }


        });
    }


    private void initData() {
        parent = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            String sDateFormat = "yyyy-MM-dd";
            DateFormat dateFormat = new SimpleDateFormat(sDateFormat, Locale.ENGLISH);
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, -i);
            date = calendar.getTime();
            String sDate = dateFormat.format(date);

            parent.add(sDate);
        }


        map = new HashMap<String, List<String>>();

        List<String> list1 = new ArrayList<String>();
        id1 = mySQLiteHandler.getUserWordDataDate(MainActivity.currentUserID, parent.get(0));
        for (int i : id1) {
            list1.add(WordImportHandler.systemWordBaseArrayList.get(i).getWord());
        }

        map.put(parent.get(0), list1);

        List<String> list2 = new ArrayList<String>();
        id2 = mySQLiteHandler.getUserWordDataDate(MainActivity.currentUserID, parent.get(1));
        for (int i : id2) {
            list2.add(WordImportHandler.systemWordBaseArrayList.get(i).getWord());
        }
        map.put(parent.get(1), list2);

        List<String> list3 = new ArrayList<String>();
        id3 = mySQLiteHandler.getUserWordDataDate(MainActivity.currentUserID, parent.get(2));
        for (int i : id3) {
            list3.add(WordImportHandler.systemWordBaseArrayList.get(i).getWord());
        }
        map.put(parent.get(2), list3);


    }


    class MyReviewListAdapter extends BaseExpandableListAdapter {
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            String key = parent.get(groupPosition);
            return (map.get(key).get(childPosition));
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            String key = ReviewActivity.this.parent.get(groupPosition);
            String info = map.get(key).get(childPosition);
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) ReviewActivity.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.layout_children, null);
            }
            TextView tv = (TextView) convertView
                    .findViewById(R.id.second_textview);
            tv.setText(info);
            return tv;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            String key = parent.get(groupPosition);
            int size = map.get(key).size();
            return size;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return parent.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return parent.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }


        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) ReviewActivity.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.layout_parent, null);
            }
//            convertView.setBackgroundColor(getResources().getColor(R.color.accent));
            TextView tv = (TextView) convertView
                    .findViewById(R.id.parent_textview);
            tv.setText(ReviewActivity.this.parent.get(groupPosition));

            return tv;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
