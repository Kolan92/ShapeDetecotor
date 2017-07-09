package com.filters;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Pawel on 25.03.2017.
 */
public class CanyFilter extends Filter {
    private double sigma;

    public CanyFilter(double sigma){
        this.sigma = sigma;
    }

    public CanyFilter(){
        this(0.9);
    }
    @Override
    public Mat applyTo(Mat image) throws Exception {
        double median = CalculateMedian(image);
        int lowerThreshold = (int)Math.max(0, (1.0 - sigma) * median);
        int upperThreshold = (int)Math.min(255, (1.0 + sigma) * median);

        Imgproc.Canny(image, processedImage, lowerThreshold, upperThreshold);
        if(successor != null){
            return successor.applyTo(processedImage);
        }
        return processedImage;
    }

    private double CalculateMedian(Mat source) {
        Mat medianaMat = source.clone();
        medianaMat = medianaMat.reshape(0, 1);
        ArrayList<Double> pixelsList = new ArrayList<>();
        double[] pixels = medianaMat.get(0, 0);
        for (int index = 0; index < pixels.length; index++) {
            pixelsList.add(pixels[index]);
        }
        Collections.sort(pixelsList);
        int index = (int) pixelsList.size() / 2;
        return pixelsList.get(index);
        /*
        long n = source.total();
        int[] histBuff = new int[(int) n];
        source.get(0, 0, histBuff);
        // Compute the mean and standard deviation
        // int n = x.length;
        double sum = 0;
        // int i;
        for (int i = 0; i < n; i++) {
            sum += histBuff[i];
        }
        double mu = sum / n;

        sum = 0;
        for (int i = 0; i < n; i++) {
            sum += (histBuff[i] - mu) * (histBuff[i] - mu);
        }
        double sigma = Math.sqrt(sum / n);

        // Bin x across the interval [mu-sigma, mu+sigma]
        int bottomcount = 0;
        int[] bincounts = new int[1001];
        for (int i = 0; i < 1001; i++) {
            bincounts[i] = 0;
        }
        double scalefactor = 1000 / (2 * sigma);
        double leftend = mu - sigma;
        double rightend = mu + sigma;
        int bin;

        for (int i = 0; i < n; i++) {
            if (histBuff[i] < leftend) {
                bottomcount++;
            } else if (histBuff[i] < rightend) {
                bin = (int) ((histBuff[i] - leftend) * scalefactor);
                bincounts[bin]++;
            }
        }

        double median = 0;
        // If n is odd
        if ((n % 2) != 0) {
            // Find the bin that contains the median
            int k = (int) ((n + 1) / 2);
            int count = bottomcount;

            for (int i = 0; i < 1001; i++) {
                count += bincounts[i];

                if (count >= k) {
                    median = (i + 0.5) / scalefactor + leftend;
                }
            }
        }

        // If n is even
        else {
            // Find the bins that contains the medians
            int k = (int) (n / 2);
            int count = bottomcount;

            for (int i = 0; i < 1001; i++) {
                count += bincounts[i];

                if (count >= k) {
                    int j = i;
                    while (count == k) {
                        j++;
                        count += bincounts[j];
                    }
                    median = (i + j + 1) / (2 * scalefactor) + leftend;
                }
            }
        }
        return median;*/
    }

    @Override
    public String toString() {
        String filterName =this.getClass().getName()+ "\tArgs\t sigma " + sigma;
        if(successor != null){
            return filterName + successor.toString();
        }
        return filterName ;
    }
}
