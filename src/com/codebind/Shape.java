package com.codebind;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;

/**
 * Created by Pawel on 09.11.2016.
 */
public class Shape{
    private ShapeType type;
    private MatOfPoint shape;

    private final Scalar circleColour= new Scalar(0,0,255); //red
    private final Scalar squareColour= new Scalar(255,0,0); //
    private final Scalar triangleColour= new Scalar(0,255,0);
    private final Scalar octogonalColour= new Scalar(255,0,255);

    public Shape(MatOfPoint shape, ShapeType type){
        this.shape = shape;
        this.type = type;
    }

    public MatOfPoint getShape(){
        return shape;
    }

    public ShapeType getType(){
        return type;
    }

    public Scalar getColor() {
        switch (type){
            case Octogon:
                return octogonalColour;
            case Square:
                return squareColour;
            case Triangle:
                return triangleColour;
            case Circle:
                return circleColour;
        }

        return new Scalar(0,0,0);
    }
}
