package com.example.mohamed.openstarter.Models;

import android.view.View;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

import java.util.ArrayList;
import java.util.Date;

@Entity
public class Project {

    //****Persistent properties
    @Id(autoincrement = true)
    private long id;
    @Property
    private String name;
    @Property
    private Date creationDate;
    @Property
    private Date startDate;
    @Property
    private Date finishDate;
    @Property
    private String description;
    @Property
    private String shortDescription;
    @Property
    private float budget;
    @Property
    private float currentBudget;
    @Property
    private String equipmentsList;
    @Property
    private String servicesList;


    @Transient
    private View.OnClickListener requestBtnClickListener;

    public Project() {
    }

    @Generated(hash = 863198504)
    public Project(long id, String name, Date creationDate, Date startDate, Date finishDate, String description, String shortDescription,
            float budget, float currentBudget, String equipmentsList, String servicesList) {
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
    }

    public Project(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public String getDescription() {
        return description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public float getBudget() {
        return budget;
    }

    public float getCurrentBudget() {
        return currentBudget;
    }

    public String getEquipmentsList() {
        return equipmentsList;
    }

    public String getServicesList() {
        return servicesList;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public void setCurrentBudget(float currentBudget) {
        this.currentBudget = currentBudget;
    }

    public void setEquipmentsList(String equipmentsList) {
        this.equipmentsList = equipmentsList;
    }

    public void setServicesList(String servicesList) {
        this.servicesList = servicesList;
    }


    public View.OnClickListener getRequestBtnClickListener() {
        return requestBtnClickListener;
    }

    public void setRequestBtnClickListener(View.OnClickListener requestBtnClickListener) {
        this.requestBtnClickListener = requestBtnClickListener;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;

        Project project = (Project) o;

        if (getId() != project.getId()) return false;
        if (!getName().equals(project.getName())) return false;
        return getCreationDate() != null ? getCreationDate().equals(project.getCreationDate()) : project.getCreationDate() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getName().hashCode();
        result = 31 * result + (getCreationDate() != null ? getCreationDate().hashCode() : 0);
        return result;
    }

}