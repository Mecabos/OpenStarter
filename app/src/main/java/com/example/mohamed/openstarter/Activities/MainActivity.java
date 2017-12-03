package com.example.mohamed.openstarter.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mohamed.openstarter.Data.DataSuppliers.CommentDs;
import com.example.mohamed.openstarter.Data.DataSuppliers.UserDs;
import com.example.mohamed.openstarter.Models.Comment;
import com.google.firebase.auth.FirebaseAuth;

import com.example.mohamed.openstarter.Adapters.ProjectListAdapter;
import com.example.mohamed.openstarter.Data.DataSuppliers.ProjectDs;
import com.example.mohamed.openstarter.Models.Project;
import com.example.mohamed.openstarter.R;
import com.example.mohamed.openstarter.foldingcell.FoldingCell;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.util.List;

/**
 * Example of using Folding Cell with ListView and ListAdapter
 */
public class MainActivity extends AppCompatActivity {

    ProjectDs projectDs = new ProjectDs();

    ListView projectListView;
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

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        //Setting projectListView
        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);

        String titles[] = {"featured projects", "Profile", "Add new Project", "Settings", "Logout"};
        int icon[] = {R.drawable.ic_launcher, R.drawable.com_facebook_profile_picture_blank_square, R.drawable.plus, R.drawable.ic_launcher, R.drawable.logout};


        final ResideMenuItem item0 = new ResideMenuItem(this, icon[0], titles[0]);
        final ResideMenuItem item1 = new ResideMenuItem(this, icon[1], titles[1]);
        final ResideMenuItem item2 = new ResideMenuItem(this, icon[2], titles[2]);
        final ResideMenuItem item3 = new ResideMenuItem(this, icon[3], titles[3]);
        final ResideMenuItem item4 = new ResideMenuItem(this, icon[4], titles[4]);

        item0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "item0 clicked", Toast.LENGTH_SHORT).show();

            }
        });

        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(MainActivity.this, ProfilActivity.class);
                startActivity(i2);

            }
        });

        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "item2 clicked", Toast.LENGTH_SHORT).show();

            }
        });

        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "item3 clicked", Toast.LENGTH_SHORT).show();

            }
        });

        item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this);
                Intent i2 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i2, oc2.toBundle());
                finish();

            }
        });

        resideMenu.addMenuItem(item0, ResideMenu.DIRECTION_LEFT); // or  ResideMenu.DIRECTION_RIGHT
        resideMenu.addMenuItem(item1, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(item2, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(item3, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(item4, ResideMenu.DIRECTION_LEFT);

        projectListView = (ListView) findViewById(R.id.mainProjectListView);

        projectDs.projectGetAll(new ProjectDs.Callback() {
            @Override
            public void onSuccess(List<Project> projectList) {


                // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)
                final ProjectListAdapter adapter = new ProjectListAdapter(getBaseContext(), projectList);


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
