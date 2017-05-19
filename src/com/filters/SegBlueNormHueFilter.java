package com.filters;

/**
 * Created by Pawel on 23.04.2017.
 */
public class SegBlueNormHueFilter extends SegNormHueFilter {

    public SegBlueNormHueFilter(){
        hueMax = 163;
        hueMin = 134;
        satMin = 39;
        isBlue = true;
    }
}
