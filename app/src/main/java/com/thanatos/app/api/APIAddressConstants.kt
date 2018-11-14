package com.thanatos.app.api


/**
 * 接口地址
 */
object APIAddressConstants {

    //cobub统计地址
    const val APP_KEY: String = "5ea885d367f6920185b19b670b804d19"
    /**
     * 协议版本号
     */
    const val VERSION: String = "v1"
    const val USER_API: String = "thanatos/$VERSION"
    /**
     * 获取基础地址，此地址是最主要的接口地址
     */
    const val baseUrl: String = "https://www.baidu.com/"

    const val userLogin: String = "$USER_API/user/login"

}
