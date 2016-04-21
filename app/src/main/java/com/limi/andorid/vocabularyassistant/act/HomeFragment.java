package com.limi.andorid.vocabularyassistant.act;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.limi.andorid.vocabularyassistant.R;
import com.limi.andorid.vocabularyassistant.helper.ColorArcProgressBar;
import com.special.ResideMenu.ResideMenu;

/**
 * Created by limi on 16/4/20.
 */


public class HomeFragment extends Fragment {

    private View parentView;
    private ResideMenu resideMenu;
    private ColorArcProgressBar colorArcProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.homefragment, container, false);
        setUpViews();
        return parentView;
    }

    private void setUpViews() {
        MainActivity parentActivity = (MainActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();

        parentView.findViewById(R.id.btn_open_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });

        // add gesture operation's ignored views
        FrameLayout ignored_view = (FrameLayout) parentView.findViewById(R.id.ignored_view);
        resideMenu.addIgnoredView(ignored_view);
        colorArcProgressBar = (ColorArcProgressBar) parentView.findViewById(R.id.progressbar);
        colorArcProgressBar.setCurrentValues(77);
        colorArcProgressBar.setDiameter(200);
    }


}
