package com.filters;

/**
 * Created by Pawel on 03.05.2017.
 */
public class SegRedNormHueFilter extends SegNormHueFilter {
    public SegRedNormHueFilter(){
        hueMax = 15;
        hueMin = 240;
        satMin = 25;
        isBlue = false;
    }
}
