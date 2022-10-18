package com.example.webtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.button)
        btn.setOnClickListener {
//            sendRequestWithHttpURLConnection()
            sendRequestWithOkHttp()
        }
    }

    private fun sendRequestWithHttpURLConnection() {
        thread {
            var connection: HttpURLConnection? = null
            try {
                val response = StringBuilder()
                val url = URL("https://www.baidu.com")
                connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 8000
                connection.readTimeout = 8000
                val input = connection.inputStream
                val reader = BufferedReader(InputStreamReader(input))
                reader.use {
                    reader.forEachLine {
                        response.append(it)
                    }
                }
                showResponse(response.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                connection?.disconnect()
            }
        }
    }

    private fun sendRequestWithOkHttp() {
        thread {
            try {
                val client = OkHttpClient()
                val request =
                    Request.Builder().url("https://www.freeapis.cn/api/v1/get/aiqing").build()
                val response = client.newCall(request).execute()
                val responseData = response.body.string()
                showResponse(responseData)
                parseJsonWithJSONObject(responseData)
                parseJsonWithGson(responseData)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun parseJsonWithJSONObject(jsonData: String) {
        val jsonObject = JSONObject(jsonData)
        val id = jsonObject.getString("code")
        val text = jsonObject.getString("text")
        Log.d("MyJson", "id is $id")
        Log.d("MyJson", "text is $text")
    }

    class Mycontent(val id: String, val text: String)

    private fun parseJsonWithGson(jsonData: String) {
        val gson = Gson()
        val content = gson.fromJson(jsonData, Mycontent::class.java)
        Log.d("MyJson1", "id is ${content.id}")
        Log.d("MyJson1", "text is ${content.text}")
    }

    private fun showResponse(res: String) {
        runOnUiThread {
            val resView = findViewById<TextView>(R.id.resText)
            resView.text = res
        }
    }
}