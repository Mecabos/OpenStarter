package com.example.mohamed.openstarter.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohamed.openstarter.Activities.MainActivity;
import com.example.mohamed.openstarter.Activities.ProfilActivity;
import com.example.mohamed.openstarter.Activities.ProjectActivity;
import com.example.mohamed.openstarter.Models.Comment;
import com.example.mohamed.openstarter.R;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Bacem on 12/3/2017.
 */

public class CommentListAdapter extends ArrayAdapter<Comment> {

    Context context;
    int layoutResourceId;
    List<Comment> commentsList = null;

    public CommentListAdapter(Context context, int layoutResourceId, List<Comment> commentsList) {
        super(context, layoutResourceId, commentsList);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.commentsList = commentsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CommentHolder holder = null;
        final Comment comment = getItem(position);
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new CommentHolder();

            holder.userName = row.findViewById(R.id.tv_user_name);
            holder.text = row.findViewById(R.id.tv_user_comment);
            holder.userImage = row.findViewById(R.id.img_user);
            holder.commentDate = row.findViewById(R.id.tv_comment_date);

            row.setTag(holder);
        } else {
            holder = (CommentHolder) row.getTag();
        }

        // setting the btn request
        comment.setRequestClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getContext(), ProfilActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getContext().startActivity(myIntent);
            }
        });

        // Name and date
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy/MM/dd");
        String timefromDate = timeFormat.format(comment.getCommentDate());
        String dayFromDate = dayFormat.format(comment.getCommentDate());
        String userName = comment.getUser().getFirstName() + " " + comment.getUser().getLastName() ;
        String commentDate = dayFromDate + " " +  timefromDate;
        // Text
        String commentText = comment.getText();
        //Image
        int imageMock = R.drawable.avatar ;

        holder.userName.setText(userName);
        holder.text.setText(commentText);
        holder.userImage.setImageResource(imageMock);
        holder.commentDate.setText(commentDate);

        return row;
    }

    private static class CommentHolder {
        TextView userName;
        TextView text;
        TextView commentDate;
        ImageView userImage;
    }

    public void add(Comment comment)
    {
        commentsList.add(comment);
        notifyDataSetChanged();

    }
}
