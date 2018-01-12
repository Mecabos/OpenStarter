package com.example.mohamed.openstarter.Data.CustomClasses;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.example.mohamed.openstarter.Models.Category;
import com.example.mohamed.openstarter.Models.CollaborationGroup;
import com.example.mohamed.openstarter.Models.Project;

import org.greenrobot.greendao.annotation.Keep;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Bacem on 1/11/2018.
 */

public class ProjectWithFollowCount  implements Parcelable {

    private long id;
    private String name;
    private Date creationDate;
    private Date startDate;
    private Date finishDate;
    private String description;
    private String shortDescription;
    private float budget;
    private float currentBudget;
    private String equipmentsList;
    private String servicesList;
    private CollaborationGroup collaborationGroup;
    private Category category;
    private Integer followCount;

    private View.OnClickListener requestBtnClickListener;

    public ProjectWithFollowCount() {
    }

    public ProjectWithFollowCount(long id, String name, Date creationDate, Date startDate, Date finishDate, String description, String shortDescription, float budget, float currentBudget, String equipmentsList, String servicesList, CollaborationGroup collaborationGroup, Category category, Integer followCount, View.OnClickListener requestBtnClickListener) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.description = description;
        this.shortDescription = shortDescription;
        this.budget = budget;
        this.currentBudget = currentBudget;
        this.equipmentsList = equipmentsList;
        this.servicesList = servicesList;
        this.collaborationGroup = collaborationGroup;
        this.category = category;
        this.followCount = followCount;
        this.requestBtnClickListener = requestBtnClickListener;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public float getCurrentBudget() {
        return currentBudget;
    }

    public void setCurrentBudget(float currentBudget) {
        this.currentBudget = currentBudget;
    }

    public String getEquipmentsList() {
        return equipmentsList;
    }

    public void setEquipmentsList(String equipmentsList) {
        this.equipmentsList = equipmentsList;
    }

    public String getServicesList() {
        return servicesList;
    }

    public void setServicesList(String servicesList) {
        this.servicesList = servicesList;
    }

    public CollaborationGroup getCollaborationGroup() {
        return collaborationGroup;
    }

    public void setCollaborationGroup(CollaborationGroup collaborationGroup) {
        this.collaborationGroup = collaborationGroup;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getFollowCount() {
        return followCount;
    }

    public void setFollowCount(Integer followCount) {
        this.followCount = followCount;
    }


    public View.OnClickListener getRequestBtnClickListener() {
        return requestBtnClickListener;
    }

    public void setRequestBtnClickListener(View.OnClickListener requestBtnClickListener) {
        this.requestBtnClickListener = requestBtnClickListener;
    }

    //*************Parcel part

    public ProjectWithFollowCount(Parcel in) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String[] data = new String[10];
        in.readStringArray(data);

        this.id = Long.parseLong(data[0]);
        this.name = data[1];
        this.creationDate = dateFormat.parse(data[2]);
        this.startDate = dateFormat.parse(data[3]);
        this.finishDate = dateFormat.parse(data[4]);
        this.description = data[5];
        this.shortDescription = data[6];
        this.budget = Float.parseFloat(data[7]);
        this.currentBudget = Float.parseFloat(data[8]);
        this.followCount = Integer.parseInt(data[9]);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        parcel.writeStringArray(new String[]{String.valueOf(this.id),
                this.name,
                dateFormat.format(this.creationDate),
                dateFormat.format(this.startDate),
                dateFormat.format(this.finishDate),
                this.description,
                this.shortDescription,
                String.valueOf(this.budget),
                String.valueOf(this.currentBudget),
                String.valueOf(this.followCount),
        });
    }

    public static final Parcelable.Creator<ProjectWithFollowCount> CREATOR = new Parcelable.Creator<ProjectWithFollowCount>() {

        @Override
        public ProjectWithFollowCount createFromParcel(Parcel source) {
            ProjectWithFollowCount projectWithFollowCount = new ProjectWithFollowCount();
            try {
                projectWithFollowCount = new ProjectWithFollowCount(source);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return (projectWithFollowCount);
        }

        @Override
        public ProjectWithFollowCount[] newArray(int size) {

            return new ProjectWithFollowCount[size];
        }
    };

    //********************* Filtering

    //Category
    public static List<String> getUniqueCategoryKeys(List<ProjectWithFollowCount> projectsList) {
        List<String> categoriesList = new ArrayList<>();
        for (ProjectWithFollowCount project : projectsList) {
            if (!categoriesList.contains(project.getCategory().getLabel())) {
                categoriesList.add(project.getCategory().getLabel());
            }
        }
        Collections.sort(categoriesList);
        return categoriesList;
    }

    public static List<String> getUniqueFollowCountKeys(List<ProjectWithFollowCount> projectsList) {
        List<String> followMileStonesList = new ArrayList<>();
        for (ProjectWithFollowCount project : projectsList) {
            Integer temp = 5*((int)Math.floor(project.getFollowCount()/5));
            String rate = "> " + temp;
            if (!followMileStonesList.contains(rate)) {
                followMileStonesList.add(rate);
            }
        }
        Collections.sort(followMileStonesList);
        return followMileStonesList;
    }

    public static List<String> getUniqueGoalReachMileStoneKeys(List<ProjectWithFollowCount> projectsList) {
        List<String> goalReachMileStonesList = new ArrayList<>();
        for (ProjectWithFollowCount project : projectsList) {
            float reachedBudget = (project.getCurrentBudget()*100)/ project.getBudget() ;
            Integer temp = 25*((int)Math.floor(reachedBudget/25));
            String rate = "> " + temp + "%";
            if (!goalReachMileStonesList.contains(rate)) {
                goalReachMileStonesList.add(rate);
            }
        }
        Collections.sort(goalReachMileStonesList);
        return goalReachMileStonesList;
    }

    public static List<ProjectWithFollowCount> getCategoryFilteredProjects(List<String> categories, List<ProjectWithFollowCount> projectsList) {
        List<ProjectWithFollowCount> tempList = new ArrayList<>();
        for (ProjectWithFollowCount project : projectsList) {
            for (String c : categories) {
                if (project.getCategory().getLabel().equalsIgnoreCase(c)) {
                    tempList.add(project);
                }
            }
        }
        return tempList;
    }

    public static List<ProjectWithFollowCount> getFollowCountFilteredProjects(List<String> followCount, List<ProjectWithFollowCount> projectsList) {
        List<ProjectWithFollowCount> tempList = new ArrayList<>();
        for (ProjectWithFollowCount project : projectsList) {
            for (String fc : followCount) {
                if (project.getFollowCount() >= Float.parseFloat(fc.replace(">",""))) {
                    tempList.add(project);
                }
            }
        }
        return tempList;
    }

    public static List<ProjectWithFollowCount> getGoalReachFilteredProjects(List<String> goalReach, List<ProjectWithFollowCount> projectsList) {
        List<ProjectWithFollowCount> tempList = new ArrayList<>();
        for (ProjectWithFollowCount project : projectsList) {
            for (String fc : goalReach) {
                float reachedBudget = (project.getCurrentBudget()*100)/ project.getBudget() ;
                if (reachedBudget >= Float.parseFloat((fc.replace(">","")).replace("%",""))) {
                    tempList.add(project);
                }
            }
        }
        return tempList;
    }



}
