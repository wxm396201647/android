package com.thanatos.app.extension

import android.app.Activity
import android.content.Context
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.TextView
import android.widget.Toast
import com.thanatos.app.R

import java.text.SimpleDateFormat
import java.util.*



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
            var date = Date(timeMillis)
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
            var date = Date(timeMillis.toLong())
            val sdf = SimpleDateFormat(format, Locale.CHINA)
            sdf.format(date)
        } catch (e: Exception) {
            ""
        }
    } else {
        ""
    }
}