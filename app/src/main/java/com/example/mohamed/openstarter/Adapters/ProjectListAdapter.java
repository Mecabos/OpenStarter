package com.example.mohamed.openstarter.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mohamed.openstarter.Activities.ProjectActivity;
import com.example.mohamed.openstarter.Data.CustomClasses.ProjectWithFollowCount;
import com.example.mohamed.openstarter.Helpers.NumbersHelper;
import com.example.mohamed.openstarter.Helpers.TimeHelper;
import com.example.mohamed.openstarter.R;
import com.example.mohamed.openstarter.foldingcell.FoldingCell;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */
public class ProjectListAdapter extends ArrayAdapter<ProjectWithFollowCount> {

    private static String PROJECT_TAG = "project" ;
    private static String COLLABORATION_GROUP_TAG = "collaboration group" ;
    private static String CATEGORY_TAG = "category" ;
    private static String FOLLOW_COUNT_TAG = "follow count" ;

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;
    private List<ProjectWithFollowCount> projectsList = null;

    public ProjectListAdapter(Context context, List<ProjectWithFollowCount> projectsList) {
        super(context, 0, projectsList);
        this.projectsList = projectsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // get item for selected view
        final ProjectWithFollowCount project = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell, parent, false);
            // binding view parts to view holder
            viewHolder.titleProjectName = cell.findViewById(R.id.title_project_name);
            viewHolder.contentProjectName = cell.findViewById(R.id.content_project_name);
            viewHolder.titleRemainingBudget = cell.findViewById(R.id.title_remaining_budget);
            viewHolder.contentReachedBudget = cell.findViewById(R.id.content_reached_budget);
            viewHolder.titleContributorsCount = cell.findViewById(R.id.title_contributors_count);
            viewHolder.contentContributorsCount = cell.findViewById(R.id.content_contributors_count);
            viewHolder.titlePledgesSum = cell.findViewById(R.id.title_pledges_sum);
            viewHolder.contentPledgesSum = cell.findViewById(R.id.content_pledges_sum);
            viewHolder.titleGoal = cell.findViewById(R.id.title_goal_value);
            viewHolder.contentGoal = cell.findViewById(R.id.content_goal_value);
            viewHolder.titleEndTime = cell.findViewById(R.id.title_end_time);
            viewHolder.titleEndDay = cell.findViewById(R.id.title_end_day);
            viewHolder.contentFromTime = cell.findViewById(R.id.content_from_date_time);
            viewHolder.contentFromDay = cell.findViewById(R.id.content_from_date_day);
            viewHolder.contentToTime = cell.findViewById(R.id.content_to_date_time);
            viewHolder.contentToDay = cell.findViewById(R.id.content_to_date_day);
            viewHolder.titleShortDescription = cell.findViewById(R.id.title_project_short_description);
            viewHolder.contentShortDescription = cell.findViewById(R.id.content_project_short_description);
            viewHolder.contentDeadLine = cell.findViewById(R.id.content_deadline);
            viewHolder.titleSideBar = cell.findViewById(R.id.title_side_bar) ;


            viewHolder.contentRequestBtn = cell.findViewById(R.id.content_request_btn);
            cell.setTag(viewHolder);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        //processing data
            //Calculating remaining budget
        float remainingBudget = project.getBudget() - project.getCurrentBudget() ;
        remainingBudget = NumbersHelper.round(remainingBudget, 2);
            //Calculating reached budget
        float reachedBudget = (project.getCurrentBudget()*100)/ project.getBudget() ;
        reachedBudget = NumbersHelper.round(reachedBudget, 2);
            //Formatting Current Budget
        Double currentBudget = BigDecimal.valueOf(project.getCurrentBudget()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            //Formatting Budget
        Double budget = BigDecimal.valueOf(project.getBudget()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            //Getting time from finish date
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String timefromDate1 = timeFormat.format(project.getStartDate());
        String timefromDate2 = timeFormat.format(project.getFinishDate());
            //Getting day from finish date
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy/MM/dd");
        String dayFromDate1 = dayFormat.format(project.getStartDate());
        String dayFromDate2 = dayFormat.format(project.getFinishDate());

            // setting the btn request
        project.setRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getContext(), ProjectActivity.class);
                myIntent.putExtra(PROJECT_TAG,project);
                myIntent.putExtra(COLLABORATION_GROUP_TAG,project.getCollaborationGroup());
                myIntent.putExtra(CATEGORY_TAG,project.getCategory());
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getContext().startActivity(myIntent);
            }
        });

        viewHolder.titleProjectName.setText(project.getName());
        viewHolder.contentProjectName.setText(project.getName());
        viewHolder.titleRemainingBudget.setText("$"+remainingBudget);
        viewHolder.contentReachedBudget.setText(reachedBudget+" %");
        viewHolder.titleContributorsCount.setText(String.valueOf(project.getFollowCount()));
        viewHolder.contentContributorsCount.setText(String.valueOf(project.getFollowCount()));
        viewHolder.titlePledgesSum.setText("$"+ currentBudget);
        viewHolder.contentPledgesSum.setText("$"+ currentBudget);
        viewHolder.titleGoal.setText("$"+ budget);
        viewHolder.contentGoal.setText("$"+ budget);
        viewHolder.titleEndTime.setText(timefromDate2);
        viewHolder.titleEndDay.setText(dayFromDate2);
        viewHolder.contentFromTime.setText(timefromDate1);
        viewHolder.contentFromDay.setText(dayFromDate1);
        viewHolder.contentToTime.setText(timefromDate2);
        viewHolder.contentToDay.setText(dayFromDate2);
        viewHolder.titleShortDescription.setText(project.getShortDescription());
        viewHolder.contentShortDescription.setText(project.getShortDescription());
        viewHolder.titleShortDescription.setText(project.getShortDescription());
        viewHolder.contentDeadLine.setText(TimeHelper.getTimeLeft(project.getStartDate(),project.getFinishDate()));
        viewHolder.titleSideBar.setBackgroundColor(Color.parseColor(project.getCategory().getColor()));



        // set custom btn handler for list project from that project
        if (project.getRequestBtnClickListener() != null) {
            viewHolder.contentRequestBtn.setOnClickListener(project.getRequestBtnClickListener());
        } else {
            // (optionally) add "default" handler if no handler found in project
            viewHolder.contentRequestBtn.setOnClickListener(defaultRequestBtnClickListener);
        }


        return cell;
    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public View.OnClickListener getDefaultRequestBtnClickListener() {
        return defaultRequestBtnClickListener;
    }

    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
    }

    // View lookup cache
    private static class ViewHolder {
        //title elements
        TextView titleProjectName;
        TextView titleRemainingBudget;
        TextView titleContributorsCount;
        TextView titlePledgesSum;
        TextView titleGoal;
        TextView titleEndDay;
        TextView titleEndTime;
        RelativeLayout titleSideBar;
        TextView titleShortDescription;
        //content elements
        TextView contentProjectName;
        TextView contentReachedBudget;
        TextView contentContributorsCount;
        TextView contentPledgesSum;
        TextView contentGoal;
        TextView contentFromDay;
        TextView contentFromTime;
        TextView contentToDay;
        TextView contentToTime;
        TextView contentShortDescription;
        TextView contentDeadLine;


        TextView contentRequestBtn;

    }

    public void refreshProjects(List<ProjectWithFollowCount> newProjectsList) {

        //must be stored in a temp list to be able to add the elemets
        List<ProjectWithFollowCount> tempList = new ArrayList<>(newProjectsList);
        this.projectsList.clear();
        this.projectsList.addAll(tempList);
        notifyDataSetChanged();
    }
}
