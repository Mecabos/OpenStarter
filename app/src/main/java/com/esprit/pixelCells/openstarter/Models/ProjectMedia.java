package com.esprit.pixelCells.openstarter.Models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by Bacem on 12/13/2017.
 */

@Entity
public class ProjectMedia {

    @Id(autoincrement = true)
    private long id;
    @Property
    private String name;
    @Property
    private String type;
    @Property
    private String path;
    @Property
    private Date uploadDate;
    @Property
    private boolean isPrimary;
    @Property
    @ToOne(joinProperty = "id")
    private Project project;


    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 265753341)
    private transient ProjectMediaDao myDao;
    @Generated(hash = 594172472)
    public ProjectMedia(long id, String name, String type, String path,
            Date uploadDate, boolean isPrimary) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.path = path;
        this.uploadDate = uploadDate;
        this.isPrimary = isPrimary;
    }
    @Generated(hash = 868025254)
    public ProjectMedia() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getPath() {
        return this.path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public Date getUploadDate() {
        return this.uploadDate;
    }
    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }
    public boolean getIsPrimary() {
        return this.isPrimary;
    }
    public void setIsPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }
    @Generated(hash = 1005767482)
    private transient Long project__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Keep
    public Project getProject() {
        long __key = this.id;
        if (project__resolvedKey == null || !project__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                if (this.project != null) return  this.project ;
                throw new DaoException("Entity is detached from DAO context");
            }
            ProjectDao targetDao = daoSession.getProjectDao();
            Project projectNew = targetDao.load(__key);
            synchronized (this) {
                project = projectNew;
                project__resolvedKey = __key;
            }
        }
        return project;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1673324817)
    public void setProject(@NotNull Project project) {
        if (project == null) {
            throw new DaoException(
                    "To-one property 'id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.project = project;
            id = project.getId();
            project__resolvedKey = id;
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
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1188720252)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getProjectMediaDao() : null;
    }


}
