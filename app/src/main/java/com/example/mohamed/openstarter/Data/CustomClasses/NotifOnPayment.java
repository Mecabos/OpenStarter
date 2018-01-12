package com.example.mohamed.openstarter.Data.CustomClasses;

/**
 * Created by Bacem on 12/12/2017.
 */

public class NotifOnPayment {

    private boolean notify;

    public NotifOnPayment() {
    }

    @Override
    public String toString() {
        return "NotifOnPayment{" +
                "notify=" + notify +
                '}';
    }

    public boolean isNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }

    public NotifOnPayment(boolean notify) {

        this.notify = notify;
    }
}
