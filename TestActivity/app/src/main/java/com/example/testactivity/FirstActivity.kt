package com.example.testactivity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.example.testactivity.databinding.MyfirstLayoutBinding

@SuppressLint("StaticFieldLeak")
private lateinit var binding: MyfirstLayoutBinding

class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MyfirstLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button1.setOnClickListener {
            Toast.makeText(this, "You clicked button 2", Toast.LENGTH_SHORT).show()
        }

        binding.button2.setOnClickListener {
            finish()
        }

        binding.button3.setOnClickListener{
            val indent = Intent(this, SecondActivity::class.java)
            indent.putExtra("some_data", 12345689)
            startActivityForResult(indent, 1)
        }

        binding.button4.setOnClickListener {
            val indent = Intent(Intent.ACTION_DIAL)
            indent.data = Uri.parse("tel:10040")
            startActivity(indent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_item -> Toast.makeText(this, "click add item", Toast.LENGTH_SHORT).show()
            R.id.rm_item -> Toast.makeText(this, "click rm item", Toast.LENGTH_SHORT).show()
        }
        return true
    }

//    TODO: https://segmentfault.com/a/1190000037601888
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            1 -> if(resultCode == RESULT_OK) {
                val returnData = data?.getStringExtra("back_data")
                Log.d("first_activity", "this is return: $returnData")
            }
        }
    }
}