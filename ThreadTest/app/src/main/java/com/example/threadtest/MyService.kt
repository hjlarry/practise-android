package com.example.threadtest

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MyService : Service() {
    val tag = "My Service"
    private val mBinder = VisualDownloadBinder()

    inner class VisualDownloadBinder : Binder() {
        fun startDownload() {
            Log.d(tag, "start download()")
        }

        fun getProgress(): Int {
            Log.d(tag, "get progress()")
            return 0
        }
    }

    override fun onCreate() {
        Log.d(tag, "onCreate")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(tag, "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.d(tag, "onDestroy")
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }
}