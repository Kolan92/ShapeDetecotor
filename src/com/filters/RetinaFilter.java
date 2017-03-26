package com.filters;

import org.opencv.core.Mat;

/**
 * Created by Pawel on 26.03.2017.
 */
public class RetinaFilter extends Filter {
    @Override
    public Mat applyTo(Mat image) throws Exception {

        if(successor != null){
            return successor.applyTo(image);
        }
        return processedImage;
    }
}
