package com.filters;

import org.opencv.core.Mat;

/**
 * Created by Pawel on 23.04.2017.
 */
public abstract class LogarithmFilter extends Filter {
    protected int colorIndex;
    @Override
    public Mat applyTo(Mat image) throws Exception {
        processedImage = image.clone();
        final int blockIter = 64;
        int Niiter = Math.max(1, (int) Math.ceil(image.rows() / blockIter));
        int Njiter = Math.max(1, (int) Math.ceil(image.cols() / blockIter));

        for (int iit = 0; iit <= Niiter; ++iit) {
            for (int i = iit * blockIter; i < iit * (blockIter + 1); i++) {

                if (i >= image.rows()) {
                    break;
                }

                for (int jit = 0; jit <= Njiter; ++jit) {
                    for (int j = jit * blockIter; j < jit * (blockIter + 1); j++) {

                        if (j >= image.cols()) {
                            break;
                        }
                        byte[] pixel = new byte[3];
                        image.get(i, j, pixel);
                        byte division = (byte)(1 / (pixel[1] == 0 ? pixel[1] + 1 : pixel[1]));
                        byte data = (byte)(pixel[colorIndex] * division);
                        byte[] newPixel = new byte[3];
                        newPixel[colorIndex] = data;
                        processedImage.put(i,j, newPixel);
                    }
                }
            }
        }

        if (successor != null)
            return successor.applyTo(processedImage);
        return processedImage;
    }
}
