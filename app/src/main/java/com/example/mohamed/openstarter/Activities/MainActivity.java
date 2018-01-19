package com.example.mohamed.openstarter.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.example.mohamed.openstarter.Adapters.ProjectListAdapter;
import com.example.mohamed.openstarter.Data.CustomClasses.ProjectWithFollowCount;
import com.example.mohamed.openstarter.Data.DataSuppliers.ProjectServer;
import com.example.mohamed.openstarter.Fragments.FilterFabProjectFragment;
import com.example.mohamed.openstarter.Helpers.DialogHelper;
import com.example.mohamed.openstarter.Models.Project;
import com.example.mohamed.openstarter.R;
import com.example.mohamed.openstarter.foldingcell.FoldingCell;
import com.google.firebase.auth.FirebaseAuth;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.vlstr.blurdialog.BlurDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Example of using Folding Cell with ListView and ListAdapter
 */
public class MainActivity extends AppCompatActivity  implements AAH_FabulousFragment.Callbacks, AAH_FabulousFragment.AnimationListener{



    public static MainActivity instance = null;


    private DialogHelper dialogHelper;
    private BlurDialog blurDialog;
    private ProjectServer projectDs = new ProjectServer();
    private FloatingActionButton fab ;
    private FilterFabProjectFragment dialogFilterFrag;
    private ArrayMap<String, List<String>> applied_filters = new ArrayMap<>();
    private List<ProjectWithFollowCount> mOriginalProjectsList = new ArrayList<>();

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

        fab = findViewById(R.id.fab);
        dialogFilterFrag = FilterFabProjectFragment.newInstance();
        dialogFilterFrag.setParentFab(fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFilterFrag.show(getSupportFragmentManager(), dialogFilterFrag.getTag());
            }
        });

        instance = this;
        dialogHelper = new DialogHelper();
        blurDialog = findViewById(R.id.blurLoader);


        dialogHelper.blurDialogShow(instance,blurDialog,"Loading projects");

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        //Setting projectListView
        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        resideMenu.setSwipeDirectionDisable( ResideMenu.DIRECTION_RIGHT);

        String titles[] = { "Profile", "Add new Project","Logout"};
        int icon[] = {R.drawable.profileicon, R.drawable.newprojecticon, R.drawable.logouticon};


        final ResideMenuItem item0 = new ResideMenuItem(this, icon[0], titles[0]);
        final ResideMenuItem item1 = new ResideMenuItem(this, icon[1], titles[1]);
        final ResideMenuItem item2 = new ResideMenuItem(this, icon[2], titles[2]);
        /*final ResideMenuItem item3 = new ResideMenuItem(this, icon[3], titles[3]);
        final ResideMenuItem item4 = new ResideMenuItem(this, icon[4], titles[4]);*/

        item0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(MainActivity.this, ProfilActivity.class);
                startActivity(i2);
                finish();

            }
        });

        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "item2 clicked", Toast.LENGTH_SHORT).show();
                Intent i2 = new Intent(MainActivity.this, CreateProjectActivity.class);
                startActivity(i2);
                finish();

            }
        });

        item2.setOnClickListener(new View.OnClickListener() {
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



        //******************************

        projectListView = findViewById(R.id.mainProjectListView);

        projectDs.projectWithCountGetAll(new ProjectServer.Callback() {

            @Override
            public void onSuccessGetWithFollowCount(List<ProjectWithFollowCount> projectList) {
                List <ProjectWithFollowCount> tempList = new ArrayList<>();
                //the usage of the temp list is very important cause without it you'll get a reference of the original list and it will get changed every time
                tempList.addAll(projectList);
                getmOriginalProjectsList().addAll(tempList);
                Log.d ("AAZZ","OriginalProjectsList size on load = "+ getmOriginalProjectsList().size() );
                dialogHelper.blurDialogHide(instance,blurDialog);
                // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)
                final ProjectListAdapter adapter = new ProjectListAdapter(getBaseContext(), tempList);

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

            @Override
            public void onFail() {
                dialogHelper.blurDialogHide(instance,blurDialog);
                Toast.makeText(MainActivity.this, "could not load projects", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccessCreate(Project createdProject) {}
            @Override
            public void onSuccessGet(List<Project> projectList) {}

        });
    }

    @Override
    public void onResult(Object result) {
        if (result.toString().equalsIgnoreCase("swiped_down")) {
            //do something or nothing
        } else {
            if (result != null) {
                ArrayMap<String, List<String>> applied_filters = (ArrayMap<String, List<String>>) result;
                List<ProjectWithFollowCount> tempList = new ArrayList<>();
                tempList.addAll(getmOriginalProjectsList());
                List<ProjectWithFollowCount> filteredList = tempList;
                if (applied_filters.size() != 0) {
                    //iterate over arraymap
                    for (Map.Entry<String, List<String>> entry : applied_filters.entrySet()) {
                        //Log.d("k9res", "entry.key: " + entry.getKey());
                        switch (entry.getKey()) {
                            case "category":
                                filteredList = ProjectWithFollowCount.getCategoryFilteredProjects(entry.getValue(), filteredList);
                                break;
                            case "followers":
                                filteredList = ProjectWithFollowCount.getFollowCountFilteredProjects(entry.getValue(), filteredList);
                                break;
                            case "goal":
                                filteredList = ProjectWithFollowCount.getGoalReachFilteredProjects(entry.getValue(), filteredList);
                                break;
                        }
                    }
                    Log.d("k9res", "new size: " + filteredList.size());
                    ((ProjectListAdapter)projectListView.getAdapter()).refreshProjects(filteredList);
                } else {
                    Log.d ("AAZZ","OriginalProjectsList size on result = "+ filteredList.size() );
                    ((ProjectListAdapter)projectListView.getAdapter()).refreshProjects(filteredList);
                }
            }
            //handle result
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (dialogFilterFrag.isAdded()) {
            dialogFilterFrag.dismiss();
            dialogFilterFrag.show(getSupportFragmentManager(), dialogFilterFrag.getTag());
        }
    }

    public ArrayMap<String, List<String>> getApplied_filters() {
        return applied_filters;
    }

    @Override
    public void onOpenAnimationStart() {

    }

    @Override
    public void onOpenAnimationEnd() {

    }

    @Override
    public void onCloseAnimationStart() {

    }

    @Override
    public void onCloseAnimationEnd() {

    }

    public List<ProjectWithFollowCount> getmOriginalProjectsList() {
        return mOriginalProjectsList;
    }

    public void setmOriginalProjectsList(List<ProjectWithFollowCount> mOriginalProjectsList) {
        this.mOriginalProjectsList = mOriginalProjectsList;
    }
}
