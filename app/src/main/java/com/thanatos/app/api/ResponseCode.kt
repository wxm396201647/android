package com.thanatos.app.api

object ResponseCode {

    /**
     * 业务正确
     */
    val SUCCESS = 200
    /**
     * 业务错误
     */
    val BAD_REQUEST = 400
    val INTERINAL_SERVER_ERROR = 500
    /**
     * 签名校验错误
     */
    val SIGN_ERROR = 406

    val UPDATE_FORCE = 201
    val ACCESS_TOKEN_INVALID = 202
    val TICKET_UNAVALIBLE = 400000023//ticket无效的请求参数
    val TICKET_UNAVALIBLE2 = 400000024//ticket无效的请求参数
    val TICKET_UNAVALIBLE3 = 400000002//ticket不存在或者已经失效
    val TOKEN_EMPTY = 400001008//TOKEN_EMPTY
    val LOGIN_DUPLICATE = 400001045//用户在其他设备，您已经被踢掉

    val REQUEST_SUCCESS = 0;
}