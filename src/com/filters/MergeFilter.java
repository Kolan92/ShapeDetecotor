package com.filters;

import org.opencv.core.Core;
import org.opencv.core.Mat;

/**
 * Created by Pawel on 11.06.2017.
 */
public class MergeFilter extends Filter {
    private Filter innerFilter;

    public MergeFilter(Filter innerFilter) {
        this.innerFilter = innerFilter;
    }

    @Override
    public Mat applyTo(Mat image) throws Exception {
        Mat innerResult = innerFilter.applyTo(image);
        Core.bitwise_or(image, innerResult, processedImage);

        if(successor != null)
            return successor.applyTo(processedImage);

        return processedImage;
    }
}
