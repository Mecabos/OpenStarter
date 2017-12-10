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
import android.widget.Toast;

import com.example.mohamed.openstarter.Activities.MainActivity;
import com.example.mohamed.openstarter.Activities.ProjectActivity;
import com.example.mohamed.openstarter.Helpers.NumbersHelper;
import com.example.mohamed.openstarter.Models.Project;
import com.example.mohamed.openstarter.R;
import com.example.mohamed.openstarter.foldingcell.FoldingCell;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;

/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */
public class ProjectListAdapter extends ArrayAdapter<Project> {

    private static String PROJECT_TAG = "project" ;
    private static String COLLABORATION_GROUP_TAG = "collaboration group" ;
    private static String CATEGORY_TAG = "category" ;


    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;


    public ProjectListAdapter(Context context, List<Project> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get item for selected view
        final Project project = getItem(position);
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
            //Getting deadline
        Long secs = (project.getFinishDate().getTime() - project.getStartDate().getTime()) / 1000;
        Long days = secs / 86400;
        secs = secs % 86400;
        Long hours = secs / 3600;
        secs = secs % 3600;
        Long mins = secs / 60;
        secs = secs % 60;
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
        viewHolder.titleContributorsCount.setText("241");
        viewHolder.contentContributorsCount.setText("241");
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
        viewHolder.contentDeadLine.setText(days.toString()+ "Days "+ hours.toString()+" Hours "+ mins + "Minutes and " + secs + "seconds");

        RelativeLayout sideBar = cell.findViewById(R.id.title_side_bar) ;
        sideBar.setBackgroundColor(Color.parseColor(project.getCategory().getColor()));


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
}
