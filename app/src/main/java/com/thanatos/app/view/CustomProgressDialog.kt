package com.thanatos.app.view

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.view.Gravity
import com.thanatos.app.R
import kotlinx.android.synthetic.main.custom_progress_dialog.*

class CustomProgressDialog(context: Context, var message: String = "") : Dialog(context, R.style.Custom_Progress) {

    private var isShow = true

    init {
        setContentView(R.layout.custom_progress_dialog)
        setTitle("")
        id_tv_loadingmsg.text = message
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        val animationDrawable = loadingImageView.background as AnimationDrawable
        if(hasFocus){
            animationDrawable.start()
        }else{
            animationDrawable.stop()
        }
    }
    override fun show() {
        if (isShow) {
            // 按返回键是否取消
            setCancelable(true)
            // 设置居中
            window!!.attributes.gravity = Gravity.CENTER
            val lp = window!!.attributes
            // 设置背景层透明度
            lp.dimAmount = 0.0f
            window!!.attributes = lp
            isShow = false
        }
        super.show()
    }
}