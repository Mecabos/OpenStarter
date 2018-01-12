package com.example.mohamed.openstarter.Data.CustomClasses;

/**
 * Created by Bacem on 12/12/2017.
 */

public class FollowCount {

    private int followsCount ;
    private boolean hasFollowed = false ;

    public int getFollowsCount() {
        return followsCount;
    }

    public void setFollowsCount(int followsCount) {
        this.followsCount = followsCount;
    }

    public boolean isHasFollowed() {
        return hasFollowed;
    }

    public void setHasFollowed(boolean hasFollowed) {
        this.hasFollowed = hasFollowed;
    }
}
