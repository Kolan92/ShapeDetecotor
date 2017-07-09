package com.codebind;

import com.filters.Filter;
import com.filters.FilterBuilder;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import com.detectors.ImageShapeDetector;

import java.awt.image.BufferedImage;

/**
 * Created by Pawel on 28.11.2016.
 */

public class VideoShapeDetector implements ShapeDetector {
    private String filePath;
    private VideoCapture videoStream;
    private ImageShapeDetector frameDetector = new ImageShapeDetector();

    static {
        System.loadLibrary("opencv_ffmpeg320_64");
    }

    public VideoShapeDetector(){
        videoStream = new VideoCapture(0);
        FilterBuilder builder = new FilterBuilder();
        /*
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()

                .WithClosingFilter()
                .WithBinaryFilter(150,230)
                .WithCanyFilter()
                .Build();
*/
        Filter filter = builder
                .WithHSVFilter()
                .WithAddWeightedFilter()
                .WithOpeningFilter()
                .WithClosingFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithCanyFilter()
                .Build();
        frameDetector.setFilter(filter);
    }

    public void OpenVideo(String filePath){
        this.filePath = filePath;
        videoStream = new VideoCapture();
        videoStream.open(filePath);
    }

    public boolean detectShapes() throws Exception {
        Mat frame = new Mat();
        if(videoStream.read(frame)){
            frameDetector.setImage(frame);
            frameDetector.detectShapes();
            frameDetector.drawBoxes();
            return true;
        }

        videoStream.release();
        return false;
    }

    @Override
    public void setMinimumTreshold(int minThreshold) {

    }

    @Override
    public void setMaxThreshold(int maxThreshold) {

    }

    @Override
    public BufferedImage toBufferedImage() {
        return frameDetector.getResultImage();
    }

    public BufferedImage GetFrame(){
        return  frameDetector.getResultImage();
    }

    public BufferedImage GetPreprocessedFrame() {
        return frameDetector.getProcessedImage();
    }
}
