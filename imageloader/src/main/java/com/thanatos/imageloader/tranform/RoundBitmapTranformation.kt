package com.thanatos.imageloader.tranform

import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

class RoundBitmapTranformation(val radius: Int) : BitmapTransformation() {
    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        return roundCrop(pool, toTransform)
    }

    /**
     * Glide 使用 LruBitmapPool 作为默认的 BitmapPool 。
     * LruBitmapPool 是一个内存中的固定大小的 BitmapPool，
     * 使用 LRU 算法清理。默认大小基于设备的分辨率和密度，
     * 同时也考虑内存类和 isLowRamDevice 的返回值。
     * 具体的计算通过 Glide 的 MemorySizeCalculator 来完成，
     * 与 Glide 的 MemoryCache 的大小检测方法相似。
     * @param pool
     * @param toTransform
     * @return
     */
    private fun roundCrop(pool: BitmapPool, toTransform: Bitmap): Bitmap {
        val bw = toTransform.width
        val bh = toTransform.height

        //由于有这个对象，可以这样的获取尺寸，方便对图片的操作，和对垃圾的回收
        val target = pool.get(bw, bh, Bitmap.Config.ARGB_8888)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.isAntiAlias = true
        val f = RectF(0f, 0f, bw.toFloat(), bh.toFloat())
        val canvas = Canvas(target)
        canvas.drawRoundRect(f, radius.toFloat(), radius.toFloat(), paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(toTransform, 0f, 0f, paint)
        return target
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {

    }
}