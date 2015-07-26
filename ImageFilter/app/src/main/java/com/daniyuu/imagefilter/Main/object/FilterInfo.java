package com.daniyuu.imagefilter.Main.object;

import com.daniyuu.imagefilter.IImageFilter;

/**
 * Created by Daniyuu on 7/26/15.
 */
public class FilterInfo {
    public int filterID;
    public IImageFilter filter;

    public FilterInfo(int filterID, IImageFilter filter) {
        this.filterID = filterID;
        this.filter = filter;
    }
}