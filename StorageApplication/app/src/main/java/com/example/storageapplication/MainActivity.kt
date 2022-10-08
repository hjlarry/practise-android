package com.example.storageapplication

import android.content.ContentValues
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.storageapplication.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.savebtn.setOnClickListener {
            val text = binding.editText.text.toString()
            save(text)
        }

        binding.loadbtn.setOnClickListener {
            val inputText = load()
            if (inputText.isNotEmpty()) {
                binding.editText.setText(inputText)
                binding.editText.setSelection(inputText.length)
                Toast.makeText(this, "load success", Toast.LENGTH_SHORT).show()
            }
        }

        binding.savebtn2.setOnClickListener {
            val editor = getSharedPreferences("cdata", Context.MODE_PRIVATE).edit()
            editor.putString("hello", "world")
            editor.putBoolean("abc", true)
            editor.putInt("age", 28)
            editor.apply()
        }

        binding.loadbtn2.setOnClickListener {
            val prefs = getSharedPreferences("cdata", Context.MODE_PRIVATE)
            val age = prefs.getInt("age", 0)
            Toast.makeText(this, "age is $age", Toast.LENGTH_SHORT).show()
        }

        val dbHelper = MyDatabaseHelper(this, "mybook.db", 21)
        binding.createdb.setOnClickListener {
            dbHelper.writableDatabase
        }

        val db = dbHelper.writableDatabase
        binding.insert.setOnClickListener {
            val values1 = ContentValues().apply {
                put("name", "abc")
                put("pages", 452)
            }
            db.insert("Book", null, values1)
            val values2 = ContentValues().apply {
                put("name", "def")
                put("pages", 453)
            }
            db.insert("Book", null, values2)
            Toast.makeText(this, "Insert success", Toast.LENGTH_SHORT).show()
        }

        binding.update.setOnClickListener {
            val values = ContentValues()
            values.put("price", 1.23)
            db.update("Book", values, "name=? and pages=?", arrayOf("def", "453"))
            Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show()
        }
    }


    private fun save(text: String) {
        try {
            val output = openFileOutput("mydata", Context.MODE_PRIVATE)
            val writer = BufferedWriter(OutputStreamWriter(output))
            writer.use {
                it.write(text)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun load(): String {
        val content = StringBuilder()
        try {
            val input = openFileInput("mydata")
            val reader = BufferedReader(InputStreamReader(input))
            reader.use {
                reader.forEachLine {
                    content.append(it)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return content.toString()
    }
}