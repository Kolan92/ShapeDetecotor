package com.imageProcessing;

import com.filters.*;
import org.opencv.core.Core;
import org.opencv.core.Mat;

/**
 * Created by Pawel on 03.05.2017.
 */
public class PreProcessor  extends Filter{
    private HLSFilter hlsFilter;
    private SegBlueNormHueFilter segBlueNormHueFilter;
    private SegRedNormHueFilter segRedNormHueFilter;
    private SegLogChromaticFilter segLogChromaticFilter;

    public PreProcessor(){
        hlsFilter = new HLSFilter();
        segBlueNormHueFilter = new SegBlueNormHueFilter();
        segRedNormHueFilter = new SegRedNormHueFilter();
        segLogChromaticFilter = new SegLogChromaticFilter();
    }

    @Override
    public Mat applyTo(Mat image) throws Exception {
        Mat hlsImage = hlsFilter.applyTo(image);
        Mat segNhsRedImage = segRedNormHueFilter.applyTo(hlsImage);
        Mat segNhsBlueImage = segBlueNormHueFilter.applyTo(hlsImage);
        Mat segLogarythmicImage = segLogChromaticFilter.applyTo(image);

        processedImage = mergeImages(segNhsRedImage, segNhsBlueImage, segLogarythmicImage);
        if(successor !=null)
            return successor.applyTo(processedImage);
        return processedImage;
    }

    private Mat mergeImages(Mat segNhsRedImage, Mat segNhsBlueImage, Mat segLogarythminImage){
        Mat mergedRedImage = segNhsRedImage.clone();
        Mat mergedAllImages = segNhsRedImage.clone();

        Core.bitwise_or(segNhsRedImage, segLogarythminImage, mergedRedImage);
        Core.bitwise_or(mergedRedImage, segNhsBlueImage, mergedAllImages);

        return mergedAllImages;
    }
}
