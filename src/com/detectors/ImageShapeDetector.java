package com.detectors;

import com.codebind.Shape;
import com.codebind.ShapeType;
import com.filters.Filter;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;

/**
 * Created by Pawel on 25.03.2017.
 */
public class ImageShapeDetector {
    private final double MAX_TOL = 0.08;

    private Mat image;
    private Mat processeImage;
    private Filter filter;
    private ArrayList<Shape> shapes = new ArrayList<Shape>();

    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    @Deprecated //only for testing
    public Filter GetFilter(){
        return filter;
    }

    public void setImage(BufferedImage image){
        shapes.clear();
        this.image = imageToMat(image);
    }
    public void setImage(Mat image){
        shapes.clear();
        this.image = image;
    }
    public void setFilter(Filter filter){
        this.filter = filter;
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

    public boolean detectShapes() throws Exception {
        processeImage = filter.applyTo(image);
        detectPolygons();
        return true;
    }

    private void detectPolygons(){
        if(processeImage == null)
            return;
        ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();
        System.out.println("processeImage size " + processeImage.size());
        System.out.println("processeImage type " + processeImage.type());
        System.out.println("processeImage channels " + processeImage.channels());

        Imgproc.findContours(processeImage, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        ArrayList<MatOfInt> hullCountours = new ArrayList<MatOfInt>();
        ArrayList<MatOfPoint> newCountours = new ArrayList<MatOfPoint>();
        for(MatOfPoint conour:contours) {
            MatOfInt hullContour = new MatOfInt();
            Imgproc.convexHull(conour, hullContour, false);
            //hullCountours.add(temp);
            newCountours.add(convertIndexesToPoints(conour, hullContour));
        }
        int minCounturArea = getMinimumShapeArea();

        int numberOfCounturs = newCountours.size();
        for (int idx = 0; idx < numberOfCounturs; idx++) {
            MatOfPoint contour = newCountours.get(idx);
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

    public BufferedImage getResultImage(){
        return getImage(image);
    }
    public BufferedImage getProcessedImage(){
        return getImage(processeImage);
    }

    public static BufferedImage getImage(Mat mat){
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

    public static MatOfPoint convertIndexesToPoints(MatOfPoint contour, MatOfInt indexes) {
        int[] arrIndex = indexes.toArray();
        Point[] arrContour = contour.toArray();
        Point[] arrPoints = new Point[arrIndex.length];

        for (int i=0;i<arrIndex.length;i++) {
            arrPoints[i] = arrContour[arrIndex[i]];
        }

        MatOfPoint hull = new MatOfPoint();
        hull.fromArray(arrPoints);
        return hull;
    }
}
