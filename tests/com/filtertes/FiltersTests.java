package com.filtertes;

import com.detectors.ImageShapeDetector;
import com.filters.Filter;
import com.filters.FilterBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

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
    public void Filter_Preprocessor_Chanell0_Dilate_Threshold_Erode(){
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


