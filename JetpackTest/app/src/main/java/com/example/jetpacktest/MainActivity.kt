package com.example.jetpacktest

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.*
import com.example.jetpacktest.databinding.ActivityMainBinding


class MyModel(countSaved: Int) : ViewModel() {
    val count: LiveData<Int>
        get() = _count

//   使_count只能通过内部来修改，外部无法给_count设置数据，保证ViewModel的数据封装性
    private val _count = MutableLiveData<Int>()

    init {
        _count.value = countSaved
    }

    fun plusOne() {
        val current = _count.value ?: 0
        _count.value = current + 1
    }

    fun clear() {
        _count.value = 0
    }
}

class MyViewModelFactory(private val countSaved: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyModel(countSaved) as T
    }
}

class MyObserver : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        Log.d("Myobserver", "activity create")
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Log.d("Myobserver", "activity start")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Log.d("Myobserver", "activity stop")
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
        lifecycle.addObserver(MyObserver())

        sp = getPreferences(Context.MODE_PRIVATE)
        val countSaved = sp.getInt("countSaved", 0)

//       ViewModel有独立的生命周期，且长于activity。
        viewModel = ViewModelProvider(this, MyViewModelFactory(countSaved)).get(MyModel::class.java)

        binding.button.setOnClickListener {
            viewModel.plusOne()
//            refreshCount()
        }

//        refreshCount()
        viewModel.count.observe(this) {
            binding.textInfo.text = it.toString()
        }
    }

    override fun onPause() {
        super.onPause()
        sp.edit {
            putInt("countSaved", viewModel.count.value ?: 0)
        }
    }

    private fun refreshCount() {
        binding.textInfo.text = viewModel.count.toString()
    }
}