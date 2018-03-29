package com.esprit.pixelCells.openstarter.Models;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;

import java.text.ParseException;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by Bacem on 12/9/2017.
 */

@Entity
public class Category implements Parcelable{

    //****Persistent properties
    @Id(autoincrement = true)
    private long id;
    @Property
    private String label;
    @Property
    private String color;

    public Category() {
    }

    @Generated(hash = 97192242)
    public Category(long id, String label, String color) {
        this.id = id;
        this.label = label;
        this.color = color;
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

    //*************Parcel part
    @Keep
    public Category(Parcel in) throws ParseException {
        String[] data = new String[3];
        in.readStringArray(data);

        this.id = Long.parseLong(data[0]);
        this.label = data[1];
        this.color = data[2];
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{String.valueOf(this.id),
                this.label,
                this.color
        });
    }

    @Transient
    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {

        @Override
        public Category createFromParcel(Parcel source) {
            Category Category = new Category();
            try {
                Category = new Category(source);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return (Category);
        }

        @Override
        public Category[] newArray(int size) {

            return new Category[size];
        }
    };
}
