package com.thanatos.app.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.thanatos.app.R
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshKernel
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import kotlinx.android.synthetic.main.view_refresh_header.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import android.view.animation.LinearInterpolator
import android.view.animation.Animation
import android.view.animation.RotateAnimation

class CommonRefreshHeader : RelativeLayout, RefreshHeader {
    override fun getSpinnerStyle(): SpinnerStyle = SpinnerStyle.Translate
    private val REFRESH_HEADER_LASTTIME = "上次更新 M-d HH:mm"
    var mFormat: DateFormat = SimpleDateFormat(REFRESH_HEADER_LASTTIME, Locale.CHINA)
    private val rotate by lazy{ RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)}
    private val lin by lazy { LinearInterpolator() }
    var lastDataText = ""

    override fun onFinish(layout: RefreshLayout, success: Boolean): Int {
        iv_refresh.clearAnimation()
        lastDataText = mFormat.format(Date())
        tv_loading.text = lastDataText
        return 300
    }

    override fun onInitialized(kernel: RefreshKernel, height: Int, extendHeight: Int) {
    }

    override fun onHorizontalDrag(percentX: Float, offsetX: Int, offsetMax: Int) {
    }

    override fun getView(): View = this

    override fun setPrimaryColors(vararg colors: Int) {
        if(colors.isNotEmpty())
            ll_refresh.setBackgroundColor( resources.getColor(colors[0]))
    }

    override fun onReleasing(percent: Float, offset: Int, headerHeight: Int, extendHeight: Int) {
        iv_refresh.startAnimation(rotate)

    }

    override fun onStartAnimator(layout: RefreshLayout, height: Int, extendHeight: Int) {
    }

    override fun onStateChanged(refreshLayout: RefreshLayout?, oldState: RefreshState?, newState: RefreshState?) {
       if( newState == RefreshState.Refreshing) tv_loading.setText(R.string.load_ing)
    }

    override fun onReleased(refreshLayout: RefreshLayout?, height: Int, extendHeight: Int) {

    }

    override fun onPulling(percent: Float, offset: Int, height: Int, extendHeight: Int) {

    }


    override fun isSupportHorizontalDrag(): Boolean = false

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        initView(context)
    }

    private fun initView(context: Context) {
        rotate.interpolator = lin
        rotate.duration = 1000//设置动画持续时间
        rotate.repeatCount = 10//设置重复次数
        rotate.fillAfter = true//动画执行完后是否停留在执行完的状态
        rotate.startOffset = 10//执行前的等待时间


        val view = View.inflate(context, R.layout.view_refresh_header, null)
        val layoutParams = RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        addView(view, layoutParams)

    }
}