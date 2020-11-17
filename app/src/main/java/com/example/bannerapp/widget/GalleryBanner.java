package com.example.bannerapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.bannerapp.R;
import com.example.bannerapp.adapter.BannerPagerAdapter;
import com.example.bannerapp.transformer.GallyPageTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XHD on 2020/11/15 画廊轮播图 只适合不低于3张图片的情况
 */
public class GalleryBanner extends RelativeLayout {
    private float minScaleBias;//最小缩放系数
    private float galleryWidth;//画廊图片宽度
    private Context mContext;
    private ViewPager mViewPager;
    private List<View> viewList;

    public GalleryBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        viewList = new ArrayList<>();
        initAttrs(attrs);
        initView();
        setClipChildren(false);//允许边缘出界
    }


    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.GalleryBanner);
        minScaleBias = typedArray.getFloat(R.styleable.GalleryBanner_minScaleBias, 0.5f);
        galleryWidth = typedArray.getLayoutDimension(R.styleable.GalleryBanner_galleryWidth, 100);
        typedArray.recycle();
    }

    private void initView() {
        mViewPager = new ViewPager(mContext);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);//addRule参数对应RelativeLayout XML布局的属性
        mViewPager.setLayoutParams(layoutParams);
        this.addView(mViewPager);
    }

    public void setdata(List<String> urls) {
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (urls != null && urls.size() > 0) {
            for (int i = 0; i < urls.size(); i++) {
                String url = urls.get(i);
                ImageView imageView = new ImageView(mContext);
                imageView.setLayoutParams(lp);
                Glide.with(mContext).load(url).into(imageView);
                viewList.add(imageView);
            }
            BannerPagerAdapter bannerPagerAdapter = new BannerPagerAdapter(viewList);
            GallyPageTransformer gallyPageTransformer = new GallyPageTransformer();
            gallyPageTransformer.setMinScale(minScaleBias);
            mViewPager.setPageTransformer(true, gallyPageTransformer);
            mViewPager.setAdapter(bannerPagerAdapter);
            int part = Integer.MAX_VALUE / viewList.size();//段数  数据必须不低于3
            mViewPager.getLayoutParams().width = (int) galleryWidth;//重新设置画廊宽度
            mViewPager.setCurrentItem(viewList.size() * (part / 2));//设置条目位置---较中间位置
            mViewPager.setPageMargin(-50);
            mViewPager.setOffscreenPageLimit(viewList.size() / 2);
        }
    }

    float upX = 0;
    //点击翻页
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                int left = mViewPager.getLeft();
                int mViewPagerWidth = mViewPager.getWidth();
                upX = event.getX();//相对自身左上角
                if (upX < left) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                }
                if (upX > left + mViewPagerWidth) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                }
                break;
        }
        return true;
    }
}
