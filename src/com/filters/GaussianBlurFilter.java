package com.filters;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class GaussianBlurFilter extends Filter{

    @Override
    public Mat applyTo(Mat image) throws Exception {
        Size s = new Size(5,5);
        Imgproc.GaussianBlur(image, processedImage, s, 0);
        if(successor != null){
            return successor.applyTo(processedImage);
        }
        return processedImage;
    }
}




