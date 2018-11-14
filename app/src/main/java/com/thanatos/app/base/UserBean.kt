package com.thanatos.app.base

import java.io.Serializable

data class UserBean(val tokenid: String, val firstLogin: Boolean, val loginFlag: Boolean, val authUser: AuthUser? ,val feipai_tokenId: String) : Serializable {

    /**
     * {"tokenid": "b4f2dfcfc6854c83869cff5866f987d0","firstLogin": false,"loginFlag": true,"authUser": {
    "id": "HYS0000199078","loginName": "15200851782","password": "c2fd04b29dbd15bc5dede23c3d4dc8d7e01d7c9b0975ba505030c71a","trueName": null,"mobile": "15200851782","email": null,"userType": "1","source": "sams","state": "1","loginIp": null,"loginTime": 1526968059902,"loginTimeString": null,"isOnline": "1","deleteFlag": "0","creator": null,"createTime": "2018-04-11 14:50:31.0","roleList": null,"permissionList": null,"gender": "0","headPath": null
    }
    }
     */

    data class AuthUser(val id: String, val loginName: String, val password: String, val trueName: String?, val mobile: String
                        , val email: String?, val userType: String, val source: String, val state: String, val loginIp: String?
                        , val loginTime: Long, val loginTimeString: String?, val isOnline: String, val deleteFlag: String
                        , val creator: String?, val createTime: String, val roleList: String?, val permissionList: String?
                        , val gender: String, var headPath: String?) : Serializable
}