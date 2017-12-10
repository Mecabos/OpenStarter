package com.example.mohamed.openstarter.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class Project implements Parcelable {

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
    @Property
    @ToOne(joinProperty = "id")
    private CollaborationGroup collaborationGroup;
    @Property
    @ToOne(joinProperty = "id")
    private Category category;


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

    //*************Parcel part
    @Keep
    public Project(Parcel in) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String[] data = new String[9];
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
        });
    }

    /** To-one relationship, resolved on first access. */
    @Keep
    public CollaborationGroup getCollaborationGroup() {
        long __key = this.id;
        if (collaborationGroup__resolvedKey == null || !collaborationGroup__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                if (this.collaborationGroup != null) return  this.collaborationGroup ;
                throw new DaoException("Entity is detached from DAO context");
            }
            CollaborationGroupDao targetDao = daoSession.getCollaborationGroupDao();
            CollaborationGroup collaborationGroupNew = targetDao.load(__key);
            synchronized (this) {
                collaborationGroup = collaborationGroupNew;
                collaborationGroup__resolvedKey = __key;
            }
        }
        return collaborationGroup;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 246631676)
    public void setCollaborationGroup(@NotNull CollaborationGroup collaborationGroup) {
        if (collaborationGroup == null) {
            throw new DaoException("To-one property 'id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.collaborationGroup = collaborationGroup;
            id = collaborationGroup.getId();
            collaborationGroup__resolvedKey = id;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** To-one relationship, resolved on first access. */
    @Keep
    public Category getCategory() {
        long __key = this.id;
        if (category__resolvedKey == null || !category__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                if (this.category != null) return  this.category ;
                throw new DaoException("Entity is detached from DAO context");
            }
            CategoryDao targetDao = daoSession.getCategoryDao();
            Category categoryNew = targetDao.load(__key);
            synchronized (this) {
                category = categoryNew;
                category__resolvedKey = __key;
            }
        }
        return category;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 288529823)
    public void setCategory(@NotNull Category category) {
        if (category == null) {
            throw new DaoException("To-one property 'id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.category = category;
            id = category.getId();
            category__resolvedKey = id;
        }
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2081800561)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getProjectDao() : null;
    }

    public static final Parcelable.Creator<Project> CREATOR = new Parcelable.Creator<Project>() {

        @Override
        public Project createFromParcel(Parcel source) {
            Project project = new Project();
            try {
                project = new Project(source);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return (project);
        }

        @Override
        public Project[] newArray(int size) {

            return new Project[size];
        }
    };
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1378029107)
    private transient ProjectDao myDao;
    @Generated(hash = 1095573163)
    private transient Long collaborationGroup__resolvedKey;
    @Generated(hash = 1372501278)
    private transient Long category__resolvedKey;
}