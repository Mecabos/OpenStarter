package com.esprit.pixelCells.openstarter.Fragments;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.esprit.pixelCells.openstarter.Activities.ProjectActivity;
import com.esprit.pixelCells.openstarter.Data.CustomClasses.ProjectWithFollowCount;
import com.esprit.pixelCells.openstarter.Helpers.TimeHelper;
import com.esprit.pixelCells.openstarter.Helpers.Util.ServerConnection;
import com.esprit.pixelCells.openstarter.R;

import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * Created by Bacem on 11/27/2017.
 */

public class TabProjectCampaignFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    private static final String TAG = "TabProjectCampaignFragment";
    private SliderLayout mCampaignSlider;

    private TextView tvTimer ;
    private TextView tvDate;
    private TextView tvDescription;
    private ProjectWithFollowCount mProject = new ProjectWithFollowCount() ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_project_campaign_fragment,container,false) ;

        initializeFragment(view);

        return  view ;

    }

    private void initializeFragment(View view) {

        mProject = ((ProjectActivity)getActivity()).getProject() ;

        tvTimer = view.findViewById(R.id.tv_timer);
        tvTimer.setText(TimeHelper.getTimeLeft(mProject.getStartDate(),mProject.getFinishDate())+" Left");

        tvDate = view.findViewById(R.id.tv_date);
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy/MM/dd");
        tvDate.setText("Started : "+dayFormat.format(mProject.getStartDate())+ " Ends : "+ dayFormat.format(mProject.getFinishDate()));


        tvDescription = view.findViewById(R.id.tv_description);
        tvDescription.setText(mProject.getDescription());

        initializeSlider (view);
    }

    //*****************Slider*****************
    private void initializeSlider (View view){

        mCampaignSlider = view.findViewById(R.id.campaignSlider);

        HashMap<String,String> url_maps = new HashMap<String, String>();

        ServerConnection sc = new ServerConnection();
        if(mProject.getId() == 1){
            url_maps.put("Fantasia", sc.SERVER_IP + "perfume2.jpg");
            url_maps.put("Madness", sc.SERVER_IP + "perfume3.jpg");
            url_maps.put("Dare", sc.SERVER_IP + "perfume4.jpg");
        }else if (mProject.getId() == 7){
            url_maps.put("Design 1", sc.SERVER_IP + "bikes2.jpg");
            url_maps.put("Design 2", sc.SERVER_IP + "bikes3.jpg");
            url_maps.put("Design 3", sc.SERVER_IP + "bikes4.jpg");
        }else if (mProject.getId() == 2){
            url_maps.put("Easy and Pleasant to use", sc.SERVER_IP + "monivulation2.jpg");
            url_maps.put("Accessible anywhere", sc.SERVER_IP + "monivulation3.jpg");
            url_maps.put("The doctors approve of it", sc.SERVER_IP + "monivulation4.jpg");
        }else if (mProject.getId() == 2){
            url_maps.put("Example01", sc.SERVER_IP + "project02.jpg");
            url_maps.put("Example02", sc.SERVER_IP + "project03.jpg");
            url_maps.put("Example03", sc.SERVER_IP + "project04.jpg");
        }






        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mCampaignSlider.addSlider(textSliderView);
        }

        mCampaignSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mCampaignSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mCampaignSlider.stopAutoCycle();
        mCampaignSlider.setCustomAnimation(new DescriptionAnimation());
        mCampaignSlider.addOnPageChangeListener(this);
    }

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mCampaignSlider.stopAutoCycle();
        super.onStop();
    }


    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getActivity(),slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    //****************************************
}
