package com.filters;

import org.opencv.core.Mat;

/**
 * Created by Pawel on 23.04.2017.
 */
public class SegLogChromaticFilter extends Filter {
    final double MINLOGRG = 0.5;
    final double MAXLOGRG = 2.1;
    final double MINLOGBG = -0.9;
    final double MAXLOGBG = 0.85;

    private BlueLogarithmFilter blueLogarithmFilter;
    private RedLogarithmFilter redLogarithmFilter;

    public SegLogChromaticFilter(){
        blueLogarithmFilter = new BlueLogarithmFilter();
        redLogarithmFilter = new RedLogarithmFilter();
    }
    @Override
    public Mat applyTo(Mat image) throws Exception {
        Mat logImageRed = redLogarithmFilter.applyTo(image);
        Mat logImageBlue = blueLogarithmFilter.applyTo(image);

        processedImage = image.clone();
        for (int i = 0 ; i < processedImage.rows() ; i++) {
            for (int j = 0 ; j < processedImage.cols() ; j++) {
                boolean condR = (logImageRed.get(i, j)[0] > MINLOGRG)&&(logImageRed.get(i, j)[0] < MAXLOGRG);
                boolean condB = (logImageBlue.get(i, j)[0] > MINLOGBG)&&(logImageBlue.get(i, j)[0] < MAXLOGBG);

                int value = (condR && condB) ? 255 : 0;
                byte[] data = new byte[3];
                data[0] = (byte)value;
                processedImage.put(i, j, data);
            }
        }

        if(successor != null)
            return successor.applyTo(processedImage);

        return processedImage;
    }
}
