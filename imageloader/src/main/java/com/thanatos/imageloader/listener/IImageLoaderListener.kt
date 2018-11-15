package com.thanatos.imageloader.listener

import android.widget.ImageView

/**
 * Created by wangming on 2018/11/15 .
 * Describe:
 */
interface IImageLoaderListener {
    //监听图片下载错误
    fun onLoadingFailed(url: String, target: ImageView, exception: Exception?)

    //监听图片加载成功
    fun onLoadingComplete(url: String, target: ImageView)
}