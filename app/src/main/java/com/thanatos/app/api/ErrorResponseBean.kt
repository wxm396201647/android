package com.thanatos.app.api

import com.google.gson.annotations.SerializedName

data class ErrorResponseBean(@SerializedName("code") var code: Int = 0,
                             @SerializedName("message") var message: String? = null,
                             @SerializedName("detail") var detail: String? = null)