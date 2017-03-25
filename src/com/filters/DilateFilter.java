package com.filters;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class DilateFilter extends Filter{

    @Override
    public Mat applyTo(Mat image) throws Exception {
        Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5,5));

        Imgproc.dilate(image, processedImage, dilateElement);
        Imgproc.dilate(image, processedImage, dilateElement);

        if(successor != null){
            return successor.applyTo(processedImage);
        }
        return processedImage;
    }
}
