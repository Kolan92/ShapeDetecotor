package com.filters;

import org.opencv.core.Mat;
import org.opencv.photo.Photo;

/**
 * Created by Pawel on 26.03.2017.
 */
public class FastNlMeansDenoisingFilter extends Filter {
    @Override
    public Mat applyTo(Mat image) throws Exception {
        Photo.fastNlMeansDenoising(image, processedImage);
        if(successor != null){
            return successor.applyTo(image);
        }
        return processedImage;
    }
}
