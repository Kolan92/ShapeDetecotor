package com.filters;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Pawel on 16.06.2017.
 */
public class OpeningFilter extends Filter{
    @Override
    public Mat applyTo(Mat image) throws Exception {
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3));
        Imgproc.morphologyEx(image, processedImage, Imgproc.MORPH_OPEN, kernel);
        if(successor != null)
            return successor.applyTo(processedImage);

        return processedImage;
    }
}
