package com.example.mohamed.openstarter.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mohamed.openstarter.R;

/**
 * Created by Bacem on 11/27/2017.
 */

public class TabProjectCommentsFragment extends Fragment {

    private static final String TAG = "TabProjectCommentsFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_project_comments_fragment,container,false) ;

        return  view ;

    }
}
