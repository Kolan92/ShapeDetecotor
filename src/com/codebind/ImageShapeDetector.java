package com.codebind;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;

public class ImageShapeDetector {
    private Mat image;
    private Mat preprocessedImage = new Mat();

    private final double MAX_TOL = 0.08;

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

    public void detectShapes(){
        preprocessImage();
        detectPolygons();
    }

    private void preprocessImage() {
        if(image == null)
            return;

        Mat matGray = new Mat();
        Imgproc.cvtColor(image, matGray, Imgproc.COLOR_BGR2GRAY);

        Mat matBlurred = new Mat();
        org.opencv.core.Size s = new Size(3,3);
        Imgproc.GaussianBlur(matGray, matBlurred, s, 0);
        //showImage(toBufferedImage(matBlurred));

       // Imgproc.threshold(matBlurred, preprocessedImage, 130, 255, Imgproc.THRESH_BINARY);
        Imgproc.Canny(matBlurred, preprocessedImage, 300, 600, 5, true);
       // showImage(toBufferedImage(preprocessedImage));

        //test
    }

    
    private void detectPolygons(){
        if(image == null)
            return;

       // Mat matBinaryReversed= new Mat();
       // Core.bitwise_not(preprocessedImage, matBinaryReversed);
        ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = image.clone();
        Imgproc.findContours(preprocessedImage, contours,hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
       // showImage(toBufferedImage(preprocessedImage));

        int minCounturArea = getMinimumShapeArea();
        int maxCountyrSize = 1000;

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

    public void showImage(){
        showImage(toBufferedImage());
    }
    public void showImage(BufferedImage image){
        if (image == null) return;
        JDialog dialog = new JDialog();
        dialog.setUndecorated(false);
        JLabel label = new JLabel( new ImageIcon(image) );
        dialog.add( label );
        dialog.pack();
        dialog.setVisible(true);
    }
}
