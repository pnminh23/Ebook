package com.example.ebook_pnminh.model;

import com.denzcoskun.imageslider.models.SlideModel;

public class SlideItem {
    private String id;
    private SlideModel slideModel;

    public SlideItem(String id, SlideModel slideModel) {
        this.id = id;
        this.slideModel = slideModel;
    }

    public String getId() {
        return id;
    }

    public SlideModel getSlideModel() {
        return slideModel;
    }
}
