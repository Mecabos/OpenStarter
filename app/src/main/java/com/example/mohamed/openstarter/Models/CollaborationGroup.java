package com.example.mohamed.openstarter.Models;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Bacem on 12/3/2017.
 */

@Entity
public class CollaborationGroup implements Parcelable {

    //****Persistent properties
    @Id(autoincrement = true)
    private long id;
    @Property
    private String name;
    @Property
    private Date creationDate;
    @Property
    private int projectsCount;
    @Property
    @ToOne(joinProperty = "id")
    private User creator;
    @Property
    private boolean isUserAdmin;


    public CollaborationGroup() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CollaborationGroup)) return false;

        CollaborationGroup that = (CollaborationGroup) o;

        if (id != that.id) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        return result;
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

    public int getProjectsCount() {
        return projectsCount;
    }

    public void setProjectsCount(int projectsCount) {
        this.projectsCount = projectsCount;
    }

    public boolean isUserAdmin() {
        return isUserAdmin;
    }

    public void setUserAdmin(boolean userAdmin) {
        isUserAdmin = userAdmin;
    }

    //*************Parcel part
    @Keep
    public CollaborationGroup(Parcel in) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String[] data = new String[3];
        in.readStringArray(data);

        this.id = Long.parseLong(data[0]);
        this.name = data[1];
        this.creationDate = dateFormat.parse(data[2]);
    }

    @Generated(hash = 617803930)
    public CollaborationGroup(long id, String name, Date creationDate, int projectsCount, boolean isUserAdmin) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.projectsCount = projectsCount;
        this.isUserAdmin = isUserAdmin;
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
        });
    }
    @Transient
    public static final Parcelable.Creator<CollaborationGroup> CREATOR = new Parcelable.Creator<CollaborationGroup>() {

        @Override
        public CollaborationGroup createFromParcel(Parcel source) {
            CollaborationGroup CollaborationGroup = new CollaborationGroup();
            try {
                CollaborationGroup = new CollaborationGroup(source);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return (CollaborationGroup);
        }

        @Override
        public CollaborationGroup[] newArray(int size) {

            return new CollaborationGroup[size];
        }
    };

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 448990065)
    public User getCreator() {
        long __key = this.id;
        if (creator__resolvedKey == null || !creator__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            User creatorNew = targetDao.load(__key);
            synchronized (this) {
                creator = creatorNew;
                creator__resolvedKey = __key;
            }
        }
        return creator;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 288776169)
    public void setCreator(@NotNull User creator) {
        if (creator == null) {
            throw new DaoException("To-one property 'id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.creator = creator;
            id = creator.getId();
            creator__resolvedKey = id;
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

    public boolean getIsUserAdmin() {
        return this.isUserAdmin;
    }

    public void setIsUserAdmin(boolean isUserAdmin) {
        this.isUserAdmin = isUserAdmin;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2114208831)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCollaborationGroupDao() : null;
    }
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 662734859)
    private transient CollaborationGroupDao myDao;
    @Generated(hash = 1767171241)
    private transient Long creator__resolvedKey;
}
