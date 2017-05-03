package com.filters;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pawel on 03.05.2017.
 */
public class ChannelFilter extends Filter {
    private int channel;
    public ChannelFilter(int channel){
        this.channel = channel;
    }

    @Override
    public Mat applyTo(Mat image) throws Exception {
        List<Mat> mv = new ArrayList<>();
        Core.split(image, mv);
        processedImage = mv.get(channel);
        System.out.println("chanelProcesseImage size " + processedImage.size());
        System.out.println("chanelProcesseImage type " + processedImage.type());
        System.out.println("chanelProcesseImage channels " + processedImage.channels());
        if (successor != null)
            return successor.applyTo(processedImage);
        return processedImage;
    }
}
