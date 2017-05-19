package com.filters;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Pawel on 19.05.2017.
 */
public class AdaptiveThresholdFilter extends Filter {
    @Override
    public Mat applyTo(Mat image) throws Exception {
        Imgproc.adaptiveThreshold(image, processedImage, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C , Imgproc.THRESH_BINARY, 11, 2);

        if(successor!=null)
            return successor.applyTo(processedImage);
        return processedImage;
    }
}
