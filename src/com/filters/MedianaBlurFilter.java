package com.filters;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class MedianaBlurFilter extends Filter{
    @Override
    public Mat applyTo(Mat image) throws Exception {
        Imgproc.medianBlur(image, processedImage,5);
        if(successor != null){
            return successor.applyTo(processedImage);
        }
        return processedImage;
    }
}
