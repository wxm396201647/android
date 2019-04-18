package com.thanatos.imageloader.okhttp

import com.bumptech.glide.load.engine.GlideException
import okhttp3.OkHttpClient
import java.lang.ref.WeakReference
import java.util.*

/**
 * Created by wangming on 2018/11/15 .
 * Describe:
 */
class ProgressManager private constructor() {

    companion object {
        private lateinit var okHttpClient: OkHttpClient
        private val listeners = Collections.synchronizedList(ArrayList<WeakReference<OnProgressListener>>())

        fun getOkHttpClient(): OkHttpClient {
            return okHttpClient
        }

        private val LISTENER = object : OnProgressListener {
            override fun onProgress(imageUrl: String, bytesRead: Long, totalBytes: Long, isDone: Boolean, exception: GlideException?) {
                if (listeners == null || listeners.size == 0) return

                for (i in listeners.indices) {
                    val listener = listeners[i]
                    val progressListener = listener.get()
                    progressListener?.onProgress(imageUrl, bytesRead, totalBytes, isDone, exception)
                            ?: listeners.removeAt(i)
                }
            }
        }

        fun addProgressListener(progressListener: OnProgressListener?) {
            if (progressListener == null) return

            if (findProgressListener(progressListener) == null) {
                listeners.add(WeakReference(progressListener))
            }
        }

        fun removeProgressListener(progressListener: OnProgressListener?) {
            if (progressListener == null) return

            val listener = findProgressListener(progressListener)
            if (listener != null) {
                listeners.remove(listener)
            }
        }

        private fun findProgressListener(listener: OnProgressListener?): WeakReference<OnProgressListener>? {
            if (listener == null) return null
            if (listeners == null || listeners.size == 0) return null

            for (i in listeners.indices) {
                val progressListener = listeners[i]
                if (progressListener.get() === listener) {
                    return progressListener
                }
            }
            return null
        }
    }
}