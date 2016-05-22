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
import com.limi.andorid.vocabularyassistant.data.Record;
import com.limi.andorid.vocabularyassistant.helper.MySQLiteHandler;

import java.util.ArrayList;


public class RankFragment extends Fragment {
    private static RankFragment instance;
    private View parentView;
    //    private ListView listView;
    private RecyclerView mRecyclerView;
    private MySQLiteHandler db;
    private ArrayList<Record> recordArrayList = new ArrayList<>();
    private RankAdapter myAdapter;

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
//        db = new MySQLiteHandler(getActivity());
        parentView = inflater.inflate(R.layout.fragment_rank, container, false);
//        listView = (ListView) parentView.findViewById(R.id.listView1);
        mRecyclerView = (RecyclerView) parentView.findViewById(R.id.ranklist);
//        for (int i = startID; i <= endID; i++) {
//            summaryList.add(WordImportHandler.systemWordBaseArrayList.get(i));
//        }


        if (MainActivity.records.size() >= 5) {
            for (int i = 0; i < 5; i++) {
                recordArrayList.add(MainActivity.records.get(i));
            }
        } else {
            recordArrayList = MainActivity.records;
        }


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setHasFixedSize(true);

        myAdapter = new RankAdapter(getContext(), recordArrayList);

        mRecyclerView.setAdapter(myAdapter);
        initView();
        return parentView;
    }

    private void initView() {


    }

}
