package com.example.mohamed.openstarter.Fragments;


import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mohamed.openstarter.Activities.ProjectActivity;
import com.example.mohamed.openstarter.Adapters.CommentListAdapter;
import com.example.mohamed.openstarter.Data.DataSuppliers.CommentDs;
import com.example.mohamed.openstarter.Models.Comment;
import com.example.mohamed.openstarter.Models.Project;
import com.example.mohamed.openstarter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bacem on 11/27/2017.
 */

public class TabProjectCommentsFragment extends Fragment implements View.OnTouchListener {

    Project mProject = new Project() ;
    CommentDs commentDs = new CommentDs();
    CommentListAdapter adapter;
    ListView commentsListView;
    EditText etComment;


    private static final String TAG = "TabProjectCommentsFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_project_comments_fragment,container,false) ;
        initializeFragment(view);
        return  view ;
    }

    public void initializeFragment(View v) {
        mProject = ((ProjectActivity)getActivity()).getProject() ;
        commentsListView = v.findViewById(R.id.lv_comments);
        etComment = v.findViewById(R.id.et_comment);

        etComment.setOnTouchListener(this);


        commentDs.commentGetByProject(String.valueOf(mProject.getId()),new CommentDs.Callback() {
            @Override
            public void onSuccessGet(ArrayList< Comment> commentsList) {
                adapter = new CommentListAdapter(getActivity(),R.layout.item_comment, commentsList);
                commentsListView.setAdapter(adapter);
            }
            @Override
            public void onSuccessCreate(Comment createdComment) {}

        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;

        if(event.getAction() == MotionEvent.ACTION_UP) {
            if(event.getRawX() >= (etComment.getRight() - etComment.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                EditText comment = (EditText) v ;
                String commentText = comment.getText().toString() ;
                String userId = "2";//TODO: dynamic
                String projectId = "2";//TODO: dynamic
                if (commentText.trim().length() == 0 )  Toast.makeText(getActivity(), "Write a comment before sending",Toast.LENGTH_LONG).show();
                else {
                    commentDs.commentCreate(commentText,userId,projectId,new CommentDs.Callback() {
                        @Override
                        public void onSuccessGet(ArrayList< Comment> commentsList) {}
                        @Override
                        public void onSuccessCreate(Comment createdComment) {

                            adapter.add(createdComment);

                            Toast.makeText(getActivity(), "Comment posted",Toast.LENGTH_LONG).show();

                        }
                    });

                }


                return true;
            }
        }
        return false;
    }
}
