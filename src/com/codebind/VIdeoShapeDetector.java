package com.codebind;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import java.awt.image.BufferedImage;

/**
 * Created by Pawel on 28.11.2016.
 */

public class VIdeoShapeDetector {
    private String filePath;
    private VideoCapture videoStream;
    private ImageShapeDetector frameDetector = new ImageShapeDetector();

    static {
        System.loadLibrary("opencv_ffmpeg310_64");
    }

    public VIdeoShapeDetector(){
        videoStream = new VideoCapture(0);
    }

    public void OpenVideo(String filePath){
        this.filePath = filePath;
        videoStream = new VideoCapture();
        videoStream.open(filePath);
    }

    public boolean PlaySequence(){
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

    public BufferedImage GetFrame(){
        return  frameDetector.toBufferedImage();
    }
    /*
    public Iterable<String> getStuff() {
        return new Iterable<String>() {

            @Override
            public Iterator<String> iterator() {
                return new Iterator<String>() {

                    @Override
                    public boolean hasNext() {
                        // TODO code to check next
                    }

                    @Override
                    public String next() {
                        // TODO code to go to next
                    }

                    @Override
                    public void remove() {
                        // TODO code to remove item or throw exception
                    }

                };
            }
        };
    }
    */
}
