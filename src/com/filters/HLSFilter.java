package com.filters;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Pawel on 02.04.2017.
 */
public class HLSFilter extends Filter {
    @Override
    public Mat applyTo(Mat image) throws Exception {
        processedImage = new Mat(image.height(),image.width(), CvType.CV_8UC3);
        Imgproc.cvtColor(image, processedImage, Imgproc.COLOR_RGB2HLS);

        if(successor != null){
            return successor.applyTo(processedImage);
        }
        return processedImage;
    }
}
