package com.filters;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class GaussianBlurFilter extends Filter{
    private int size;

    public GaussianBlurFilter(){
        this(5);
    }

    public GaussianBlurFilter(int size){
        this.size = size;
    }

    @Override
    public Mat applyTo(Mat image) throws Exception {
        Size s = new Size(size,size);
        Imgproc.GaussianBlur(image, processedImage, s, 0);
        if(successor != null){
            return successor.applyTo(processedImage);
        }
        return processedImage;
    }
}




