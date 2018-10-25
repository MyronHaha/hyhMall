package com.example.myron.heyihui.com.example.myron.heyihui.utils;

import android.app.Activity;
import android.content.Context;

import com.example.myron.heyihui.R;
import com.recker.flybanner.FlyBanner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2017/10/20.
 */

public class Banner {
    /**
     *  加载本地图片
     */
    public static  void  initLocalBanner(Context context, int viewId,FlyBanner mBannerLocal,int[] imagesResource)  {
        mBannerLocal  =  (FlyBanner) ((Activity)context).findViewById(viewId);

        List<Integer>  images  =  new ArrayList<>();
        for(int i = 0;i<imagesResource.length; i++ ){
            images.add(imagesResource[i]);
        }
//        images.add(R.drawable.img_1);
//        images.add(R.drawable.img_2);
//        images.add(R.drawable.img_3);
        mBannerLocal.setImages(images);

//        mBannerLocal.setOnItemClickListener(new  FlyBanner.OnItemClickListener()  {
//            @Override
//            public  void  onItemClick(int  position)  {
//                Toast.makeText(MainActivity.this,"点击了第"  +  position  +  "张图片",Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    /**
     *  加载网页图片
     */
    public static  void  initNetBanner(Context context, int viewId,FlyBanner mBannerNet,List imgesUrl)  {
        mBannerNet.setImagesUrl(imgesUrl);
    }

}
