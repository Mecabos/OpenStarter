package com.example.mohamed.openstarter.Helpers;

/**
 * Created by Bacem on 11/26/2017.
 */

public class NumbersHelper {

    public static float round(float number, int scale) {
        int pow = 10;
        for (int i = 1; i < scale; i++)
            pow *= 10;
        float tmp = number * pow;
        return ( (float) ( (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) ) ) / pow;
    }
}
