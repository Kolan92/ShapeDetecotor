package com.filters;

import java.util.ArrayList;

/**
 * Created by Pawel on 25.03.2017.
 */
public class FilterBuilder {
    private ArrayList<Filter> filters;

    public FilterBuilder(){
        filters = new ArrayList<Filter>();
    }

    public FilterBuilder WithBinaryFilter(){
        BinaryFilter filter = new BinaryFilter();
        filters.add(filter);

        return this;
    }

    public FilterBuilder WithBinaryFilter(int lowThreshold, int highThreshold){
        BinaryFilter filter = new BinaryFilter(lowThreshold, highThreshold);
        filters.add(filter);

        return this;
    }

    public FilterBuilder WithDilateFilter(){
        DilateFilter filter = new DilateFilter();
        filters.add(filter);

        return this;
    }

    public FilterBuilder WithErodeFilter(){
        ErodeFilter filter = new ErodeFilter();
        filters.add(filter);

        return this;
    }

    public FilterBuilder WithGaussianBlurFilter(){
        GaussianBlurFilter filter = new GaussianBlurFilter();
        filters.add(filter);

        return this;
    }
    public FilterBuilder WithGaussianBlurFilter(int size){
        GaussianBlurFilter filter = new GaussianBlurFilter(size);
        filters.add(filter);

        return this;
    }

    public FilterBuilder WithMedianaBlurFilter(){
        MedianaBlurFilter filter = new MedianaBlurFilter();
        filters.add(filter);

        return this;
    }

    public FilterBuilder WithCanyFilter(){
        CanyFilter filter = new CanyFilter();
        filters.add(filter);

        return this;
    }

    public FilterBuilder WithCanyFilter(double sigma){
        CanyFilter filter = new CanyFilter(sigma);
        filters.add(filter);

        return this;
    }

    public FilterBuilder WithGrayScaleFilter(){
        GrayScaleFilter filter = new GrayScaleFilter();
        filters.add(filter);

        return this;
    }

    public FilterBuilder WithClosingFilter(){
        ClosingFilter filter = new ClosingFilter();
        filters.add(filter);

        return this;
    }

    public FilterBuilder WithClosingFilter(int size){
        ClosingFilter filter = new ClosingFilter(size);
        filters.add(filter);

        return this;
    }

    public FilterBuilder WithFastNlMeansDenoisingFilter(){
        FastNlMeansDenoisingFilter filter = new FastNlMeansDenoisingFilter();
        filters.add(filter);

        return this;
    }

    public FilterBuilder WithEqualizeHistogramFilter(){
        EqualizeHistogramFilter filter = new EqualizeHistogramFilter();
        filters.add(filter);

        return this;
    }

    public Filter Build(){
        for (int i = 0; i < filters.size() - 1; i++ ){
            Filter sucessor = filters.get(i+1);
            filters.get(i).setSuccessor(sucessor);
        }
        return filters.get(0);
    }
}
