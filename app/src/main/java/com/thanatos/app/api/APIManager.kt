package com.thanatos.app.api

import com.blankj.utilcode.util.LogUtils
import okhttp3.Headers.Companion.toHeaders
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.EOFException
import java.nio.charset.Charset
import java.util.HashMap
import java.util.concurrent.TimeUnit

object APIManager {
    val api: Api by lazy { getRetofit()!!.create(Api::class.java) }
    private var retrofit: Retrofit? = null
    private val okHttpClient by lazy { initOkHttp() }

    private fun getRetofit(): Retrofit? {
        if (null == retrofit) {
            synchronized(APIManager::class.java) {
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                            .baseUrl(APIAddressConstants.baseUrl)
                            .client(okHttpClient)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                }
            }
        }
        return retrofit
    }

    private fun initOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(20000L, TimeUnit.MILLISECONDS)//超时
                .readTimeout(20000L, TimeUnit.MILLISECONDS)
                .addInterceptor(getCommonHeaderInterceptor())//配置通用header
                .addInterceptor(getLogInterceptor())//打印http整个请求的Log
                .build()
    }

    /**
     * 打印retrofit日志
     */
    private fun getLogInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                LogUtils.e("RetrofitLog", "retrofitBack = $message")
            }
        })
        return httpLoggingInterceptor.apply { httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY }
    }

    /**
     * 增加通用的请求头
     * 签名元素：gid  timestamp  tokenId（有则放没有不放），请求参数，requestPathUrl，requestBody（post、put请求有）
     *
     *
     */
    private fun getCommonHeaderInterceptor(): Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val url = chain.request().url.encodedPath

                var bodyString: String? = null
                //增加拦截器，用以加入公用头部
                val builder = chain.request().newBuilder()
                bodyString = getBodyString(chain, bodyString)

                val commonHeaders = CommonHeaders.getCommonHeaders()

                val signMap = HashMap<String, String>()

                chain.request().url.queryParameterNames.forEach {
                    val str = chain.request().url.queryParameter(it);
                    signMap[it] = if (str.isNullOrEmpty()) "" else str
                }
                if (!bodyString.isNullOrEmpty()) {
                    signMap["requestBody"] = bodyString
                }
                signMap["timestamp"] = System.currentTimeMillis().toString() + ""//当前时间

                if (!url.isEmpty()) {
                    signMap["requestPathUrl"] = url
                }
//            val userInfo = BaseContext.instance. getUserInfo()
//            if (userInfo?.tokenid != null) {
//                signMap["tokenId"] = userInfo.tokenid
//            }
//            if(null != BaseContext.instance.secretKey){
//                signMap["gid"] =  BaseContext.instance.secretKey?.gid!!//加密串
//            }
                signMap["appid"] = APIAddressConstants.APP_KEY

                try {
                    chain.request().url.queryParameterNames.forEach {
                        signMap.remove(it)
                    }

                    signMap.remove("requestPathUrl")
                } catch (e: Exception) {
                    LogUtils.e(e.message)
                }

                signMap.putAll(commonHeaders)

                builder.headers(signMap.toHeaders())
                val request = builder.build()
                return chain.proceed(request)
            }
        }
    }

    private fun getBodyString(chain: Interceptor.Chain, bodyString: String?): String? {
        var bodyString1 = bodyString
        chain.request().body?.let {
            val buffer = Buffer()
            it.writeTo(buffer)

            var charset = Charset.forName("UTF-8")
            val contentType = it.contentType()
            contentType?.let { it1 ->
                charset = it1.charset(Charset.forName("UTF-8"))
            }
            if (isPlaintext(buffer)) {
                bodyString1 = buffer.readString(charset)
            }
        }
        return bodyString1
    }


    internal fun isPlaintext(buffer: Buffer): Boolean {
        try {
            val prefix = Buffer()
            val byteCount = if (buffer.size < 64) buffer.size else 64
            buffer.copyTo(prefix, 0, byteCount)
            for (i in 0..15) {
                if (prefix.exhausted()) {
                    break
                }
                val codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false
                }
            }
            return true
        } catch (e: EOFException) {
            return false // Truncated UTF-8 sequence.
        }

    }
}