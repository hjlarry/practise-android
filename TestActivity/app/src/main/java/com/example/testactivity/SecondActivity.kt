package com.example.testactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.testactivity.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extraData = intent.getIntExtra("some_data", 9527)
        Log.d("extraData", "extra data is $extraData")

        binding.button.setOnClickListener {
            val intent = Intent()
            intent.putExtra("back_data", "some another data")
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}