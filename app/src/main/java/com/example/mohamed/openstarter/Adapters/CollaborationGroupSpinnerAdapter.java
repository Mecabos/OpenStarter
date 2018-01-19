package com.example.mohamed.openstarter.Adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mohamed.openstarter.Models.CollaborationGroup;
import com.example.mohamed.openstarter.R;

/**
 * Created by Bacem on 12/5/2017.
 */

public class CollaborationGroupSpinnerAdapter extends ArrayAdapter<CollaborationGroup> {

        private Activity context;
        CollaborationGroup[] data = null;

        public CollaborationGroupSpinnerAdapter(Activity context, int resource, CollaborationGroup[] categoriesList)
        {
            super(context, resource, categoriesList);
            this.context = context;
            this.data = categoriesList;
        }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if(row == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_item_collaboration_group, parent, false);
        }
        String item = data[position].getName();
        if(item != null)
        {
            TextView collaborationGroupName = row.findViewById(R.id.tv_collaboration_group_name);
            collaborationGroupName.setTextColor(Color.WHITE);
            collaborationGroupName.setTextSize(20);
            collaborationGroupName.setText(item);
        }

        return row;
    }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent)
        {
            View row = convertView;
            if(row == null)
            {
                LayoutInflater inflater = context.getLayoutInflater();
                row = inflater.inflate(R.layout.spinner_item_collaboration_group, parent, false);
            }

            String item = data[position].getName();
            if(item != null)
            {
                TextView collaborationGroupName = row.findViewById(R.id.tv_collaboration_group_name);
                collaborationGroupName.setTextColor(Color.BLACK);
                collaborationGroupName.setTextSize(24);
                collaborationGroupName.setText(item);
            }

            return row;
        }
}
