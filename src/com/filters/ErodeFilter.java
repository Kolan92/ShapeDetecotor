package com.filters;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class ErodeFilter extends Filter{

    @Override
    public Mat applyTo(Mat image) throws Exception {
        Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3));

        Imgproc.erode(image, processedImage, erodeElement);
        Imgproc.erode(image, processedImage, erodeElement);

        if(successor != null){
            return successor.applyTo(processedImage);
        }
        return processedImage;
    }
}
