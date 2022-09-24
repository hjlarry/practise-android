package com.example.activitylifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DialogAcitvity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_layout)
    }
}