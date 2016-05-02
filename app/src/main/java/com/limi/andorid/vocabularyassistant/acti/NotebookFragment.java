package com.limi.andorid.vocabularyassistant.acti;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.limi.andorid.vocabularyassistant.R;
import com.limi.andorid.vocabularyassistant.helper.UserWord;
import com.limi.andorid.vocabularyassistant.helper.Word;
import com.limi.andorid.vocabularyassistant.helper.WordImportHandler;

import java.util.ArrayList;

public class NotebookFragment extends Fragment {

    private View parentView;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.notebook_fragment, container, false);
        listView = (ListView) parentView.findViewById(R.id.listView1);
        initView();
        return parentView;
    }

    private void initView() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                getCalendarData());
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "Clicked item!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private ArrayList<String> getCalendarData() {
        ArrayList<String> wordList = new ArrayList<>();
        ArrayList<Word> words = WordImportHandler.threeKArrayList;
//        wordList.add("Word1");
//        wordList.add("Word2");
//        wordList.add("Word3");

        for (UserWord u : UserWord.userWordArrayList) {
            if (u.isFavourite()) {
                wordList.add(words.get(u.getWordID()).getWord());
            }

        }

        return wordList;
    }
}
