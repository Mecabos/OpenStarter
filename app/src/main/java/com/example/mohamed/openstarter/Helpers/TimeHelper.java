package com.example.mohamed.openstarter.Helpers;

import java.util.Date;

/**
 * Created by Bacem on 12/12/2017.
 */

public class TimeHelper {

    public static String getTimeLeft (Date starteDate, Date finishDate){

        String result = "";
        Long secs = (finishDate.getTime() - starteDate.getTime()) / 1000;
        Long days = secs / 86400;
        secs = secs % 86400;
        Long hours = secs / 3600;
        secs = secs % 3600;
        Long mins = secs / 60;
        secs = secs % 60;

        if (days > 0){
            result+= days.toString()+ " Days ";
        }else {
            if (hours > 0){
                result += hours.toString()+" Hours and "+ mins + " Minutes" ;
            }else{
                result += mins + " Minutes ";
                if (secs > 0){
                    result += "and " + secs + " s";
                }
            }
        }

        return result ;
    }

}
