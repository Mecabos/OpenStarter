package com.example.mohamed.openstarter.Models;

import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

/**
 * Created by Bacem on 12/9/2017.
 */

public class Category {

    //****Persistent properties
    @Id(autoincrement = true)
    private long id;
    @Property
    private String label;
    @Property
    private String color;

    public Category() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;

        Category category = (Category) o;

        if (id != category.id) return false;
        return label.equals(category.label);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + label.hashCode();
        return result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
