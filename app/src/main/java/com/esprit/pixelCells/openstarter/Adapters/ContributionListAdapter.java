package com.esprit.pixelCells.openstarter.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.pixelCells.openstarter.Data.CustomClasses.ContributionsWithUsers;
import com.esprit.pixelCells.openstarter.R;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Bacem on 12/3/2017.
 */

public class ContributionListAdapter extends ArrayAdapter<ContributionsWithUsers> {

    private Context context;
    private int layoutResourceId;
    private List<ContributionsWithUsers> contributionsList = null;

    public ContributionListAdapter(Context context, int layoutResourceId, List<ContributionsWithUsers> contributionsList) {
        super(context, layoutResourceId, contributionsList);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.contributionsList = contributionsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ContributionsWithUsersHolder holder = null;
        final ContributionsWithUsers contributionsWithUsers = getItem(position);
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ContributionsWithUsersHolder();

            holder.userName = row.findViewById(R.id.fullName);
            holder.contribDate = row.findViewById(R.id.contributionDate);
            holder.amount = row.findViewById(R.id.amount);
            holder.email = row.findViewById(R.id.email);
            holder.userImage = row.findViewById(R.id.avatar);
            row.setTag(holder);
        } else {
            holder = (ContributionsWithUsersHolder) row.getTag();
        }

        // Name and date
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy/MM/dd");
        String timefromDate = timeFormat.format(contributionsWithUsers.getPaymentDate());
        String dayFromDate = dayFormat.format(contributionsWithUsers.getPaymentDate());
        String userNameDate = contributionsWithUsers.getFirstName() + " " + contributionsWithUsers.getLastName() /*+ " - " + dayFromDate + " " +  timefromDate */;
        String contributionDate = dayFromDate + " " +  timefromDate ;
        // Text
        String amountText = String.valueOf(contributionsWithUsers.getAmount());
        String emailText = String.valueOf(contributionsWithUsers.getEmail());
        //Image
        int imageMock = R.drawable.avatar ;

        holder.userName.setText(userNameDate);
        holder.contribDate.setText(contributionDate);
        holder.amount.setText("amount : "+amountText+"$");
        holder.email.setText(emailText);
        holder.userImage.setImageResource(imageMock);

        return row;
    }

    private static class ContributionsWithUsersHolder {
        TextView userName;
        TextView contribDate;
        TextView amount;
        TextView email;
        ImageView userImage;
    }

    public void add(ContributionsWithUsers contributionsWithUsers)
    {
        contributionsList.add(contributionsWithUsers);
        notifyDataSetChanged();

    }
}
