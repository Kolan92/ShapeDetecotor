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
        Mat lower_red_hue_range = new Mat();
        Mat upper_red_hue_range = new Mat();
        Core.inRange(image, new Scalar(0, 100, 100), new Scalar(10, 255, 255), lower_red_hue_range);
        Core.inRange(image, new Scalar(160, 100, 100), new Scalar(179, 255, 255), upper_red_hue_range);

        // Combine the above two images
        Mat red_hue_image = new Mat();
        Core.addWeighted(lower_red_hue_range, 1.0, upper_red_hue_range, 1.0, 0.0, red_hue_image);

        if(successor != null)
            return successor.applyTo(processedImage);

        return processedImage;
    }
}
