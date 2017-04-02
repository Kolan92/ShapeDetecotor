package com.filters;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Pawel on 01.04.2017.
 */
public class EqualizeHistogramFilter extends Filter {
    @Override
    public Mat applyTo(Mat image) throws Exception {
        Imgproc.equalizeHist(image, processedImage);

        if(successor != null){
            return successor.applyTo(processedImage);
        }
        return processedImage;
    }
}
