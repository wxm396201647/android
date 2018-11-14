package com.thanatos.app.base

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.support.multidex.MultiDex
import com.scwang.smartrefresh.layout.SmartRefreshLayout.*
import com.thanatos.app.R

import com.thanatos.app.view.CommonRefreshHeader
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import org.greenrobot.eventbus.EventBus
import kotlin.properties.Delegates


class BaseContext : Application() {
    init {
        //设置全局的Header构建器
        setDefaultRefreshHeaderCreator(DefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColors(resources.getColor(android.R.color.white), resources.getColor(R.color.color_cccccc))
            return@DefaultRefreshHeaderCreator CommonRefreshHeader(context)
        })
        //设置全局的Footer构建器
        setDefaultRefreshFooterCreater { context, _ -> ClassicsFooter(context).setDrawableSize(20f) }
    }

     private var userInfo: UserBean? = null

    /**
     * BaseContext instance
     */
    companion object {
        var instance: BaseContext by Delegates.notNull()
    }

    /**
     * dex 分包
     */
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        //编译时注解 use the index
//        EventBus.builder().addIndex(MyEventBusIndex()).installDefaultEventBus()

    }

    /**
     * 是否是debug包
     */
    private fun isApkDebugable(context: Context): Boolean {
        try {
            val info = context.applicationInfo
            return info.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        } catch (e: Exception) {

        }
        return false
    }
}