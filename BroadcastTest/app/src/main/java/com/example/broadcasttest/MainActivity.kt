package com.example.broadcasttest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import com.example.broadcasttest.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var timeChangeReceiver: TimeChangeReceiver
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intentFilter = IntentFilter()
        intentFilter.addAction("android.intent.action.TIME_TICK")
        timeChangeReceiver = TimeChangeReceiver()
        registerReceiver(timeChangeReceiver, intentFilter)

        binding.SendBroadcast.setOnClickListener {
            val intent = Intent("com.example.broadcasttest.MY_BROADCAST")
            intent.setPackage(packageName)
            sendOrderedBroadcast(intent, null)
        }

        binding.forceOffline.setOnClickListener {
            val intent = Intent("com.example.broadcasttest.FORCE_OFFLINE")
            sendBroadcast(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(timeChangeReceiver)
    }
}


class TimeChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Some thing", Toast.LENGTH_SHORT).show()
    }
}

class MyBroadcastReciver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "received my broadcast", Toast.LENGTH_LONG).show()
    }
}

// 有序广播可以在androidManifest中设定广播的优先级，并可以阻断后续广播的运行
class AnotherBroadcastReciver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "received in another broadcast", Toast.LENGTH_SHORT).show()
        abortBroadcast()
    }
}