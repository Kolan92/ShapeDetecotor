package com.filters;

import org.opencv.core.Mat;

/**
 * Created by Pawel on 03.05.2017.
 */
public abstract class SegNormHueFilter extends Filter{
    protected int hueMin;
    protected int hueMax;
    protected int satMin;

    @Override
    public Mat applyTo(Mat image) throws Exception {
        double[] buffer = new double[3];
        processedImage = image.clone();
        for (int i = 0; i < image.rows(); ++i) {

            for (int j = 0; j < image.cols(); ++j) {
                double[] hlsData = image.get(i,j);
                double s = hlsData[0];
                //double l = hlsData[1]; //For debug purpose
                double h = hlsData[2];
                char data = (char)(blueCondition(h, hueMax, hueMin, s, satMin) ? 255 : 0);

                buffer[0] = data;
                processedImage.put(i,j, buffer);
            }
        }

        if (successor != null)
            return successor.applyTo(processedImage);

        return processedImage;
    }

    private boolean blueCondition(double h, int hue_max, int hue_min, double s, int sat_min){
        return (h < hue_max && h > hue_min) && s > sat_min;
    }
}
