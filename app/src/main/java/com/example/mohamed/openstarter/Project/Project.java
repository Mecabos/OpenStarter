package com.example.mohamed.openstarter.Project;

import android.view.View;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private Double budget;
    @Property
    private Double currentBudget;
    @Property
    private String equipmentsList;
    @Property
    private String servicesList;

    //****Transient properties
    @Transient
    private String price;
    @Transient
    private String pledgePrice;
    @Transient
    private String fromAddress;
    @Transient
    private String toAddress;
    @Transient
    private int requestsCount;
    @Transient
    private String date;
    @Transient
    private String time;

    @Transient
    private View.OnClickListener requestBtnClickListener;

    public Project() {
    }

    public Project(String price, String pledgePrice, String fromAddress, String toAddress, int requestsCount, String date, String time) {
        this.price = price;
        this.pledgePrice = pledgePrice;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.requestsCount = requestsCount;
        this.date = date;
        this.time = time;
    }

    @Generated(hash = 206272926)
    public Project(long id, String name, Date creationDate, Date startDate, Date finishDate, String description, String shortDescription,
                   Double budget, Double currentBudget, String equipmentsList, String servicesList) {
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

    public Project(String name, Double budget) {
        this.name = name;
        this.budget = budget;
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

    public Double getBudget() {
        return budget;
    }

    public Double getCurrentBudget() {
        return currentBudget;
    }

    public String getEquipmentsList() {
        return equipmentsList;
    }

    public String getServicesList() {
        return servicesList;
    }

    public String getPrice() {
        return price;
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

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public void setCurrentBudget(Double currentBudget) {
        this.currentBudget = currentBudget;
    }

    public void setEquipmentsList(String equipmentsList) {
        this.equipmentsList = equipmentsList;
    }

    public void setServicesList(String servicesList) {
        this.servicesList = servicesList;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPledgePrice() {
        return pledgePrice;
    }

    public void setPledgePrice(String pledgePrice) {
        this.pledgePrice = pledgePrice;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public int getRequestsCount() {
        return requestsCount;
    }

    public void setRequestsCount(int requestsCount) {
        this.requestsCount = requestsCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (requestsCount != project.requestsCount) return false;
        if (price != null ? !price.equals(project.price) : project.price != null) return false;
        if (pledgePrice != null ? !pledgePrice.equals(project.pledgePrice) : project.pledgePrice != null)
            return false;
        if (fromAddress != null ? !fromAddress.equals(project.fromAddress) : project.fromAddress != null)
            return false;
        if (toAddress != null ? !toAddress.equals(project.toAddress) : project.toAddress != null)
            return false;
        if (date != null ? !date.equals(project.date) : project.date != null) return false;
        return !(time != null ? !time.equals(project.time) : project.time != null);

    }

    @Override
    public int hashCode() {
        int result = price != null ? price.hashCode() : 0;
        result = 31 * result + (pledgePrice != null ? pledgePrice.hashCode() : 0);
        result = 31 * result + (fromAddress != null ? fromAddress.hashCode() : 0);
        result = 31 * result + (toAddress != null ? toAddress.hashCode() : 0);
        result = 31 * result + requestsCount;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    /**
     * @return List of elements prepared for tests
     */
    public static ArrayList<Project> getTestingList() {
        ArrayList<Project> projects = new ArrayList<>();
        projects.add(new Project("$14", "$270", "W 79th St, NY, 10024", "W 139th St, NY, 10030", 3, "TODAY", "05:10 PM"));
        projects.add(new Project("$23", "$116", "W 36th St, NY, 10015", "W 114th St, NY, 10037", 10, "TODAY", "11:10 AM"));
        projects.add(new Project("$63", "$350", "W 36th St, NY, 10029", "56th Ave, NY, 10041", 0, "TODAY", "07:11 PM"));
        projects.add(new Project("$19", "$150", "12th Ave, NY, 10012", "W 57th St, NY, 10048", 8, "TODAY", "4:15 AM"));
        projects.add(new Project("$5", "$300", "56th Ave, NY, 10041", "W 36th St, NY, 10029", 0, "TODAY", "06:15 PM"));
        return projects;

    }
    /*
    public List<Project> findAllItems() {
        JSONObject serviceResult = ConnectionUtilTask.requestWebService(
                "http://localhost/androidws/web/app_dev.php/project/getAll");

        List<Project> foundItems = new ArrayList<Project>(20);

        try {
            JSONArray items = serviceResult.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                JSONObject obj = items.getJSONObject(i);
                foundItems.add(
                        new Project(obj.getString("name"),
                                obj.getDouble("budget")));
            }

        } catch (JSONException e) {
            // handle exception
        }

        return foundItems;
    }*/

}