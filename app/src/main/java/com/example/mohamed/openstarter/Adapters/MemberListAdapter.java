package com.example.mohamed.openstarter.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohamed.openstarter.Models.User;
import com.example.mohamed.openstarter.R;

import java.util.List;

/**
 * Created by Bacem on 12/3/2017.
 */

public class MemberListAdapter extends ArrayAdapter<User> {

    private Context context;
    private int layoutResourceId;
    private List<User> memberList = null;

    public MemberListAdapter(Context context, int layoutResourceId, List<User> memberList) {
        super(context, layoutResourceId, memberList);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.memberList = memberList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        UserHolder holder = null;
        final User user = getItem(position);
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new UserHolder();

            holder.userName = row.findViewById(R.id.fullName);
            holder.email = row.findViewById(R.id.email);
            holder.userImage = row.findViewById(R.id.avatar);
            row.setTag(holder);
        } else {
            holder = (UserHolder) row.getTag();
        }

        // Name and date

        String userNameDate = user.getFirstName() + " " + user.getLastName();

        // Text
        String emailText = String.valueOf(user.getEmail());
        //Image
        int imageMock = R.drawable.avatar ;

        holder.userName.setText(userNameDate);
        holder.email.setText(emailText);
        holder.userImage.setImageResource(imageMock);

        return row;
    }

    private static class UserHolder {
        TextView userName;
        TextView email;
        ImageView userImage;
    }

    public void add(User user)
    {
        memberList.add(user);
        notifyDataSetChanged();

    }
}
