package com.example.bannerapp.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;


import java.util.List;

/**
 * Created by XHD on 2020/11/15
 */
public class BannerPagerAdapter extends PagerAdapter {
    private List<View> views;

    public BannerPagerAdapter(List<View> views) {
        this.views = views;
    }

    /**
     * 为给定位置创建页面
     *
     * @param 容器显示页面的包含视图。
     * @param 定位要实例化的页面位置。
     * @return 返回一个表示新页面的对象。
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //第一种写法 必须不低于4张图片 ，3张图片会使一个容器中先出现两个相同view而报异常
//        position = position % views.size();
//        if (position < 0) {
//            position = views.size() + position;
//        }
//        View view = views.get(position);
//        container.addView(view);
        String tag=position+"";
        //第二种写法 至少不低于3张图片，否则效果不好
        position = position % views.size();
        if (position < 0) {
            position = views.size() + position;
        }
        View view = views.get(position);
        //因为一个容器中不能添加两个相同view所以需要先移除之前的view
        ViewParent vp = view.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(view);//该处移除场景适用于无限左滑右滑且view不低于3的情况，否则效果不合适
        }
        container.addView(view);
        return view;
    }

    /**
     * 删除给定位置的页面。
     *
     * @param container将从中删除页面的包含视图。
     * @param position要删除的页面位置。
     * @param object返回的对象
     */

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //用第一种写法需要使用这个方法
//        container.removeView((View) object);//此处如果移除view，那么container最多只会缓存3个view
    }

    /**
     * 必须实现，其判断View是否是强转而来，可以直接对其进行比较
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    /**
     * 告诉viewpager一共有多少个Item
     */
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
//        return views.size();
    }

}
