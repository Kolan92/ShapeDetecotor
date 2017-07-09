package com.filters;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Pawel on 11.06.2017.
 */
public class HughCirclesFilter extends Filter {
    private int CannyUpperThreshold = 100;
    private int MinRadius = 200;
    private int MaxRadius = 400;
    private int Accumulator = 150;

    private int minDist = 100;
    private int dp = 1;
    private int param1 = 80;
    private int param2 = 10;
    private int minRadius = 250;
    private int maxRadius = 300;
    @Override
    public Mat applyTo(Mat image) throws Exception {
        //    public static void HoughCircles(Mat image, Mat circles, int method, double dp, double minDist, double param1, double param2, int minRadius, int maxRadius)

        //TODO: needs improvments, method sensitive to its parameters
        Mat circles= new Mat();
        Mat processedImage = image.clone();
        Imgproc.HoughCircles(image, circles, Imgproc.CV_HOUGH_GRADIENT,
                1, 30, 200, 50, 0, 0 );

        /*
        Imgproc.HoughCircles(image, circles, Imgproc.CV_HOUGH_GRADIENT,
                dp, minDist, param1, param2,
                minRadius, maxRadius);
        */
        System.out.println("Hugh found circles before filtration: "+circles.cols());
        if (circles.cols() > 0)
            for (int x = 0; x < circles.cols(); x++)
            {
                double vCircle[] = circles.get(0,x);

                if (vCircle == null)
                    continue;

                Point pt = new Point(Math.round(vCircle[0]), Math.round(vCircle[1]));
                int radius = (int)Math.round(vCircle[2]);
                Imgproc.circle(processedImage, pt, radius, new Scalar(255,255,255), 10);
            }
        if(successor != null)
            return successor.applyTo(processedImage);

        return processedImage;
    }
}
