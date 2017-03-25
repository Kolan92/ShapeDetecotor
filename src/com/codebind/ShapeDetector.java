package com.codebind;

import java.awt.image.BufferedImage;

public interface ShapeDetector{
    boolean detectShapes();
    void setMinimumTreshold(int minThreshold);
    void setMaxThreshold(int maxThreshold);
    BufferedImage toBufferedImage();
}
