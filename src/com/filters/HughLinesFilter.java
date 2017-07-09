package com.filters;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Pawel on 25.06.2017.
 */
public class HughLinesFilter extends Filter {
    private int rho = 1;
    private double theta = Math.PI/180;;
    private int threshold = 10;

    @Override
    public Mat applyTo(Mat image) throws Exception {
        processedImage = image.clone();
        Mat lines = new Mat();
        Imgproc.HoughLinesP(image, lines, rho, theta, threshold);

        for (int i = 0; i < lines.cols(); i++) {
            double[] val = lines.get(0, i);
            if (val == null)
                continue;
            Imgproc.line(processedImage, new Point(val[0], val[1]), new Point(val[2], val[3]), new Scalar(255, 255, 255), 2);
        }
        if(successor != null)
            return successor.applyTo(processedImage);
        return processedImage;
    }
}
