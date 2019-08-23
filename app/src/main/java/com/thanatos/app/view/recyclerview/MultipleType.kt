package com.thanatos.app.view.recyclerview

interface MultipleType<in T> {
    fun getLayoutId(item: T, position: Int): Int
}
