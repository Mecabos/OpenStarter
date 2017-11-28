package com.example.mohamed.openstarter.Activities;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.mohamed.openstarter.Adapters.SectionPageAdapter;
import com.example.mohamed.openstarter.Fragments.TabProjectCampaignFragment;
import com.example.mohamed.openstarter.Fragments.TabProjectCommentsFragment;
import com.example.mohamed.openstarter.Fragments.TabProjectCommunityFragment;
import com.example.mohamed.openstarter.R;
import com.github.florent37.bubbletab.BubbleTab;
import com.ldoublem.ringPregressLibrary.OnSelectRing;
import com.ldoublem.ringPregressLibrary.Ring;
import com.ldoublem.ringPregressLibrary.RingProgress;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProjectActivity extends AppCompatActivity {

    private static final String TAG = "ProjectActivity";

    private SectionPageAdapter mSectionPageAdapter ;

    private ViewPager mViewPager;



    RingProgress mRingProgress ;
    List<Ring> mlistRing = new ArrayList<>();
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        mRingProgress = (RingProgress) findViewById(R.id.ring_progress);
        setData();

        mSectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        //set up the viewpager with the sections adapter
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        BubbleTab tabLayout =  findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }


    private String[] data = new String[]{"Goal", "So far"};


    private void setData() {
        mlistRing.clear();
        for (int i = 0; i < data.length; i++) {

            Ring r = new Ring();
            int p = random.nextInt(100);
            r.setProgress(p);
            r.setValue(String.valueOf(p * 100));

            r.setName(data[i]);


            if (i == 0) {
                r.setProgress(100);
                r.setValue(String.valueOf(2550));
                r.setStartColor(Color.rgb(235, 79, 56));
                r.setEndColor(Color.argb(100, 235, 79, 56));
            }
            if (i == 1) {
                r.setStartColor(Color.rgb(17, 205, 110));
                r.setEndColor(Color.argb(100, 17, 205, 110));
            }
            mlistRing.add(r);

        }
        mRingProgress.setData(mlistRing, 500);

    }

    private void setupViewPager(ViewPager viewPager){
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new TabProjectCampaignFragment(), "Campaign");
        adapter.addFragment(new TabProjectCommunityFragment(), "Community");
        adapter.addFragment(new TabProjectCommentsFragment(), "Comments");
        viewPager.setAdapter(adapter);
    }

}
