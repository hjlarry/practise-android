package com.example.testactivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.testactivity.databinding.ActivitySecondBinding

class SecondActivity : BaseActivity() {

//    别人调用启动SecondActivity所需的参数一目了然，直接通过下面的代码就能启动
//    SecondActivity.actionStart(this, "data 1", "data 2")
    companion object {
        fun actionStart(context:Context, data1:String, data2:String){
            val intent = Intent(context, SecondActivity::class.java)
            intent.putExtra("param1", data1)
            intent.putExtra("param2", data2)
            context.startActivity(intent)
        }
    }

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

        binding.closebtn.setOnClickListener {
            ActivityCollector.finishAll()
            android.os.Process.killProcess(android.os.Process.myPid())
        }
    }
}