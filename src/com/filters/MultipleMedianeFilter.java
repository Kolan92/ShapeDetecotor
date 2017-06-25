package com.filters;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Pawel on 03.05.2017.
 */
public class MultipleMedianeFilter extends Filter {
    private int iterations;
    private int size;

    MultipleMedianeFilter(){
        this(5,5);
    }

    MultipleMedianeFilter(int iterations, int size){
        this.iterations = iterations;
        this.size = size;
    }

    @Override
    public Mat applyTo(Mat image) throws Exception {
        System.out.println("Before");
        System.out.println(image.type());

        processedImage = image.clone();
        for ( int i=0; i< iterations; i++) {
            Imgproc.medianBlur(processedImage, processedImage, size);
            System.out.println(processedImage.type());
        }

        if(successor != null)
            successor.applyTo(processedImage);

        return processedImage;
    }
}
