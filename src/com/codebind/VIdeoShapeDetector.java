package com.codebind;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import java.awt.image.BufferedImage;

/**
 * Created by Pawel on 28.11.2016.
 */

public class VideoShapeDetector implements ShapeDetector {
    private String filePath;
    private VideoCapture videoStream;
    private ImageShapeDetector frameDetector = new ImageShapeDetector();

    static {
        System.loadLibrary("opencv_ffmpeg310_64");
    }

    public VideoShapeDetector(){
        videoStream = new VideoCapture(0);
    }

    public void OpenVideo(String filePath){
        this.filePath = filePath;
        videoStream = new VideoCapture();
        videoStream.open(filePath);
    }

    public boolean detectShapes(){
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
        frameDetector.setMinimumTreshold(minThreshold);
    }

    @Override
    public void setMaxThreshold(int maxThreshold) {
        frameDetector.setMaxThreshold(maxThreshold);
    }

    @Override
    public BufferedImage toBufferedImage() {
        return frameDetector.toBufferedImage();
    }

    public BufferedImage GetFrame(){
        return  frameDetector.toBufferedImage();
    }

    public BufferedImage GetPreprocessedFrame() {
        return frameDetector.toBufferedImage(frameDetector.getPreprocessedImage());
    }
}
