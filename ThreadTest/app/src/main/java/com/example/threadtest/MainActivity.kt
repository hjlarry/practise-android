package com.example.threadtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Button
import android.widget.TextView
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    val update_text = 1

    val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                update_text -> {
                    val textView = findViewById<TextView>(R.id.text)
                    textView.text = "Nice to meet you!"
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.button)
        btn.setOnClickListener {
            thread {
                val msg = Message()
                msg.what = update_text
                handler.sendMessage(msg)
            }
        }

        val startBtn = findViewById<Button>(R.id.startBtn)
        startBtn.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            startService(intent)
        }

        val stopBtn = findViewById<Button>(R.id.stopBtn)
        stopBtn.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            stopService(intent)
        }
    }
}