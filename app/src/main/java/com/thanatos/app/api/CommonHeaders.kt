package com.thanatos.app.api

import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.ScreenUtils
import java.util.*

object CommonHeaders {


    /**
     * 通用的http请求头 header
     */
    private var sCommonHeaders: HashMap<String, String>? = null

    init {
        sCommonHeaders = HashMap()
        sCommonHeaders!!["deviceid"] = DeviceUtils.getAndroidID()
        sCommonHeaders!!["devicebrand"] = DeviceUtils.getModel() //设备型号
        sCommonHeaders!!["systembrand"] = DeviceUtils.getSDKVersionName() //操作系统版本
//        sCommonHeaders!!["version"] = SysUtils.getVersionName(BaseContext.instance)//应用版本
        sCommonHeaders!!["APPkey"] = APIAddressConstants.APP_KEY //应用名称
        sCommonHeaders!!["lon"] = "0"//该版本默认0
        sCommonHeaders!!["lat"] = "0" //该版本默认0
        sCommonHeaders!!["deviceresolution"] = ScreenUtils.getScreenWidth().toString() + "x" + ScreenUtils.getScreenHeight()//分辨率
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                }

    /**
     * 设置通用的http请求的header
     */
    fun getCommonHeaders(): HashMap<String, String> {
        val signMap = HashMap<String, String>()
        signMap.putAll(sCommonHeaders!!)
        return signMap
    }


}