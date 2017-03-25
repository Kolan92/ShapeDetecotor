package com.codebind;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.Collections;

public class ImageShapeDetector implements ShapeDetector {
    private Mat image;

    public Mat getPreprocessedImage() {
        return preprocessedImage;
    }

    private Mat preprocessedImage = new Mat();
    private Mat hsvImage = new Mat();

    private final double MAX_TOL = 0.08;
    private final int H_MIN = 0;
    private final int H_MAX = 256;
    private final int S_MIN = 0;
    private final int S_MAX = 256;
    private final int V_MIN = 0;
    private final int V_MAX = 256;
    private int minTheshold = 100;
    private int maxThreshold = 200;
    int iterator = 100;
    double lastMediana =0;

    private ArrayList<Shape> shapes = new ArrayList<Shape>();

    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public ImageShapeDetector(BufferedImage image){
        this.image = imageToMat(image);
    }

    public ImageShapeDetector(){

    }

    public void setImage(Mat image){
        shapes.clear();
        this.image = image;
        preprocessedImage = new Mat();
    }

    private int getMinimumShapeArea(){
        return image.height() * image.width() / 150;
    }

    private Mat imageToMat(BufferedImage image) {
        Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, data);
        return mat;
    }

    public boolean detectShapes(){
        preprocessImage2();
        detectPolygons();
        return true;
    }

    @Override
    public void setMinimumTreshold(int minThreshold) {
        this.minTheshold =  minThreshold;
    }

    @Override
    public void setMaxThreshold(int maxThreshold) {
        this.maxThreshold = maxThreshold;
    }

    private void preprocessImage() {
        if(image == null)
            return;

        Mat matGray = new Mat();
        Imgproc.cvtColor(image, matGray, Imgproc.COLOR_BGR2GRAY);

        Mat matBlurred = new Mat();
        Size s = new Size(3,3);
        Imgproc.GaussianBlur(matGray, matBlurred, s, 0);
        preprocessedImage = autoCany(matBlurred);
    }


    private Mat autoCany(Mat source) {
        return autoCany(source,0.9);
    }

    private Mat autoCany(Mat source, double sigma){
        double mediana = CalculateMediana(source);
        int lowerThreshold = (int)Math.max(0, (1.0 - sigma) * mediana);
        int upperThreshold = (int)Math.min(255, (1.0 + sigma) * mediana);

        Mat edged =new Mat();
        Imgproc.Canny(source, edged, lowerThreshold, upperThreshold);
        return edged;
    }

    private double CalculateMediana(Mat source) {
        Mat medianaMat = source.clone();
        medianaMat = medianaMat.reshape(0, 1);
        ArrayList<Double> pixelsList = new ArrayList<>();
        double[] pixels = medianaMat.get(0, 0);
        for (int index = 0; index < pixels.length; index++) {
            pixelsList.add(pixels[index]);
        }
        Collections.sort(pixelsList);
        int index = (int) pixelsList.size() / 2;
        lastMediana = pixelsList.get(index);
        return lastMediana;
    }

    private Mat cany(Mat source){
        Mat destionation =  new Mat();
        Imgproc.Canny(source, destionation, minTheshold, maxThreshold, 5, true);
        return destionation;
    }

    private Mat hsvToGreay(Mat hsvImage){
        ArrayList<Mat> chanells = new ArrayList<Mat>();
        Core.split(hsvImage, chanells);
        return chanells.get(0);
    }

    private void preprocessImage2(){
        if(image == null)
            return;

        Imgproc.cvtColor(image, preprocessedImage, Imgproc.COLOR_BGR2HSV);

        //Core.inRange(preprocessedImage, new Scalar(H_MIN,S_MIN,V_MIN),
        //                new Scalar(H_MAX,S_MAX,V_MAX),preprocessedImage);
        Size s = new Size(5,5);
        Imgproc.GaussianBlur(image, preprocessedImage, s, 0);
        //preprocessedImage.convertTo(preprocessedImage, -1, 2, 0);

        morphImage();
        Mat out = new Mat();
        Imgproc.medianBlur(preprocessedImage, out,5);
        preprocessedImage = out;
        preprocessedImage = hsvToGreay(preprocessedImage);
        preprocessedImage = autoCany(preprocessedImage);
        Imgproc.threshold( preprocessedImage, preprocessedImage, 200, 255, Imgproc.THRESH_BINARY );
    }

    private void morphImage(){
        Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3));
        Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5,5));

        Imgproc.erode(preprocessedImage, preprocessedImage, erodeElement);
        Imgproc.erode(preprocessedImage, preprocessedImage, erodeElement);

        Imgproc.dilate(preprocessedImage, preprocessedImage, dilateElement);
        Imgproc.dilate(preprocessedImage, preprocessedImage, dilateElement);
    }

    
    private void detectPolygons(){
        if(image == null)
            return;
        ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(preprocessedImage, contours,hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        int minCounturArea = getMinimumShapeArea();

        int numberOfCounturs = contours.size();
        for (int idx = 0; idx < numberOfCounturs; idx++) {
            MatOfPoint contour = contours.get(idx);
            double contourArea = Imgproc.contourArea(contour);
            if (contourArea < minCounturArea ) continue;
            ShapeType type = determinePolygonType(contour);
            if(type == ShapeType.None) continue;

            shapes.add(new Shape(contour, type));
        }
    }

    private ShapeType determinePolygonType(MatOfPoint countur) {
        MatOfPoint2f countur2f = new MatOfPoint2f();
        countur.convertTo(countur2f, CvType.CV_32FC2);
        double arcLength = Imgproc.arcLength(countur2f, true);
        MatOfPoint2f approx = new MatOfPoint2f();
        Imgproc.approxPolyDP(countur2f, approx, arcLength * 0.01, true);

        int corners = approx.toArray().length;
        switch (corners) {
            case 3:
                return ShapeType.Triangle;
            case 4:
                return ShapeType.Square;
            case 8:
                return ShapeType.Octogon;
        }
        if(isElipsis(countur)){
            System.out.println("Circle corners: " + Integer.toString(corners));
            return ShapeType.Circle;
        }
        return ShapeType.None;

    }
    private boolean isElipsis(Mat countour){
        double counturArea = Imgproc.contourArea(countour);
        Rect rect = Imgproc.boundingRect((MatOfPoint)countour);
        int A = rect.width / 2;
        int B = rect.height / 2;
        double estimated_area = Math.PI * A * B;
        double error = Math.abs(1 -(estimated_area / counturArea));
        if (error > MAX_TOL)
            return  false;

        return true;
    }


    public void drawBoxes(){
        for(int i = 0; i < shapes.size(); i++){
            Rect boundingRect = Imgproc.boundingRect(shapes.get(i).getShape());
            Point pt1 = new Point(boundingRect.x, boundingRect.y);
            Point pt2 = new Point(boundingRect.x + boundingRect.width, boundingRect.y + boundingRect.height);
            Imgproc.rectangle(image, pt1, pt2, shapes.get(i).getColor(), 5);
        }
    }

    public BufferedImage toBufferedImage(){
        return toBufferedImage(image);
    }

    public BufferedImage toBufferedImage(Mat mat){
        try{
            int type = mat.channels() == 1 ? BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_3BYTE_BGR;
            BufferedImage out = new BufferedImage(mat.width(), mat.height(), type);

            byte[] data = ((DataBufferByte) out.getRaster().getDataBuffer()).getData();
            mat.get(0, 0, data);
            return out;
        }
        catch (Exception e){
            return null;
        }
    }

    private void showImage(){
        showImage(toBufferedImage());
    }
    private void showImage(BufferedImage image){
        if (image == null) return;
        JDialog dialog = new JDialog();
        dialog.setUndecorated(false);
        JLabel label = new JLabel( new ImageIcon(image) );
        dialog.add( label );
        dialog.pack();
        dialog.setVisible(true);
    }
}
