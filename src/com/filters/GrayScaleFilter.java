package com.filters;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Pawel on 25.03.2017.
 */
public class GrayScaleFilter extends Filter {
    @Override
    public Mat applyTo(Mat image) throws Exception {
        Imgproc.cvtColor(image, processedImage, Imgproc.COLOR_BGR2GRAY);
        if(successor != null){
            return successor.applyTo(processedImage);
        }
        return processedImage;
    }
}
