package com.limi.andorid.vocabularyassistant.acti;


import android.content.Intent;
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
import java.util.Iterator;
import java.util.Map;

public class FavouriteWordFragment extends Fragment {

    private static FavouriteWordFragment instance;
    private View parentView;
    private ListView listView;

    public static FavouriteWordFragment getInstance() {
        if (instance == null) {
            synchronized (FavouriteWordFragment.class) {
                if (instance == null) instance = new FavouriteWordFragment();
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
        parentView = inflater.inflate(R.layout.notebook_fragment, container, false);
        listView = (ListView) parentView.findViewById(R.id.listView1);
        initView();
        return parentView;
    }

    private void initView() {
        final ArrayAdapter<Word> arrayAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                getCalendarData());
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s = String.valueOf(arrayAdapter.getItem(i).getID());

                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(parentView.getContext(), ViewWord.class);
                Bundle bundle = new Bundle();
                bundle.putString("ID", s);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);

            }
        });
    }

    private ArrayList<Word> getCalendarData() {
        ArrayList<Word> wordList = new ArrayList<>();
        ArrayList<Word> words = WordImportHandler.threeKArrayList;

        Iterator iterator = UserWord.userWordHashMap.entrySet().iterator();

//        for (UserWord u : UserWord.userWordHashMap) {
//            if (u.isFavourite()) {
//                wordList.add(words.get(u.getWordID()));
//            }
//
//        }
        while (iterator.hasNext()) {
            Map.Entry<Integer, UserWord> mapEntry = (Map.Entry) iterator.next();
            Integer key = mapEntry.getKey();
            UserWord userWord = mapEntry.getValue();
            if (userWord.isFavourite()) {
                wordList.add(words.get((userWord.getWordID())));
            }


        }
        return wordList;
    }
}
