package com.thanatos.app.extension

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.thanatos.app.R
import com.thanatos.imageloader.ImageLoader

import java.text.SimpleDateFormat
import java.util.*

fun Fragment.displayImage(url: String, imageView: ImageView) {
    ImageLoader.getInstance().displayImage(this.activity, url, imageView)
}

fun Activity.displayImage(url: String, imageView: ImageView) {
    ImageLoader.getInstance().displayImage(this, url, imageView)
}

fun ImageView.displayImage(context: Context, url: String) {
    ImageLoader.getInstance().displayImage(context, url, this)
}

fun ImageView.displayRoundImage(context: Context, url: String, def: Int = 0, radius: Int = 5) {
    ImageLoader.getInstance().displayRoundImage(context, url, this, def, radius)
}

fun Context.toast(resId: Int, duration: Int = Toast.LENGTH_SHORT) =
        Toast.makeText(this, resId, duration).show()

fun Context.toast(message: String?, duration: Int = Toast.LENGTH_SHORT) =
        Toast.makeText(this, message, duration).show()

fun Activity.rotateAnimation(isRotate: Boolean, view: TextView, text: String) {
    val animation = AnimationUtils.loadAnimation(this, R.anim.anim_rotate_view)
    animation.interpolator = LinearInterpolator()
    if (isRotate) {
        view.text = ""
        view.setBackgroundResource(R.mipmap.ic_rotate)
        view.startAnimation(animation)
    } else {
        view.text = text
        view.setBackgroundResource(0)
        view.clearAnimation()
    }

}

fun String.formateDate(timeMillis: Long?, format: String = "yyyy-MM-dd HH:mm:ss"): String {
    return if (null != timeMillis) {
        try {
            val date = Date(timeMillis)
            val sdf = SimpleDateFormat(format, Locale.CHINA)
            sdf.format(date)
        } catch (e: Exception) {
            ""
        }
    } else {
        ""
    }
}

fun String.formateDate(timeMillis: String?, format: String = "yyyy-MM-dd HH:mm:ss"): String {
    return if (null != timeMillis) {
        try {
            val date = Date(timeMillis.toLong())
            val sdf = SimpleDateFormat(format, Locale.CHINA)
            sdf.format(date)
        } catch (e: Exception) {
            ""
        }
    } else {
        ""
    }
}

fun View.dip2px(dipValue: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (dipValue * scale + 0.5f).toInt()
}

fun View.px2dip(pxValue: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

fun durationFormat(duration: Long?): String {
    val minute = duration!! / 60
    val second = duration % 60
    return if (minute <= 9) {
        if (second <= 9) {
            "0$minute' 0$second''"
        } else {
            "0$minute' $second''"
        }
    } else {
        if (second <= 9) {
            "$minute' 0$second''"
        } else {
            "$minute' $second''"
        }
    }
}

/**
 * 数据流量格式化
 */
fun Context.dataFormat(total: Long): String {
    val result: String
    val speedReal: Int = (total / (1024)).toInt()
    result = if (speedReal < 512) {
        "$speedReal KB"
    } else {
        val mSpeed = speedReal / 1024.0
        (Math.round(mSpeed * 100) / 100.0).toString() + " MB"
    }
    return result
}
