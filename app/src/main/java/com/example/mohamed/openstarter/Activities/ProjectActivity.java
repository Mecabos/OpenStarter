package com.example.mohamed.openstarter.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.mohamed.openstarter.Adapters.SectionPageAdapter;
import com.example.mohamed.openstarter.Fragments.TabProjectCampaignFragment;
import com.example.mohamed.openstarter.Fragments.TabProjectCommentsFragment;
import com.example.mohamed.openstarter.Fragments.TabProjectCommunityFragment;
import com.example.mohamed.openstarter.Models.Category;
import com.example.mohamed.openstarter.Models.CollaborationGroup;
import com.example.mohamed.openstarter.Models.Project;
import com.example.mohamed.openstarter.R;
import com.github.florent37.bubbletab.BubbleTab;
import com.ldoublem.ringPregressLibrary.Ring;
import com.ldoublem.ringPregressLibrary.RingProgress;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProjectActivity extends AppCompatActivity {

    private static final String TAG = "ProjectActivity";
    private static String PROJECT_TAG = "project";
    private static String COLLABORATION_GROUP_TAG = "collaboration group";
    private static String CATEGORY_TAG = "category" ;

    private Project mProject = new Project();
    private CollaborationGroup mCollaborationGroup = new CollaborationGroup();
    private Category mCategory = new Category();


    private SectionPageAdapter mSectionPageAdapter;
    private TextView projectNameTv ;
    private TextView projectGroupTv ;
    private TextView projectCategoryTv ;
    private ViewPager mViewPager;
    private LinearLayout categoryLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        initializeFragment();
    }

    private void initializeFragment() {

        setProject();
        setCollaborationGroup();
        setCategory();

        mSectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);
        BubbleTab tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        categoryLayout = findViewById(R.id.project_category_layout);
        GradientDrawable categoryBg = (GradientDrawable ) categoryLayout.getBackground() ;
        categoryBg.setColor(Color.parseColor(mCategory.getColor()));


        //The page text fields
        projectNameTv = findViewById(R.id.project_name);
        projectNameTv.setText(mProject.getName());
        projectGroupTv = findViewById(R.id.project_founder_group);
        projectGroupTv.setText(mCollaborationGroup.getName());
        projectCategoryTv = findViewById(R.id.project_category);
        projectCategoryTv.setText(mCategory.getLabel());

    }

    private void setupViewPager(ViewPager viewPager) {
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

    public CollaborationGroup getCollaborationGroup() {
        return mCollaborationGroup;
    }

    public void setCollaborationGroup() {
        this.mCollaborationGroup = getIntent().getParcelableExtra(COLLABORATION_GROUP_TAG);
    }

    public Category getCategory() {
        return mCategory;
    }

    public void setCategory() {
        this.mCategory = getIntent().getParcelableExtra(CATEGORY_TAG);
    }
}
