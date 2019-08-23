package com.thanatos.imageloader.okhttp

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*
import java.io.IOException

class ProgressResponseBody(val imageUrl: String, val responseBody: ResponseBody, val progressListener: OnProgressListener?) : ResponseBody() {
    private lateinit var bufferedSource: BufferedSource

    override fun contentType(): MediaType? {
        return responseBody.contentType()
    }

    override fun contentLength(): Long {
        return responseBody.contentLength()
    }

    override fun source(): BufferedSource {
        return bufferedSource
    }

    private fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            internal var totalBytesRead: Long = 0

            @Throws(IOException::class)
            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)
                totalBytesRead += if (bytesRead.toInt() == -1) 0 else bytesRead

                progressListener?.onProgress(imageUrl, totalBytesRead, contentLength(), bytesRead.toInt() == -1, null)
                return bytesRead
            }
        }
    }
}