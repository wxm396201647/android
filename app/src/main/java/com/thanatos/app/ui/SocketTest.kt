package com.thanatos.app.ui

import android.app.Activity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import com.blankj.utilcode.util.TimeUtils
import com.thanatos.app.R
import kotlinx.android.synthetic.main.activity_sec.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket
import java.net.SocketTimeoutException
import java.nio.charset.Charset


class SocketTest : Activity() {
    var mSocket: Socket? = null
    var mOutStream: OutputStream? = null
    var mInStream: InputStream? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sec)

        connect.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    //指定ip地址和端口号
                    mSocket = Socket(ip.text.toString(), dk.text.toString().toInt())
                    mSocket?.soTimeout = 10 * 60 * 1000
                    if (mSocket != null) {
                        //获取输出流、输入流
                        mOutStream = mSocket?.getOutputStream()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    showTxt("连接失败：$e")
                    return@launch
                }
                showTxt("连接成功")
            }
        }

        write.setOnClickListener { writeMsg("${write_et.text}\n") }

        read.setOnClickListener { readInfo() }
    }

    private fun writeMsg(msg: String) {
        GlobalScope.launch(Dispatchers.IO) {
            if (msg.isEmpty() || mOutStream == null) return@launch
            try {   //发送
                mOutStream?.write(msg.toByteArray())
                mOutStream?.flush()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun readInfo() {
        GlobalScope.launch(Dispatchers.IO) {
            //读取客户端数据
            try {
                mInStream = mSocket?.getInputStream()
                while (true) {
                    mInStream?.let {
                        val output = ByteArrayOutputStream()
                        readStreamWithRecursion(output, it);
                        output.close()
                        val size = output.size()
                        Log.d("thanatos", "本次读取字节总数：$size")
                        val message = String(output.toByteArray(), Charset.forName("UTF-8"))
                        Log.d("thanatos", "message：$message")
                        showTxt(message)
                    }
                }
            } catch (e: Exception) {
                writeMsg("exception:$e")
            }
        }
    }

    fun readStreamWithRecursion(output: ByteArrayOutputStream, inStream: InputStream) {
        val start = System.currentTimeMillis()
        while (inStream.available() == 0) {
            if ((System.currentTimeMillis() - start) > 20 * 1000) {//超时退出
                throw SocketTimeoutException("超时读取");
            }
        }
        val buffer = ByteArray(2048)
        val read = inStream.read(buffer)
        output.write(buffer, 0, read)
        SystemClock.sleep(100)//需要延时以下，不然还是有概率漏读
        val a = inStream.available()//再判断一下，是否有可用字节数或者根据实际情况验证报文完整性
        if (a > 0) {
            Log.w("thanatos", "========还有剩余：" + a + "个字节数据没读");
            readStreamWithRecursion(output, inStream);
        }
    }

    private fun showTxt(text: String) {
        GlobalScope.launch(Dispatchers.Main) {
            info.text = "${TimeUtils.millis2String(TimeUtils.getNowDate().time)} => $text \n ========================================================================\n ${info.text}"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            if (mOutStream != null) {
                mOutStream!!.close() //关闭输出流
                mOutStream = null
            }
            if (mInStream != null) {
                mInStream!!.close() //关闭输入流
                mInStream = null
            }
            if (mSocket != null) {
                mSocket!!.close() //关闭socket
                mSocket = null
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
