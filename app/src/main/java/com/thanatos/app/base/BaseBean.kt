package com.thanatos.app.base

import java.io.Serializable

/**
 * 数据格式约定
 * **/
data class BaseBean<T>(var code: Int, var message: String, var detail: String,var timestamp:Long, var data: T) : Serializable