package com.filters;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class BinaryFilter extends Filter{
    private int lowThreshold;
    private int highThreshold;

    public BinaryFilter(){
        this(200, 255);
    }

    public BinaryFilter(int lowThershold, int highThreshold){
        this.lowThreshold = lowThershold;
        this.highThreshold = highThreshold;
    }
    @Override
    public Mat applyTo(Mat image) throws Exception {
        System.out.println("Binary");
        System.out.println(image.type());

        Imgproc.threshold(image, processedImage, lowThreshold, highThreshold, Imgproc.THRESH_BINARY );
        System.out.println(processedImage.type());

        if(successor != null){
            return successor.applyTo(processedImage);
        }
        return processedImage;
    }

    @Override
    public String toString() {
        String filterName =this.getClass().getName()+"\tArgs\t lowThreshold " + lowThreshold + "\t highThreshold" +highThreshold;
        if(successor != null){
            return filterName + successor.toString();
        }
        return filterName ;
    }
}
