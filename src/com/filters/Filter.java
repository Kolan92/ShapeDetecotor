package com.filters;

import org.opencv.core.Mat;

/**
 * Created by Pawel on 25.03.2017.
 */
public abstract class Filter {
    protected Filter successor;
    protected Mat processedImage;

    public void setSuccessor(Filter successor){
        this.successor = successor;
    }

    public abstract Mat applyTo(Mat image) throws Exception;

    public Filter(){
        processedImage = new Mat();
    }

    @Override
    public String toString() {
        String filterName =this.getClass().getName();
        if(successor != null){
           return filterName + successor.toString();
        }
        return filterName ;
    }
}


