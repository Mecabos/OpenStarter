package com.example.mohamed.openstarter.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mohamed.openstarter.Adapters.ProjectListAdapter;
import com.example.mohamed.openstarter.Models.Project;
import com.example.mohamed.openstarter.Data.DataSuppliers.ProjectDs;
import com.example.mohamed.openstarter.R;
import com.example.mohamed.openstarter.foldingcell.FoldingCell;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Example of using Folding Cell with ListView and ListAdapter
 */
public class MainActivity extends AppCompatActivity {

    ProjectDs projectDs = new ProjectDs();

    ListView projectListView ;
    ResideMenu resideMenu;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Setting projectListView
        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);

        String titles[] = { "featured projects", "Profile", "Calendar", "Settings" };
        int icon[] = { R.drawable.ic_launcher, R.drawable.com_facebook_profile_picture_blank_portrait, R.drawable.ic_launcher, R.drawable.ic_launcher };

        for (int i = 0; i < titles.length; i++){
            final ResideMenuItem item = new ResideMenuItem(this, icon[i], titles[i]);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            resideMenu.addMenuItem(item,  ResideMenu.DIRECTION_LEFT); // or  ResideMenu.DIRECTION_RIGHT

        }

        projectListView = findViewById(R.id.mainProjectListView);

        projectDs.projectGetAll(new ProjectDs.Callback() {
            @Override
            public void onSuccess(List<Project> projectList) {

                // add custom btn handler to first list project
                projectList.get(0).setRequestBtnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "CUSTOM HANDLER FOR FIRST BUTTON", Toast.LENGTH_SHORT).show();
                    }
                });

                // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)
                final ProjectListAdapter adapter = new ProjectListAdapter(getBaseContext(), projectList);

                // add default btn handler for each request btn on each project if custom handler not found
                adapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "DEFAULT HANDLER FOR ALL BUTTONS", Toast.LENGTH_SHORT).show();
                    }
                });

                // set elements to adapter
                projectListView.setAdapter(adapter);

                // set on click event listener to list view
                projectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                        // toggle clicked cell state
                        ((FoldingCell) view).toggle(false);
                        // register in adapter that state for selected cell is toggled
                        adapter.registerToggle(pos);
                    }
                });
            }
        });









    }
}
