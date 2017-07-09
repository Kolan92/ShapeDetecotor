package com.filters;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

/**
 * Created by Pawel on 25.06.2017.
 */
public class AddWeightedFilter extends Filter {
    @Override
    public Mat applyTo(Mat image) throws Exception {
        Mat toRemove = new Mat(image.size(), image.type());
        Mat toSave = new Mat(image.size(), image.type());
        Core.inRange(image, new Scalar(80, 100, 100), new Scalar(100, 255, 255), toRemove);
        Core.inRange(image, new Scalar(100, 100, 100), new Scalar(120, 255, 255), toSave);

        // Combine the above two images
        //Core.addWeighted(toRemove, 1.0, upper_red_hue_range, 1.0, 0.0, processedImage);
        //Core.bitwise_or(toRemove,upper_red_hue_range, processedImage);
        Core.subtract(toSave, toRemove, processedImage);

        //processedImage = toRemove.clone();
        if(successor != null)
            return successor.applyTo(processedImage);

        return processedImage;
    }
}
