package com.filters;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Pawel on 26.03.2017.
 */
public class ClosingFilter extends Filter {
    private int size;

    public ClosingFilter(int size){
        this.size = size;
    }

    public ClosingFilter(){
        this(7);
    }

    @Override
    public Mat applyTo(Mat image) throws Exception {
        Mat structuringElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(size,size));
        Imgproc.morphologyEx(image, processedImage, Imgproc.MORPH_CLOSE, structuringElement);

        if(successor != null){
            return successor.applyTo(processedImage);
        }
        return processedImage;
    }
}
