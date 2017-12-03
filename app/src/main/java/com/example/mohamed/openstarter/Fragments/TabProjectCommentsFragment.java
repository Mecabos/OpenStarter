package com.example.mohamed.openstarter.Fragments;


import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mohamed.openstarter.Adapters.CommentListAdapter;
import com.example.mohamed.openstarter.Data.DataSuppliers.CommentDs;
import com.example.mohamed.openstarter.Models.Comment;
import com.example.mohamed.openstarter.R;

import java.util.List;

/**
 * Created by Bacem on 11/27/2017.
 */

public class TabProjectCommentsFragment extends Fragment {

    CommentDs commentDs = new CommentDs();
    ListView commentsListView;


    private static final String TAG = "TabProjectCommentsFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_project_comments_fragment,container,false) ;

        perform(view);



        return  view ;
    }

    public void perform(View v) {

        commentsListView = (ListView) v.findViewById(R.id.lv_comments);

        commentDs.commentGetByProject("2",new CommentDs.Callback() {
            @Override
            public void onSuccess(List< Comment> commentsList) {

                final CommentListAdapter adapter = new CommentListAdapter(getActivity(),R.layout.item_comment, commentsList);

                // set elements to adapter
                commentsListView.setAdapter(adapter);
            }
        });
    }
}
