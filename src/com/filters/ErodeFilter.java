package com.filters;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class ErodeFilter extends Filter{
    private int morphElement;

    public ErodeFilter(){
        morphElement = Imgproc.MORPH_CROSS;
    }

    public ErodeFilter(int morpgElement){
        morphElement = morpgElement;
    }

    @Override
    public Mat applyTo(Mat image) throws Exception {
        Mat erodeElement = Imgproc.getStructuringElement(morphElement, new Size(3,3));

        Imgproc.erode(image, processedImage, erodeElement);
        Imgproc.erode(image, processedImage, erodeElement);

        if(successor != null){
            return successor.applyTo(processedImage);
        }
        return processedImage;
    }
}
