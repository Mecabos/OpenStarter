package com.example.mohamed.openstarter.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.mohamed.openstarter.Adapters.SectionPageAdapter;
import com.example.mohamed.openstarter.Fragments.TabProjectCampaignFragment;
import com.example.mohamed.openstarter.Fragments.TabProjectCommentsFragment;
import com.example.mohamed.openstarter.Fragments.TabProjectCommunityFragment;
import com.example.mohamed.openstarter.Models.Project;
import com.example.mohamed.openstarter.R;
import com.github.florent37.bubbletab.BubbleTab;
import com.ldoublem.ringPregressLibrary.Ring;
import com.ldoublem.ringPregressLibrary.RingProgress;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProjectActivity extends AppCompatActivity {

    private static final String TAG = "ProjectActivity";
    private static String PROJECT_TAG = "project" ;

    private Project mProject = new Project();
    private SectionPageAdapter mSectionPageAdapter ;

    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        initializeFragment();
    }

    private void initializeFragment(){

        setProject();
        mSectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        //set up the viewpager with the sections adapter
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        BubbleTab tabLayout =  findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new TabProjectCampaignFragment(), "Campaign");
        adapter.addFragment(new TabProjectCommunityFragment(), "Community");
        adapter.addFragment(new TabProjectCommentsFragment(), "Comments");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }

    public Project getProject() {
        return mProject;
    }

    public void setProject() {
        this.mProject = getIntent().getParcelableExtra(PROJECT_TAG);
    }

}
