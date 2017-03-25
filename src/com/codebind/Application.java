package com.codebind;

import org.opencv.core.Mat;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Pawel on 25.10.2016.
 */
public class Application {
    enum FileType {Image, Video}

    private static JFrame frame;
    private JButton loadImageButton;
    private JPanel mainPanel;
    private ImagePanel imagePanel;
    private JButton findCirclesButton;
    private JButton loadVideoButton;
    private JButton captureVideoButton;
    private ImagePanel prepocessedImage;
    private JSlider minValueSlider;
    private JSlider maxValueSlider;
    private FileType fileType = FileType.Image;
    private ShapeDetector detector = new ImageShapeDetector();


    public Application()  {
        loadImageButton.addActionListener(e -> loadImage());
        loadVideoButton.addActionListener(e -> loadVideo());
        captureVideoButton.addActionListener(e -> captureVideo());
        findCirclesButton.addActionListener(e -> findCircles());

        minValueSlider.addChangeListener(e -> detector.setMinimumTreshold(minValueSlider.getValue()));
        maxValueSlider.addChangeListener(e -> detector.setMaxThreshold(maxValueSlider.getValue()));
    }

    public static void main(String[] args) {
        Application.frame = new JFrame("ShapeDetector");
        frame.setContentPane(new Application().mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void loadImage() {
        fileType = FileType.Image;
        String imagePath = OpenImageFileDialog();
        imagePanel.setImage(imagePath);
    }

    private void captureVideo(){
        VideoShapeDetector videoShapeDetector = new VideoShapeDetector();
        startVideo(videoShapeDetector);
    }

    private void loadVideo(){
        fileType = FileType.Video;
        String videoPath = OpenVideoFileDialog();
        VideoShapeDetector videoShapeDetector = new VideoShapeDetector();
        videoShapeDetector.OpenVideo(videoPath);
        startVideo(videoShapeDetector);
        detector = videoShapeDetector;

    }

    private void startVideo(VideoShapeDetector videoShapeDetector){
        SwingWorker<Void, Mat> worker = new SwingWorker<Void, Mat>() {
            @Override
            protected Void doInBackground() throws Exception {
                while(!isCancelled()) {
                    if (videoShapeDetector.detectShapes()) {
                        imagePanel.setImage(videoShapeDetector.GetFrame());
                        prepocessedImage.setImage(videoShapeDetector.GetPreprocessedFrame());
                    }
                }
                return null;
            }
        };

        worker.execute();
    }

    private String OpenImageFileDialog(){
        final String imageFileTypes = "*.jpg;*.jpeg;*.png";
        return OpenFileDialog(imageFileTypes);
    }

    private String OpenVideoFileDialog(){
        final String imageFileTypes = "*.mp4;*.avi";
        return OpenFileDialog(imageFileTypes);
    }

    private String OpenFileDialog(String fileTypes){
        FileDialog fileDialog = new FileDialog(frame, "Choose a file", FileDialog.LOAD);
        fileDialog.setDirectory("C:\\");
        fileDialog.setFile(fileTypes);
        fileDialog.setVisible(true);
        String filename = fileDialog.getFile();
        String directory = fileDialog.getDirectory();
        StringBuffer buf = new StringBuffer(directory);
        buf.append(filename);
        return buf.toString();
    }


    private void findCircles(){
        BufferedImage image = imagePanel.getImage();
        ImageShapeDetector detector = new ImageShapeDetector(image);
        detector.detectShapes();
        detector.drawBoxes();

        image = detector.toBufferedImage();
        imagePanel.setImage(image);
        prepocessedImage.setImage(detector.toBufferedImage(detector.getPreprocessedImage()));
        this.detector = detector;
    }

}

