package com.codebind;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel{

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        repaint();
    }

    public void setImage(String imagePath) {
        try {
            image = ImageIO.read(new File(imagePath));
            repaint();
        } catch (IOException e) {
            System.out.println(imagePath);
            e.printStackTrace();

        }
    }

    private BufferedImage image;
    private JPoint imageStartPoint;
    private JPoint imageScalePoint;

    public ImagePanel(){
        imageStartPoint = new JPoint();
        imageScalePoint = new JPoint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //calculateScale();
        //g.drawImage(image, imageStartPoint.x, imageStartPoint.y, imageScalePoint.x, imageScalePoint.y, this);
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    }

    private void calculateScale(){
        boolean isImageWidther = this.getWidth() < image.getWidth();
        boolean isImageHigher = this.getHeight() < image.getHeight();

        if(isImageHigher && isImageWidther){
            imageStartPoint.setY(0);
            imageScalePoint.setY(this.getHeight());

            imageStartPoint.setX(0);
            imageScalePoint.setX(this.getWidth());
        }
        else {
            int scaleFactor = (this.getHeight() - image.getHeight()) / 2;
            imageStartPoint.setY(scaleFactor);
            imageScalePoint.setY(image.getHeight());

            int scaleFactorX = (this.getWidth() - image.getWidth()) / 2;
            imageStartPoint.setX(scaleFactorX);
            imageScalePoint.setX(image.getWidth());
        }

        if(isImageHigher && !isImageWidther){
            imageStartPoint.setY(0);
            imageScalePoint.setY(this.getHeight());
        }
        else {
            int scaleFactor = (this.getHeight() - image.getHeight()) / 2;
            imageStartPoint.setY(scaleFactor);
            imageScalePoint.setY(scaleFactor + image.getHeight());
        }

        if(isImageWidther && !isImageHigher){
            imageStartPoint.setX(0);
            imageScalePoint.setX(this.getWidth());
        }
        else {
            int scaleFactorX = (this.getWidth() - image.getWidth()) / 2;
            imageStartPoint.setX(scaleFactorX);
            imageScalePoint.setX(scaleFactorX + image.getWidth());
        }
    }
}