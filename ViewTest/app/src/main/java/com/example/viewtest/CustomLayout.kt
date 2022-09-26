package com.example.viewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CustomLayout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_layout)
        supportActionBar?.hide()
    }
}