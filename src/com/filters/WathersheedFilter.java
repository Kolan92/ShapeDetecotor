package com.filters;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Pawel on 17.06.2017.
 */
public class WathersheedFilter extends Filter {
    @Override
    public Mat applyTo(Mat image) throws Exception {
        //image must be gryscale
        Mat binaryImage = new Mat();

        Imgproc.threshold(binaryImage, binaryImage, 100, 255, Imgproc.THRESH_BINARY);

        Mat fg = new Mat(image.size(),CvType.CV_8U);
        Imgproc.erode(binaryImage,fg,new Mat(),new Point(-1,-1),2);

        Mat bg = new Mat(image.size(), CvType.CV_8U);
        Imgproc.dilate(binaryImage,bg,new Mat(),new Point(-1,-1),3);
        Imgproc.threshold(bg,bg,1, 128,Imgproc.THRESH_BINARY_INV);

        Mat markers = new Mat(image.size(),CvType.CV_8U, new Scalar(0));
        Core.add(fg, bg, markers);

        WatershedSegmenter segmenter = new WatershedSegmenter();
        segmenter.setMarkers(markers);
        processedImage = segmenter.process(image);
        if(successor!=null)
            return successor.applyTo(processedImage);
        return processedImage;
    }

    private class WatershedSegmenter{
        public Mat markers;

        public void setMarkers(Mat markerImage)
        {
            markerImage.convertTo(markers, CvType.CV_32S);
        }

        public Mat process(Mat image)
        {
            Imgproc.watershed(image, markers);
            markers.convertTo(markers,CvType.CV_8U);
            return markers;
        }
    }
}
