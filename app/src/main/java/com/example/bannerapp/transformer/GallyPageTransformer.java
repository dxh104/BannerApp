package com.example.bannerapp.transformer;


import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

/**
 * Created by XHD on 2020/11/15
 */
public class GallyPageTransformer implements PageTransformer {
    private float minScale;

    public void setMinScale(float minScale) {
        this.minScale = minScale;
    }

    @Override
    public void transformPage(@NonNull View page, float position) {
        float scaleFactor = Math.max(minScale, 1 - Math.abs(position));
//        float rotate = 20 * Math.abs(position);
//        if (position < -1) {
//
//        } else if (position < 0) {
//            page.setScaleX(scaleFactor);
//            page.setScaleY(scaleFactor);
////            page.setRotationY(rotate);
//        } else if (position >= 0 && position < 1) {
//            page.setScaleX(scaleFactor);
//            page.setScaleY(scaleFactor);
////            page.setRotationY(-rotate);
//        } else if (position >= 1) {
//            page.setScaleX(scaleFactor);
//            page.setScaleY(scaleFactor);
////            page.setRotationY(-rotate);
//        }

        page.setScaleX(scaleFactor);
        page.setScaleY(scaleFactor);
    }
}
