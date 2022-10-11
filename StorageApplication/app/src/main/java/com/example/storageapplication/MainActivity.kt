package com.example.storageapplication

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.storageapplication.databinding.ActivityMainBinding
import java.io.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rwlocal()

        rwSharedPreferences()

        addData()

        queryDB()
    }

    private fun rwSharedPreferences() {
        //        binding.savebtn2.setOnClickListener {
//            val editor = getSharedPreferences("cdata", Context.MODE_PRIVATE).edit()
//            editor.putString("hello", "world")
//            editor.putBoolean("abc", true)
//            editor.putInt("age", 28)
//            editor.apply()
//        }
//        高阶函数写法:
        fun SharedPreferences.open(block: SharedPreferences.Editor.() -> Unit) {
            val editor = edit()
            editor.block()
            editor.apply()
        }
        binding.savebtn2.setOnClickListener {
            getSharedPreferences("cdata", Context.MODE_PRIVATE).open {
                putString("hello", "world")
                putBoolean("abc", true)
                putInt("age", 290)
            }
        }

        binding.loadbtn2.setOnClickListener {
            val prefs = getSharedPreferences("cdata", Context.MODE_PRIVATE)
            val age = prefs.getInt("age", 0)
            Toast.makeText(this, "age is $age", Toast.LENGTH_SHORT).show()
        }
    }

    private fun rwlocal() {
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

    private fun addData() {
        val dbHelper = MyDatabaseHelper(this, "mybook.db", 21)
        binding.createdb.setOnClickListener {
            dbHelper.writableDatabase
        }

        val db = dbHelper.writableDatabase
//        binding.insert.setOnClickListener {
//            val values1 = ContentValues().apply {
//                put("name", "abc")
//                put("pages", 452)
//            }
//            db.insert("Book", null, values1)
//            val values2 = ContentValues().apply {
//                put("name", "def")
//                put("pages", 453)
//            }
//            db.insert("Book", null, values2)
//            Toast.makeText(this, "Insert success", Toast.LENGTH_SHORT).show()
//        }

        //        高级函数，其实也是KTX库中 contentValuesOf()的实现方式
        fun cvOf(vararg pairs: Pair<String, Any?>) = ContentValues().apply {
            for (pair in pairs) {
                val key = pair.first
                val value = pair.second
                when (value) {
                    is Int -> put(key, value)
                    is Boolean -> put(key, value)
                    is String -> put(key, value)
                }
            }
        }

        binding.insert.setOnClickListener {
            val values1 = cvOf("name" to "abc", "pages" to 452)
            db.insert("Book", null, values1)
            val values2 = cvOf("name" to "def", "pages" to 453)
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

    @SuppressLint("Range")
    private fun queryDB() {
        val dbHelper = MyDatabaseHelper(this, "mybook.db", 21)
        val db = dbHelper.writableDatabase
        binding.queryAll.setOnClickListener {
            val cursor = db.query("Book", null, null, null, null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val pages = cursor.getString(cursor.getColumnIndex("pages"))
                    val price = cursor.getString(cursor.getColumnIndex("price"))
                    Log.d("MainQuery", "book name is $name")
                    Log.d("MainQuery", "book pages is $pages")
                    Log.d("MainQuery", "book price is $price")
                } while (cursor.moveToNext())
            }
            cursor.close()
        }

        binding.queryOne.setOnClickListener {
            val cursor = db.rawQuery("select * from Book where id=1", null)
            while (cursor.moveToNext()) {
                val name = cursor.getString(cursor.getColumnIndex("name"))
                Toast.makeText(this, "book name is $name", Toast.LENGTH_SHORT).show()
            }
            cursor.close()
        }
    }


}