package com.example.viewtest

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast


class TitleLayout(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    init {
        LayoutInflater.from(context).inflate(R.layout.title, this)
        val titleBack = findViewById<Button>(R.id.titleBack)
        titleBack.setOnClickListener{
            val activity = context as Activity
            activity.finish()
        }
        val titleEdit = findViewById<Button>(R.id.titleEdit)
        titleEdit.setOnClickListener {
            Toast.makeText(context, "Hello world", Toast.LENGTH_SHORT).show()
        }
    }
}