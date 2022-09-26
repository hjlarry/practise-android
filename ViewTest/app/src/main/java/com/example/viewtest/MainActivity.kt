package com.example.viewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        val img = findViewById<ImageView>(R.id.imageView)
        button.setOnClickListener(this)
        img.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button -> {
//                val text = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
//                Toast.makeText(this, text, Toast.LENGTH_LONG).show()
//                val progressBar = findViewById<ProgressBar>(R.id.progressBar)
//                progressBar.progress = progressBar.progress + 10
                val dialog = AlertDialog.Builder(this).run {
                    setTitle("This is a Dialog")
                    setMessage("Some important thing")
                    setCancelable(false)
                    setPositiveButton("OK") { dialog, which -> }
                    setNegativeButton("cancel"){dialog, which ->}
                    create()
                }
                dialog.show()
            }
            R.id.imageView -> {
                findViewById<ImageView>(R.id.imageView).apply { setImageResource(R.drawable.img_2) }
            }
        }
    }
}