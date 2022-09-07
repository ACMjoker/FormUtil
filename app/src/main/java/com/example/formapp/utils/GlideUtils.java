package com.example.formapp.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.formapp.R;

/**
 * @author liuyan
 */
public class GlideUtils {

    /**
     * 不使用内存缓存
     * 不使用磁盘缓存
     * @param context
     * @param url
     * @param imageHeade
     * skipMemoryCache 跳过内存缓存
     * diskCacheStrategy 跳过磁盘缓存
     */
    public static void showImage(Context context, String url, ImageView imageHeade){
        Glide.with(context).load(url)
                .placeholder(R.mipmap.icon_image_load)
                .error(R.mipmap.icon_image_error)
                .into(imageHeade);
      /*  Glide.with(context).load(url)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageHeade);*/
    }

    /**
     * 默认使用内存缓存和磁盘缓存
     * @param context
     * @param url
     * @param imageView
     */
    public static void showImageNoJupme(Context context, String url, ImageView imageView){
        Glide.with(context).load(url).into(imageView);
    }
    /**
     * 默认使用内存缓存和磁盘缓存
     * @param context
     * @param url
     * @param imageView
     * @param size 你传了0.1f作为参数，那么Glide则会显示原图大小的10%。如果原图的尺寸是1000x1000像素，那么缩略图就是100x100像素。由于缩略图会比ImageView要小许多，
     *             所以你要确保一个准确的缩放比例
     */
    public static void showImageNoJupme(Context context, String url, ImageView imageView,float size){
        Glide.with(context).load(url).placeholder(R.mipmap.icon_image_load)
                .error(R.mipmap.icon_image_error).thumbnail(size).into(imageView);
    }
    /**
     *
     * @param context
     * @param url
     * @param mipmapImageview   默认的占位图片
     * @param errorImageview    加载错误显示的图片
     * @param imageView         显示在哪个空间上
     */
    public static void showImagePlace(Context context, String url, int mipmapImageview, int errorImageview, ImageView imageView){
        Glide.with(context).load(url)
                .skipMemoryCache(true)
                .placeholder(mipmapImageview)
                .error(errorImageview)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }

    /**
     * 头像圆角
     * @param context 上下文
     * @param url 图片地址
     * @param imageHeade 显示在哪个ImageView上
     */
    public static void showImageCircle(Context context, String url, ImageView imageHeade){
        Glide.with(context).load(url)
                .transform(new CenterCrop(), new RoundedCorners(10))
//                .skipMemoryCache(false)   //跳过内存缓存
//                .diskCacheStrategy(DiskCacheStrategy.NONE) // 跳过磁盘缓存
                .into(imageHeade);
    }




}
