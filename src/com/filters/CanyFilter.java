package com.filters;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Pawel on 25.03.2017.
 */
public class CanyFilter extends Filter {
    private double sigma;

    public CanyFilter(double sigma){
        this.sigma = sigma;
    }

    public CanyFilter(){
        this(0.9);
    }
    @Override
    public Mat applyTo(Mat image) throws Exception {
        double mediana = CalculateMediana(image);
        int lowerThreshold = (int)Math.max(0, (1.0 - sigma) * mediana);
        int upperThreshold = (int)Math.min(255, (1.0 + sigma) * mediana);

        Imgproc.Canny(image, processedImage, lowerThreshold, upperThreshold);
        if(successor != null){
            return successor.applyTo(processedImage);
        }
        return processedImage;
    }

    private double CalculateMediana(Mat source) {
        Mat medianaMat = source.clone();
        medianaMat = medianaMat.reshape(0, 1);
        ArrayList<Double> pixelsList = new ArrayList<>();
        double[] pixels = medianaMat.get(0, 0);
        for (int index = 0; index < pixels.length; index++) {
            pixelsList.add(pixels[index]);
        }
        Collections.sort(pixelsList);
        int index = (int) pixelsList.size() / 2;
        return pixelsList.get(index);
    }

    @Override
    public String toString() {
        String filterName =this.getClass().getName()+ "\tArgs\t sigma " + sigma;
        if(successor != null){
            return filterName + successor.toString();
        }
        return filterName ;
    }
}
