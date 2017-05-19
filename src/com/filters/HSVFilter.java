package com.filters;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Pawel on 19.05.2017.
 */
public class HSVFilter extends Filter {
    @Override
    public Mat applyTo(Mat image) throws Exception {
        processedImage = new Mat(image.height(),image.width(), CvType.CV_8UC3);
        Imgproc.cvtColor(image, processedImage, Imgproc.COLOR_RGB2HSV);
        if(successor != null){
            return successor.applyTo(processedImage);
        }
        return processedImage;
    }
}
