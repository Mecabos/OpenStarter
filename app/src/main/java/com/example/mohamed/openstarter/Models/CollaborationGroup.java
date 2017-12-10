package com.example.mohamed.openstarter.Models;

import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;

/**
 * Created by Bacem on 12/3/2017.
 */

public class CollaborationGroup {

    //****Persistent properties
    @Id(autoincrement = true)
    private long id;
    @Property
    private String name;
    @Property
    private Date creationDate;
    @Property
    @ToOne(joinProperty = "userId")
    private User creator;


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

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
