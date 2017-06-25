package com.filters;

import org.opencv.core.Mat;

/**
 * Created by Pawel on 16.06.2017.
 */
public class SmoothFilter extends Filter {
    @Override
    public Mat applyTo(Mat image) throws Exception {

        if(successor != null)
            return successor.applyTo(processedImage);
        return processedImage;
    }
}
