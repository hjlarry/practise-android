package com.example.jetpacktest

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpacktest.databinding.ActivityMainBinding


class MyModel(countSaved: Int) : ViewModel() {
    var count = countSaved
}

class MyViewModelFactory(private val countSaved: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyModel(countSaved) as T
    }
}

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MyModel
    lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sp = getPreferences(Context.MODE_PRIVATE)
        val countSaved = sp.getInt("countSaved", 0)

//       ViewModel有独立的生命周期，且长于activity。
        viewModel = ViewModelProvider(this, MyViewModelFactory(countSaved)).get(MyModel::class.java)

        binding.button.setOnClickListener {
            viewModel.count++
            refreshCount()
        }

        refreshCount()
    }

    override fun onPause() {
        super.onPause()
        sp.edit {
            putInt("countSaved", viewModel.count)
        }
    }

    private fun refreshCount() {
        binding.textInfo.text = viewModel.count.toString()
    }
}