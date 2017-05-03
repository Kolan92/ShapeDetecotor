package com.filters;

import org.opencv.core.Mat;

/**
 * Created by Pawel on 02.04.2017.
 */
public class NormaliseRedHueFilter extends Filter {
    @Override
    public Mat applyTo(Mat image) throws Exception {
        if(successor != null){
            return successor.applyTo(processedImage);
        }
        return processedImage;
    }
}
