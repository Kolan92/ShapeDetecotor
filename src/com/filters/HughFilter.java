package com.filters;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Pawel on 11.06.2017.
 */
public class HughFilter extends Filter {
    private int CannyUpperThreshold = 100;
    private int MinRadius = 200;
    private int MaxRadius = 400;
    private int Accumulator = 300;

    private int minDist = 100;
    private int dp = 1;
    private int param1 = 80;
    private int param2 = 10;
    private int minRadius = 250;
    private int maxRadius = 300;
    @Override
    public Mat applyTo(Mat image) throws Exception {
        //    public static void HoughCircles(Mat image, Mat circles, int method, double dp, double minDist, double param1, double param2, int minRadius, int maxRadius)

        Imgproc.HoughCircles(image, processedImage, Imgproc.CV_HOUGH_GRADIENT,
                4.0, image.rows() / 4, CannyUpperThreshold, Accumulator,
                MinRadius, MaxRadius);

        if (processedImage.cols() > 0)
            for (int x = 0; x < processedImage.cols(); x++)
            {
                double vCircle[] = processedImage.get(0,x);

                if (vCircle == null)
                    break;

                Point pt = new Point(Math.round(vCircle[0]), Math.round(vCircle[1]));
                int radius = (int)Math.round(vCircle[2]);
                Imgproc.circle(image, pt, radius, new Scalar(255,255,255), 10);
            }
        if(successor != null)
            return successor.applyTo(image);

        return image;
    }
}
