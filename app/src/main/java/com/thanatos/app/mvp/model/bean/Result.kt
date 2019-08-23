package com.thanatos.app.mvp.model.bean

data class Result(
    var _id: String = "",
    var createdAt: String = "",
    var desc: String = "",
    var images: List<String> = listOf(),
    var publishedAt: String = "",
    var source: String = "",
    var type: String = "",
    var url: String = "",
    var used: Boolean = false,
    var who: String = ""
)