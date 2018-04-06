package com.esprit.pixelCells.openstarter.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.esprit.pixelCells.openstarter.Adapters.SectionPageAdapter;
import com.esprit.pixelCells.openstarter.Data.CustomClasses.ProjectWithFollowCount;
import com.esprit.pixelCells.openstarter.Data.DataSuppliers.FollowServer;
import com.esprit.pixelCells.openstarter.Fragments.TabProjectCampaignFragment;
import com.esprit.pixelCells.openstarter.Fragments.TabProjectCommentsFragment;
import com.esprit.pixelCells.openstarter.Fragments.TabProjectCommunityFragment;
import com.esprit.pixelCells.openstarter.Helpers.DialogHelper;
import com.esprit.pixelCells.openstarter.Data.CustomClasses.FollowCount;
import com.esprit.pixelCells.openstarter.Models.Category;
import com.esprit.pixelCells.openstarter.Models.CollaborationGroup;
import com.esprit.pixelCells.openstarter.Models.Follow;
import com.esprit.pixelCells.openstarter.R;
import com.github.florent37.bubbletab.BubbleTab;
import com.google.firebase.auth.FirebaseAuth;
import com.ldoublem.thumbUplib.ThumbUpView;
import com.vlstr.blurdialog.BlurDialog;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProjectActivity extends AppCompatActivity {

    private static final String TAG = "ProjectActivity";
    private static String PROJECT_TAG = "project";
    private static String COLLABORATION_GROUP_TAG = "collaboration group";
    private static String CATEGORY_TAG = "category";

    private ProjectWithFollowCount mProject = new ProjectWithFollowCount();
    private CollaborationGroup mCollaborationGroup = new CollaborationGroup();
    private Category mCategory = new Category();
    private FollowServer followDs;
    private DialogHelper dialogHelper = new DialogHelper() ;
    public static ProjectActivity instance = null;
    private FirebaseAuth firebaseAuth;


    private SectionPageAdapter mSectionPageAdapter;
    private TextView tvProjectName;
    private TextView tvProjectGroup;
    private TextView tvProjectCategory;
    private ViewPager mViewPager;
    private LinearLayout categoryLayout;
    private ThumbUpView btnFollow;
    private TextView tvFollowersCount;
    private BlurDialog blurDialog;
    private ImageView projectHeaderImage ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        instance = this ;

        blurDialog = findViewById(R.id.blurLoader);
        dialogHelper.blurDialogShow(instance,blurDialog,"Loading project");
        initializeActivity();

    }

    private void initializeActivity() {

        firebaseAuth = FirebaseAuth.getInstance();
        setProject();
        setCollaborationGroup();
        setCategory();
        followDs = new FollowServer();

        setTabs();
        setFollow();

        categoryLayout = findViewById(R.id.project_category_layout);
        GradientDrawable categoryBg = (GradientDrawable) categoryLayout.getBackground();
        categoryBg.setColor(Color.parseColor(mCategory.getColor()));

        //The page text fields
        tvProjectName = findViewById(R.id.project_name);
        tvProjectName.setText(mProject.getName());
        tvProjectGroup = findViewById(R.id.project_founder_group);
        tvProjectGroup.setText(mCollaborationGroup.getName());
        tvProjectCategory = findViewById(R.id.project_category);
        tvProjectCategory.setText(mCategory.getLabel());

        projectHeaderImage = findViewById(R.id.project_header_image);
        if (mProject.getId() == 7)
            projectHeaderImage.setImageResource(R.drawable.bikes1);
        else if (mProject.getId() == 2)
            projectHeaderImage.setImageResource(R.drawable.monivulation1);
        else if (mProject.getId() == 1)
            projectHeaderImage.setImageResource(R.drawable.perfume1);
        else
            projectHeaderImage.setImageResource(R.drawable.placeholderproject);

    }

    private void setTabs() {

        mSectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);
        BubbleTab tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new TabProjectCampaignFragment(), "Campaign");
        adapter.addFragment(new TabProjectCommunityFragment(), "Community");
        adapter.addFragment(new TabProjectCommentsFragment(), "Comments");
        viewPager.setAdapter(adapter);
    }

    private void setFollow() {

        tvFollowersCount = findViewById(R.id.project_followers_count);
        btnFollow = findViewById(R.id.project_follow_btn);

        followDs.followCount(firebaseAuth.getCurrentUser().getEmail(), String.valueOf(mProject.getId()), new FollowServer.Callback() {

            @Override
            public void onSuccessCount(FollowCount followCount) {
                if (followCount.isHasFollowed()) btnFollow.setLike();
                else btnFollow.setUnlike();
                tvFollowersCount.setText(String.valueOf(followCount.getFollowsCount()));
                //put here as it's the last request to be made at the project page loading
                dialogHelper.blurDialogHide(instance,blurDialog);
            }

            @Override
            public void onSuccessGet(ArrayList<Follow> result) {
            }

            @Override
            public void onSuccessCreate(Follow createdFollow) {
            }

            @Override
            public void onSuccess() {
            }

        });

        btnFollow.setOnThumbUp(new ThumbUpView.OnThumbUp() {
            @Override
            public void like(boolean like) {
                if (like) {
                    dialogHelper.blurDialogShow(instance,blurDialog,"Following");
                    followDs.followCreate(firebaseAuth.getCurrentUser().getEmail(), String.valueOf(mProject.getId()), new FollowServer.Callback() {

                        @Override
                        public void onSuccessCreate(Follow createdFollow) {
                            Toast.makeText(ProjectActivity.this, "Project Followed", Toast.LENGTH_LONG).show();
                            tvFollowersCount.setText(String.valueOf(Integer.valueOf(tvFollowersCount.getText().toString()) + 1));
                            dialogHelper.blurDialogHide(instance,blurDialog);
                        }

                        @Override
                        public void onSuccessGet(ArrayList<Follow> result) {
                        }

                        @Override
                        public void onSuccessCount(FollowCount followCount) {
                        }

                        @Override
                        public void onSuccess() {
                        }

                    });
                } else {
                    dialogHelper.blurDialogShow(instance,blurDialog,"Unfollowing");
                    followDs.followDelete(firebaseAuth.getCurrentUser().getEmail(), String.valueOf(mProject.getId()), new FollowServer.Callback() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(ProjectActivity.this, "Project Unfollowed", Toast.LENGTH_LONG).show();
                            tvFollowersCount.setText(String.valueOf(Integer.valueOf(tvFollowersCount.getText().toString()) - 1));
                            dialogHelper.blurDialogHide(instance,blurDialog);
                        }

                        @Override
                        public void onSuccessGet(ArrayList<Follow> result) {
                        }

                        @Override
                        public void onSuccessCreate(Follow createdFollow) {
                        }

                        @Override
                        public void onSuccessCount(FollowCount followCount) {

                        }

                    });
                }
            }
        });
    }

    public ProjectWithFollowCount getProject() {
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
