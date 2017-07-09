package com.filtertes;

import com.detectors.ImageShapeDetector;
import com.filters.Filter;
import com.filters.FilterBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Pawel on 25.03.2017.
 */
public class FiltersTests {
    private FilterBuilder builder;
    private ImageShapeDetector detector;
    private long millisTestStart;
    private String testName;
    private String usedFiltres;
    private String imageName;
    private File[] testData;
    private ArrayList<TestMetaData> testDatas;


    @Before
    public void SetUp(){
        builder = new FilterBuilder();
        detector = new ImageShapeDetector();
        millisTestStart = System.currentTimeMillis();
        testDatas = new ArrayList<>();
    }


    @Test
    @Ignore
    public void Filter_GrayScale_Gausian() {
        Filter filter = builder.WithGrayScaleFilter()
                .WithGaussianBlurFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }

    @Test
    public void Filter_GrayScale_Gausian_Mediana_Cany_Threshold() {
        Filter filter = builder.WithGrayScaleFilter()
                .WithGaussianBlurFilter()
                .WithMedianaBlurFilter()
                .WithCanyFilter()
                .WithBinaryFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_GrayScale_Gausian_Erode_Dilate_Mediana_Cany_Threshold() {
        Filter filter = builder.WithGrayScaleFilter()
                .WithGaussianBlurFilter()
                .WithErodeFilter()
                .WithDilateFilter()
                .WithMedianaBlurFilter()
                .WithCanyFilter()
                .WithBinaryFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_GrayScale_Erode_Dilate_Mediana_Cany_Threshold() {
        Filter filter = builder.WithGrayScaleFilter()
                .WithErodeFilter()
                .WithDilateFilter()
                .WithMedianaBlurFilter()
                .WithCanyFilter()
                .WithBinaryFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_GrayScale_Erode_Dilate_Mediana_Cany_Custom_Threshold() {
        Filter filter = builder.WithGrayScaleFilter()
                .WithErodeFilter()
                .WithDilateFilter()
                .WithMedianaBlurFilter()
                .WithCanyFilter()
                .WithBinaryFilter(150, 200)
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_HLS_Channel0_Erode_Dilate_Mediana_Cany_Custom_Threshold() {
        Filter filter = builder.WithHLSFilter()
                .WithChannelFilter(0)
                .WithErodeFilter()
                .WithDilateFilter()
                .WithMedianaBlurFilter()
                .WithCanyFilter()
                .WithBinaryFilter(150, 200)
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    } @Test
    public void Filter_HLS_Channel1_Erode_Dilate_Mediana_Cany_Custom_Threshold() {
        Filter filter = builder.WithHLSFilter()
                .WithChannelFilter(1)
                .WithErodeFilter()
                .WithDilateFilter()
                .WithMedianaBlurFilter()
                .WithCanyFilter()
                .WithBinaryFilter(150, 200)
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    } @Test
    public void Filter_HLS_Channel2_Erode_Dilate_Mediana_Cany_Custom_Threshold() {
        Filter filter = builder.WithHLSFilter()
                .WithChannelFilter(2)
                .WithErodeFilter()
                .WithDilateFilter()
                .WithMedianaBlurFilter()
                .WithCanyFilter()
                .WithBinaryFilter(150, 200)
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }

    @Test
    public void Filter_GrayScale_Gaussian_Canny_Closing() {
        Filter filter = builder.WithGrayScaleFilter()
                .WithGaussianBlurFilter()
                .WithCanyFilter()
                .WithClosingFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }

    @Test
    public void Filter_GrayScale_Custom_Gaussian_Canny_Closing() {
        Filter filter = builder.WithGrayScaleFilter()
                .WithGaussianBlurFilter(3)
                .WithCanyFilter()
                .WithClosingFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }

    @Test
    public void Filter_GrayScale_Custom_Gaussian_Canny_Small_Closing() {
        Filter filter = builder.WithGrayScaleFilter()
                .WithGaussianBlurFilter(3)
                .WithCanyFilter()
                .WithClosingFilter(3)
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_GrayScale_EqaliseHists_Custom_Gaussian_Canny_Small_Closing() {
        Filter filter = builder.WithGrayScaleFilter()
                .WithEqualizeHistogramFilter()
                .WithGaussianBlurFilter(3)
                .WithCanyFilter()
                .WithClosingFilter(3)
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_GrayScale_EqaliseHists_Custom_Gaussian_Canny() {
        Filter filter = builder.WithGrayScaleFilter()
                .WithEqualizeHistogramFilter()
                .WithGaussianBlurFilter(3)
                .WithCanyFilter()
                .WithClosingFilter(3)
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    @Ignore("To big gausian")
    public void Filter_GrayScale_Custom_Gaussian_Big_Canny_Closing() {
        Filter filter = builder.WithGrayScaleFilter()
                .WithGaussianBlurFilter(15)
                .WithCanyFilter()
                .WithClosingFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    @Ignore("slow")
    public void Filter_Denois_GrayScale_Custom_Gaussian_Canny_Closing() {
        Filter filter = builder.WithFastNlMeansDenoisingFilter()
                .WithGrayScaleFilter()
                .WithGaussianBlurFilter(3)
                .WithCanyFilter()
                .WithClosingFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }

    @Test
    @Ignore("Stupid")
    public void Filter_GrayScale_Erode_Dilate_Mediana_Cany_Gaussian_Threshold() {
        Filter filter = builder.WithGrayScaleFilter()
                .WithErodeFilter()
                .WithDilateFilter()
                .WithMedianaBlurFilter()
                .WithCanyFilter()
                .WithGaussianBlurFilter()
                .WithBinaryFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }

    @Test
    public void PreProcessorTest_With_Some_Filters(){
        Filter filter = builder.WithPreProcessor()
                .WithGrayScaleFilter()
                .WithGaussianBlurFilter(3)
                .WithCanyFilter()
                .WithClosingFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_HLS_Channel0_Gaussian3_Cany_Closing(){
        Filter filter = builder.WithHLSFilter()
                .WithChannelFilter(0)
                .WithGaussianBlurFilter(3)
                .WithCanyFilter()
                .WithClosingFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_HLS_Channel0_Gaussian_Cany_Closing(){
        Filter filter = builder.WithHLSFilter()
                .WithChannelFilter(0)
                .WithGaussianBlurFilter()
                .WithCanyFilter()
                .WithClosingFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_Gaussian_HLS_Channel0_Cany_Closing(){
        Filter filter = builder.WithGaussianBlurFilter().
                WithHLSFilter()
                .WithChannelFilter(0)
                .WithCanyFilter()
                .WithClosingFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_Gaussian_HLS_Channel0_Cany(){
        Filter filter = builder.WithGaussianBlurFilter().
                WithHLSFilter()
                .WithChannelFilter(0)
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }

    @Test
    public void Filter_Preprocessor_Chanell0_Dilate_Threshold_Erode_MultipleMediana(){
        Filter filter = builder
                .WithPreProcessor()
                .WithChannelFilter(0)
                .WithDilateFilter()
                .WithBinaryFilter(230, 255)
                .WithErodeFilter()
                .WithMultipleMedianaFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_Preprocessor_Chanell0_Dilate_Threshold230_255_Erode(){
        Filter filter = builder
                .WithPreProcessor()
                .WithChannelFilter(0)
                .WithDilateFilter()
                .WithBinaryFilter(230, 255)
                .WithErodeFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }@Test
    public void Filter_Preprocessor_Chanell1_Dilate_Threshold_Erode(){
        Filter filter = builder
                .WithPreProcessor()
                .WithChannelFilter(1)
                .WithDilateFilter()
                .WithBinaryFilter(230, 255)
                .WithErodeFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }@Test
    public void Filter_Preprocessor_Chanell2_Dilate_Threshold_Erode(){
        Filter filter = builder
                .WithPreProcessor()
                .WithChannelFilter(2)
                .WithDilateFilter()
                .WithBinaryFilter(230, 255)
                .WithErodeFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }

    @Test
    public void PreProcessorTest_GreyScale_Cany(){
        Filter filter = builder.WithPreProcessor()
                .WithGrayScaleFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void PreProcessorTest_GreyScale_MultipleMediana_Dilate_Cany(){
        Filter filter = builder.WithPreProcessor()
                .WithGrayScaleFilter()
                .WithMultipleMedianaFilter()
                .WithDilateFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }

    @Test
    public void PreProcessorTest_GreyScale_MultipleMediana_Erode_Cany(){
        Filter filter = builder.WithPreProcessor()
                .WithGrayScaleFilter()
                .WithMultipleMedianaFilter()
                .WithErodeFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void PreProcessorTest_GreyScale_MultipleMediana_Clsoing_Cany(){
        Filter filter = builder.WithPreProcessor()
                .WithGrayScaleFilter()
                .WithMultipleMedianaFilter()
                .WithClosingFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void PreProcessorTest_Channel0_Threshold_Cany(){
        Filter filter = builder.WithPreProcessor()
                .WithChannelFilter(0)
                .WithBinaryFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void PreProcessorTest_Channel0_Threshold240_255_Cany(){
        Filter filter = builder.WithPreProcessor()
                .WithChannelFilter(0)
                .WithBinaryFilter(240, 255)
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }    @Test
    public void PreProcessorTest_Channel0_Threshold150_255_Cany(){
        Filter filter = builder.WithPreProcessor()
                .WithChannelFilter(0)
                .WithBinaryFilter(150, 255)
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void PreProcessorTest_Channel1_Threshold_Cany(){
        Filter filter = builder.WithPreProcessor()
                .WithChannelFilter(1)
                .WithBinaryFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void PreProcessorTest_Channel2_Threshold_Cany(){
        Filter filter = builder.WithPreProcessor()
                .WithChannelFilter(2)
                .WithBinaryFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }


    @Test
    public void Filter_Preprocessor_Chanell0_Dilate_Threshold_Erode(){
        Filter filter = builder
                .WithPreProcessor()
                .WithChannelFilter(0)
                .WithDilateFilter()
                .WithBinaryFilter()
                .WithErodeFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_Preprocessor_Chanell0_Dilate_Threshold_Erode_Cany(){
        Filter filter = builder
                .WithPreProcessor()
                .WithChannelFilter(0)
                .WithDilateFilter()
                .WithBinaryFilter()
                .WithErodeFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_Preprocessor_Chanell0_Dilate_Binary_Erode_Gaussin_Cany(){
        Filter filter = builder
                .WithPreProcessor()
                .WithChannelFilter(0)
                .WithDilateFilter()
                .WithBinaryFilter()
                .WithErodeFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }

    @Test
    public void Filter_Preprocessor_Chanell0_Dilate_Binary_Gaussin_Cany(){
        Filter filter = builder
                .WithPreProcessor()
                .WithChannelFilter(0)
                .WithDilateFilter()
                .WithBinaryFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_Preprocessor_Chanell0_Closing_Binary_Gaussin_Cany(){
        Filter filter = builder
                .WithPreProcessor()
                .WithChannelFilter(0)
                .WithClosingFilter()
                .WithBinaryFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }

    @Test
    public void Filter_HLS_Chanell0_Dilate_Binary_Gaussin_Cany(){
        Filter filter = builder
                .WithHLSFilter()
                .WithChannelFilter(0)
                .WithDilateFilter()
                .WithBinaryFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_HLS_Chanell0_Dilate_Binary100_255_Gaussin_Cany(){
        Filter filter = builder
                .WithHLSFilter()
                .WithChannelFilter(0)
                .WithDilateFilter()
                .WithBinaryFilter(100, 255)
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }

    @Test
    public void Filter_Preprocessor_Chanell0_MuliMedianaClosing_Binary_Gaussin_Cany(){
        Filter filter = builder
                .WithPreProcessor()
                .WithChannelFilter(0)
                .WithMultipleMedianaFilter()
                .WithClosingFilter()
                .WithBinaryFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }

    @Test
    public void Filter_Preprocessor_Chanell0_Mediana_Closing_Binary_Gaussin_Cany(){
        Filter filter = builder
                .WithPreProcessor()
                .WithChannelFilter(0)
                .WithMedianaBlurFilter()
                .WithClosingFilter()
                .WithBinaryFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }

    @Test
    public void Filter_Preprocessor_Chanell0_Cany(){
        Filter filter = builder
                .WithPreProcessor()
                .WithChannelFilter(0)
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_Gaussian_Preprocessor_Chanell0_Cany(){
        Filter filter = builder
                .WithGaussianBlurFilter()
                .WithPreProcessor()
                .WithChannelFilter(0)
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_Preprocessor_Chanell0_closing_Cany(){
        Filter filter = builder
                .WithPreProcessor()
                .WithChannelFilter(0)
                .WithClosingFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }@Test
    public void Filter_Preprocessor_Chanell0_Erode_Cany(){
        Filter filter = builder
                .WithPreProcessor()
                .WithChannelFilter(0)
                .WithErodeFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }

    @Test
    public void Filter_NIDenoise_Preprocessor_Chanell0_Dilate_Binary_Erode_Gaussin_Cany(){
        Filter filter = builder
                .WithFastNlMeansDenoisingFilter()
                .WithPreProcessor()
                .WithChannelFilter(0)
                .WithDilateFilter()
                .WithBinaryFilter()
                .WithErodeFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }

    @Test
    public void Filter_Preprocessor_Chanell0_Dilate_Elipse_Threshold_Erode(){
        Filter filter = builder
                .WithPreProcessor()
                .WithChannelFilter(0)
                .WithDilateFilter(Imgproc.MORPH_ELLIPSE)
                .WithBinaryFilter(230, 255)
                .WithErodeFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    } @Test
    public void Filter_Preprocessor_Chanell0_Dilate_Elipse_Threshold230_255_Erode_Elipsis(){
        Filter filter = builder
                .WithPreProcessor()
                .WithChannelFilter(0)
                .WithDilateFilter(Imgproc.MORPH_ELLIPSE)
                .WithBinaryFilter(230, 255)
                .WithErodeFilter(Imgproc.MORPH_ELLIPSE)
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    } @Test
    public void Filter_Preprocessor_Chanell0_Dilate_Elipse_Threshold_Erode_Elipsis(){
        Filter filter = builder
                .WithPreProcessor()
                .WithChannelFilter(0)
                .WithDilateFilter(Imgproc.MORPH_ELLIPSE)
                .WithBinaryFilter()
                .WithErodeFilter(Imgproc.MORPH_ELLIPSE)
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }@Test
    public void Filter_HLS_Chanell0_Dilate_Elipse_Threshold_Erode_Elipsis(){
        Filter filter = builder
                .WithHLSFilter()
                .WithChannelFilter(0)
                .WithDilateFilter(Imgproc.MORPH_ELLIPSE)
                .WithBinaryFilter()
                .WithErodeFilter(Imgproc.MORPH_ELLIPSE)
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }@Test
    public void Filter_HLS_Chanell1_Dilate_Elipse_Threshold_Erode_Elipsis(){
        Filter filter = builder
                .WithHLSFilter()
                .WithChannelFilter(1)
                .WithDilateFilter(Imgproc.MORPH_ELLIPSE)
                .WithBinaryFilter()
                .WithErodeFilter(Imgproc.MORPH_ELLIPSE)
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_HSV_Chanell0_Dilate_Elipse_Threshold_Erode_Elipsis(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(0)
                .WithDilateFilter(Imgproc.MORPH_ELLIPSE)
                .WithBinaryFilter()
                .WithErodeFilter(Imgproc.MORPH_ELLIPSE)
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }@Test
    public void Filter_HSV_Chanell1_Dilate_Elipse_Threshold_Erode_Elipsis(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithDilateFilter(Imgproc.MORPH_ELLIPSE)
                .WithBinaryFilter()
                .WithErodeFilter(Imgproc.MORPH_ELLIPSE)
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }

    @Test
    public void Filter_HSV_Threshold_Gaussian_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithBinaryFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }

    @Test
    public void Filter_HSV_Chanell1_Gaussian_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithGaussianBlurFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_HSV_Chanell1_MultipleMediana_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithMultipleMedianaFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }

    //Important
    @Test
    public void Filter_HSV_Chanell1_MultipleMediana_Binary_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithMultipleMedianaFilter()
                .WithBinaryFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }

    @Test
    public void Filter_Threshold_Cany(){
        Filter filter = builder
                .WithBinaryFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }

    @Test
    public void Filter_HSV_Chanel1_Threshold_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithBinaryFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }


    @Test
    @Ignore
    public void Filter_HSV_Chanel1_MultiMediana_Gray_Threshold_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithMultipleMedianaFilter()
                .WithGrayScaleFilter()
                .WithBinaryFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_HSV_Chanell1_MultipleMediana_Binary100_250_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithMultipleMedianaFilter()
                .WithBinaryFilter(100,250)
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }


    @Test
    public void Filter_HSV_Chanell1_Gaussian_Erode_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithGaussianBlurFilter()
                .WithErodeFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_HSV_Chanell1_Binary_Gaussian_Erode_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithBinaryFilter()
                .WithGaussianBlurFilter()
                .WithErodeFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_HSV_Chanell1_Binary_Gaussian_Closing_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithBinaryFilter()
                .WithGaussianBlurFilter()
                .WithClosingFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_HSV_Chanel1_MultiMediana_Threshold_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithMultipleMedianaFilter()
                .WithBinaryFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_Gryeyscale_MultiMediana_Threshold_Cany(){
        Filter filter = builder
                .WithGrayScaleFilter()
                .WithMultipleMedianaFilter()
                .WithBinaryFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_Gryeyscale_Threshold_Cany(){
        Filter filter = builder
                .WithGrayScaleFilter()
                .WithBinaryFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_HSV_Chanell1_MultipleMediana_AdaptiveThreshold_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithMultipleMedianaFilter()
                .WithAdaptiveThresholdFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_HSV_Chanell1_Mediana_AdaptiveThreshold_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithMedianaBlurFilter()
                .WithAdaptiveThresholdFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }

    @Test
    public void Filter_HSV_Chanell1_Mediana_Mediana_Mediana_Mediana_Mediana_AdaptiveThreshold_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithAdaptiveThresholdFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }

    @Test
    public void Filter_HSV_Chanell1_Mediana_Mediana_Mediana_Mediana_Mediana_Mediana_Mediana_Mediana_Mediana_Mediana_AdaptiveThreshold_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithAdaptiveThresholdFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }    @Test
    public void Filter_HSV_Chanell1_10Mediana_Closing_AdaptiveThreshold_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithClosingFilter()
                .WithAdaptiveThresholdFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_HSV_Chanell1_10Mediana_Threshold_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithBinaryFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    } @Test
    public void Filter_HSV_Chanell1_7Mediana_Threshold_200_250_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithBinaryFilter(200, 250)
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    } @Test
    public void Filter_HSV_AddWeighted_Threshold_200_250_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithAddWeightedFilter()
                .WithBinaryFilter(200, 250)
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_HSV_AddWeighted_Opening_Closing_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithAddWeightedFilter()
                .WithOpeningFilter()
                .WithClosingFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_HSV_AddWeighted_Opening_Closing_HughCircles_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithAddWeightedFilter()
                .WithOpeningFilter()
                .WithClosingFilter()
                .WithHughCirclesFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }    @Test
    public void Filter_HSV_AddWeighted_Opening_Closing_HughCircles_Mediana2_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithAddWeightedFilter()
                .WithOpeningFilter()
                .WithClosingFilter()
                .WithHughCirclesFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_HSV_AddWeighted_Opening_Closing_Mediana2_HughLines_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithAddWeightedFilter()
                .WithOpeningFilter()
                .WithClosingFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithHughLinesFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Ignore
    @Test
    public void Filter_HSV_AddWeighted_Closing_Opening_HughCircles_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithAddWeightedFilter()
                .WithClosingFilter()
                .WithOpeningFilter()
                .WithHughCirclesFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    } @Test
    public void Filter_HSV_Chanell1_10Mediana_Threshold_Cany_Hugh(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithBinaryFilter()
                .WithCanyFilter()
                .WithHughCirclesFilter()
                //.AsMergeFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }  @Test
    public void Filter_HSV_Chanell1_10Mediana_Threshold150_255_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithBinaryFilter(150,255)
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }@Test
    public void Filter_HSV_Chanell1_10Mediana_Threshold150_255_Cany_Hugh(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithMedianaBlurFilter()
                .WithBinaryFilter(150,255)
                .WithCanyFilter()
                .WithHughCirclesFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_HSV_Chanell1_Gaussin_AdaptiveThreshold_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithGaussianBlurFilter()
                .WithAdaptiveThresholdFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_HSV_Chanell1_Gaussin_Threshold_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithGaussianBlurFilter()
                .WithBinaryFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }

    @Test
    public void Filter_HSV_Chanell1_Gaussin_Threshold150_255_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithGaussianBlurFilter()
                .WithBinaryFilter(150,255)
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }

    @Test
    @Ignore
    public void Filter_FastNlMeansDenoising_HSV_Chanell1_Gaussin_Threshold150_255_Cany(){
        Filter filter = builder
                .WithFastNlMeansDenoisingFilter()
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithGaussianBlurFilter()
                .WithBinaryFilter(150,255)
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }

    @Test
    public void Filter_HSV_Chanell1_Gaussin_AdaptiveThreshold_Cany_Hugh(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithGaussianBlurFilter()
                .WithAdaptiveThresholdFilter()
                .WithCanyFilter()
                .WithHughCirclesFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_HSV_Chanell1_Gaussin_AdaptiveThreshold_Cany_Opening(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithGaussianBlurFilter()
                .WithAdaptiveThresholdFilter()
                .WithCanyFilter()
                .WithOpeningFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }    @Test
    public void Filter_HSV_Chanell1_Gaussin_AdaptiveThreshold_Opening_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithGaussianBlurFilter()
                .WithAdaptiveThresholdFilter()
                .WithOpeningFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_HSV_Chanell1_Gaussin_AdaptiveThreshold_Cany_Hugh_Mediana(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithGaussianBlurFilter()
                .WithAdaptiveThresholdFilter()
                .WithCanyFilter()
                .WithHughCirclesFilter()
                .WithMedianaBlurFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }

    @Test
    public void Filter_HSV_Chanell1_Gaussin_Cany_Hugh_Mediana(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithGaussianBlurFilter()
                .WithCanyFilter()
                .WithHughCirclesFilter()
                .WithMedianaBlurFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    } @Test
    public void Filter_HSV_Chanell1_Gaussin_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithGaussianBlurFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }

    @Test
    public void Filter_HSV_Chanell1_Gaussin_Threshold_100_255_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithGaussianBlurFilter()
                .WithBinaryFilter(100,255)
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    @Test
    public void Filter_HSV_Chanell1_Gaussin_Threshold_100_255_Cany_Hugh(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1)
                .WithGaussianBlurFilter()
                .WithBinaryFilter(100,255)
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    } @Test
    public void Filter_HSV_Chanell1WaterSheed_Cany(){
        Filter filter = builder
                .WithHSVFilter()
                .WithChannelFilter(1).WithWaterSheedFilter()
                .WithCanyFilter()
                .Build();

        testName = new Object(){}.getClass().getEnclosingMethod().getName();
        usedFiltres = filter.toString();

        detector.setFilter(filter);
        assertDoesNotThrow(() -> detecShapes(detector));
    }
    public void detecShapes(ImageShapeDetector detector) throws Exception {
        getTestData();
        for(File file:testData){
            millisTestStart = System.currentTimeMillis();
            BufferedImage image = ImageIO.read(file);
            String currentFile = file.getName().replaceFirst("[.][^.]+$", "");
            detector.setImage(image);
            detector.detectShapes();
            detector.drawBoxes();
            writeResult(currentFile);

            String parentDir = GetMostRecentFolder().getAbsolutePath();

            File dir = new File(parentDir+ "\\"+testName);
            dir.mkdir();
            String resultPath = parentDir + "\\"+testName  +"\\result_" + currentFile + ".png";
            String filtredPath = parentDir + "\\"+testName + "\\filtred_" + currentFile+ ".png";
            File fiteredIamge = new File(filtredPath);
            File resultImage= new File(resultPath);
            ImageIO.write(detector.getResultImage(), "png", resultImage);
            ImageIO.write(detector.getProcessedImage(), "png", fiteredIamge);

            Filter currentFilter = detector.GetFilter();
            File stepdir = new File(parentDir+ "\\"+testName+ "\\"+currentFile);
            stepdir.mkdir();
            int step = 0;
            while (currentFilter!= null){
                BufferedImage filtredImage = ImageShapeDetector.getImage(currentFilter.getProcessedImage());
                if(filtredImage == null) {
                    step++;
                    currentFilter = currentFilter.getSuccessor();
                    continue;
                }
                String filterPath = parentDir + "\\"+testName+ "\\"+currentFile  +"\\filteredStep_"+step + currentFile + ".png";
                File filtredStep= new File(filterPath);
                ImageIO.write(filtredImage, "png", filtredStep);
                step++;
                currentFilter = currentFilter.getSuccessor();
            }
        }

        File parentDir = GetMostRecentFolder();
        String fileName = parentDir.getAbsolutePath() + "\\"+testName+"\\test_data.json";

        try (FileWriter writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(testDatas, writer);
        }
        catch(Exception ex){
            System.out.println(ex.getStackTrace());
        }
    }

    private void getTestData(){
        Path testDataDirectory = Paths.get("TestData");
        testData= testDataDirectory.toFile().listFiles();
    }

    public void writeResult(String currentFile){
        long timeExecution = System.currentTimeMillis() - millisTestStart;

        TestMetaData testData = new TestMetaData();
        testData.TestName =testName;
        testData.FiltersUsed = usedFiltres;
        testData.MilisExecutionTime = timeExecution;
        testData.FileName = currentFile;

        testDatas.add(testData);


    }

    private File GetMostRecentFolder(){
        Path parentFolder = Paths.get("TestOutput");
        Optional<File> mostRecentFolder = Arrays.stream(parentFolder.toFile().listFiles())
                                            .filter(f -> f.isDirectory())
                                                .max((f1, f2) -> Long.compare(f1.lastModified(),f2.lastModified()));
        return  mostRecentFolder.get();
    }

    public static void assertDoesNotThrow(FailingRunnable action){
        try{
            action.run();
        }
        catch(Exception ex){
            throw new Error("expected action not to throw, but it did!", ex);
        }
    }


    @FunctionalInterface interface FailingRunnable { void run() throws Exception; }

    private class TestMetaData{
        public String TestName;
        public String FiltersUsed;
        public String FileName;
        public long MilisExecutionTime;
    }
}


