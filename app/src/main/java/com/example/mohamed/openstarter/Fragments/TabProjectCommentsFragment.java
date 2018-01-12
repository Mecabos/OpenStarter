package com.example.mohamed.openstarter.Fragments;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mohamed.openstarter.Activities.ProfilActivity;
import com.example.mohamed.openstarter.Activities.ProjectActivity;
import com.example.mohamed.openstarter.Adapters.CommentListAdapter;
import com.example.mohamed.openstarter.Data.CustomClasses.ProjectWithFollowCount;
import com.example.mohamed.openstarter.Data.DataSuppliers.CommentServer;
import com.example.mohamed.openstarter.Data.DataSuppliers.NotificationServer;
import com.example.mohamed.openstarter.Data.DataSuppliers.ProjectServer;
import com.example.mohamed.openstarter.Models.Comment;
import com.example.mohamed.openstarter.Models.User;
import com.example.mohamed.openstarter.R;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bacem on 11/27/2017.
 */

public class TabProjectCommentsFragment extends Fragment implements View.OnTouchListener {

    ProjectWithFollowCount mProject = new ProjectWithFollowCount();
    CommentServer commentDs = new CommentServer();
    CommentListAdapter adapter;
    ListView commentsListView;
    EditText etComment;
    private FirebaseAuth firebaseAuth;


    private static final String TAG = "TabProjectCommentsFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_project_comments_fragment, container, false);
        initializeFragment(view);
        return view;
    }

    public void initializeFragment(View v) {
        firebaseAuth = FirebaseAuth.getInstance();
        mProject = ((ProjectActivity) getActivity()).getProject();
        commentsListView = v.findViewById(R.id.lv_comments);
        etComment = v.findViewById(R.id.et_comment);
        //Drawable sendCommentImage = etComment.getCompoundDrawables()[2];
        /*sendCommentImage.setBounds(0, 0, (int) (sendCommentImage.getIntrinsicWidth() * 0.6),
                (int) (sendCommentImage.getIntrinsicHeight() * 0.6));*/
        //ScaleDrawable sd = new ScaleDrawable(sendCommentImage, 0, 40, 40);
        //etComment.setCompoundDrawables(null, null, sd.getDrawable(), null);
        etComment.setOnTouchListener(this);


        commentDs.commentGetByProject(String.valueOf(mProject.getId()), new CommentServer.Callback() {
            @Override
            public void onSuccessGet(ArrayList<Comment> commentsList) {
                adapter = new CommentListAdapter(getActivity(), R.layout.item_comment, commentsList);
                commentsListView.setAdapter(adapter);
            }

            @Override
            public void onSuccessCreate(Comment createdComment) {
            }

        });

        commentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(getActivity(), ProfilActivity.class);
                String user_email = "aaaa@aa.aa";
                intent.putExtra("user_id", user_email);
                Toast.makeText(getActivity(), "userr ="+Long.toString(id),Toast.LENGTH_LONG).show();
                //startActivity(intent);

            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        mProject = ((ProjectActivity) getActivity()).getProject();

        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getRawX() >= (etComment.getRight() - (etComment.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()*5))) {
                EditText comment = (EditText) v;
                String commentText = comment.getText().toString();
                String userEmail = firebaseAuth.getCurrentUser().getEmail();
                final String projectId = String.valueOf(((ProjectActivity) getActivity()).getProject().getId());
                if (commentText.trim().length() == 0)
                    Toast.makeText(getActivity(), "Write a comment before sending", Toast.LENGTH_LONG).show();
                else {
                    commentDs.commentCreate(commentText, userEmail, projectId, new CommentServer.Callback() {
                        @Override
                        public void onSuccessGet(ArrayList<Comment> commentsList) {
                        }

                        @Override
                        public void onSuccessCreate(Comment createdComment) {

                            adapter.add(createdComment);

                            Toast.makeText(getActivity(), "Comment posted", Toast.LENGTH_LONG).show();


                            Log.d("notiffy", "users "/*+String.valueOf(mProject.getId())*/);
                            //notif here
                            //MyFirebaseInstanceIDService instanceIDService = new MyFirebaseInstanceIDService();
                            //String notifToken = instanceIDService.refreshedToken;
                            etComment.getText().clear();
                            etComment.clearFocus();
                            //Log.d("notiff", "Refreshed token: " + FirebaseInstanceId.getInstance().getToken());

                            //Log.d("notiffy", "users "+String.valueOf(mProject.getId()));

                            ProjectServer projectDs = new ProjectServer();

                            projectDs.projectGetGroupMembersAll(String.valueOf(mProject.getId()), new ProjectServer.CallbackGet() {
                                @Override
                                public void onSuccessGet(List<User> result) {

                                    JSONArray jsonArray = new JSONArray();
                                    //List<String> tokens = new ArrayList<>();
                                    for (User user:result) {
                                        jsonArray.put(user.getToken());
                                    }

                                    NotificationServer notificationDs = new NotificationServer();

                                    notificationDs.addNotification(jsonArray, "new comment","new comment added", new NotificationServer.CallbackSend() {
                                        @Override
                                        public void onSuccess() {
                                            Log.d("notiff", " notification success");
                                        }

                                        @Override
                                        public void onError() {
                                            Log.d("notiff", "error notification ");
                                        }
                                    });

                                }

                                @Override
                                public void onFail() {

                                    Log.d("notiff", "failed to load users");

                                }
                            });


                        }
                    });

                }

                return true;
            }
        }
        return false;
    }
}
