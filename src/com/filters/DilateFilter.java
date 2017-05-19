package com.filters;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class DilateFilter extends Filter{

    private int morphElement;

    public DilateFilter(){
        morphElement = Imgproc.MORPH_CROSS;
    }

    public DilateFilter(int morpgElement){
        morphElement = morpgElement;
    }
    @Override
    public Mat applyTo(Mat image) throws Exception {
        Mat dilateElement = Imgproc.getStructuringElement(morphElement, new Size(5,5));

        Imgproc.dilate(image, processedImage, dilateElement);
        Imgproc.dilate(image, processedImage, dilateElement);

        if(successor != null){
            return successor.applyTo(processedImage);
        }
        return processedImage;
    }
}
