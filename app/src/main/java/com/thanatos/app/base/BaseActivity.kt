package com.thanatos.app.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.KeyboardUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

abstract class BaseActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        initViewsAndEvents()
    }

    /**
     * 获取资源id 子类返回
     */
    abstract fun getLayoutId(): Int

    /**
     *初始化View与事件
     */
    abstract fun initViewsAndEvents()


    /**
     * 释放资源 与 取消掉 页面Presenter 相关请求
     */
    override fun onDestroy() {
        super.onDestroy()
        cancel()
        // 收起输入框
        KeyboardUtils.hideSoftInput(this)
    }

}