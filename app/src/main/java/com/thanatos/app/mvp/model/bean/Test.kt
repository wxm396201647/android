package com.thanatos.app.mvp.model.bean

data class Test(
    var error: Boolean = false,
    var results: List<Result> = listOf()
)