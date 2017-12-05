package com.example.mohamed.openstarter.Adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mohamed.openstarter.R;

/**
 * Created by Bacem on 12/5/2017.
 */

public class CategoriesSpinnerAdapter extends ArrayAdapter<String> {

        private Activity context;
        String[] data = null;

        public CategoriesSpinnerAdapter(Activity context, int resource, String[] categoriesList)
        {
            super(context, resource, categoriesList);
            this.context = context;
            this.data = categoriesList;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent)
        {
            View row = convertView;
            if(row == null)
            {
                LayoutInflater inflater = context.getLayoutInflater();
                row = inflater.inflate(R.layout.spinner_item_category, parent, false);
            }

            String item = data[position];
            if(item != null)
            {
                TextView categoryName = row.findViewById(R.id.tv_category_name);
                categoryName.setTextColor(Color.BLACK);
                categoryName.setText(item);
            }

            return row;
        }
}
