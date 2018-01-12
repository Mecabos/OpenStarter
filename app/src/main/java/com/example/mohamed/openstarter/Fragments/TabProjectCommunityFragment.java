package com.example.mohamed.openstarter.Fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.openstarter.Activities.PaymentActivity;
import com.example.mohamed.openstarter.Activities.ProjectActivity;
import com.example.mohamed.openstarter.Adapters.ContributionListAdapter;
import com.example.mohamed.openstarter.Data.CustomClasses.ContributionsWithUsers;
import com.example.mohamed.openstarter.Data.CustomClasses.ProjectWithFollowCount;
import com.example.mohamed.openstarter.Data.DataSuppliers.ProjectServer;
import com.example.mohamed.openstarter.R;
import com.ldoublem.ringPregressLibrary.Ring;
import com.ldoublem.ringPregressLibrary.RingProgress;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Bacem on 11/27/2017.
 */

public class TabProjectCommunityFragment extends Fragment {

    RingProgress mRingProgress ;
    Button btn_contribute;
    TextView project_contributors_count;
    ListView contributions;
    List<Ring> mlistRing = new ArrayList<>();
    Random random = new Random();
    ProjectWithFollowCount mProject = new ProjectWithFollowCount() ;

    private static final String TAG = "TabProjectCommunityFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_project_community_fragment,container,false) ;
        initializeFragment(view);
        btn_contribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PaymentActivity.class);
                intent.putExtra("projectId",String.valueOf(mProject.getId()));
                startActivity(intent);
            }
        });

        ProjectServer projectServer = new ProjectServer();
        projectServer.projectGetContributionsWithUsers(String.valueOf(mProject.getId()), new ProjectServer.CallbackGetContributions() {
            @Override
            public void onSuccessGet(List<ContributionsWithUsers> result) {
                if (result.size()>0){
                    project_contributors_count.setText(result.size()+" "+project_contributors_count.getText().toString());
                    Log.d("contribs",result.toString());
                    contributions.setAdapter(new ContributionListAdapter(getContext(),R.layout.item_contribution,result));
                }
                else
                    project_contributors_count.setText("0 "+project_contributors_count.getText().toString());
            }

            @Override
            public void onFail() {
                Toast.makeText(getActivity(), "could not load project info", Toast.LENGTH_LONG).show();
            }
        });

        return  view ;

    }

    private void initializeFragment (View view){
        mProject = ((ProjectActivity)getActivity()).getProject() ;
        mRingProgress = view.findViewById(R.id.ring_progress);
        btn_contribute = view.findViewById(R.id.btn_contribute);
        project_contributors_count = view.findViewById(R.id.project_contributors_count);
        contributions =  view.findViewById(R.id.contributions);
        setData();
    }

    private void setData() {
        String[] data = new String[]{"Goal", "So far"};
        mlistRing.clear();
        for (int i = 0; i < data.length; i++) {
            Ring r = new Ring();
            r.setName(data[i]);
            if (i == 0) {
                r.setProgress(100);
                r.setValue(String.valueOf(mProject.getBudget()));
                r.setStartColor(Color.rgb(235, 79, 56));
                r.setEndColor(Color.argb(100, 235, 79, 56));
            }
            if (i == 1) {
                int reachedBudget = Math.round((mProject.getCurrentBudget()*100)/ mProject.getBudget()) ;
                r.setProgress(reachedBudget);
                r.setValue(String.valueOf(mProject.getCurrentBudget()));
                r.setStartColor(Color.rgb(17, 205, 110));
                r.setEndColor(Color.argb(100, 17, 205, 110));
            }
            mlistRing.add(r);

        }
        mRingProgress.setData(mlistRing, 500);

    }
}
