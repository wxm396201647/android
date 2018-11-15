package com.thanatos.imageloader.listener

/**
 * Created by wangming on 2018/11/15 .
 * Describe:
 */
class ImageSize(val width: Int, val height: Int) {

    override fun toString(): String {
        return StringBuilder().append(width).append(SEPARATOR).append(height).toString()
    }

    companion object {
        private val SEPARATOR = "x"
    }
}
