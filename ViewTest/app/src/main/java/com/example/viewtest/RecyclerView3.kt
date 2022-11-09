package com.example.viewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.*
import com.example.viewtest.databinding.ActivityRecyclerView3Binding
import com.example.viewtest.databinding.View3RowBinding
import java.util.Random

data class Event(
    val message: String,
    val activityHash: Int,
    val viewModelHash: Int,
    val timeStamp: Long = SystemClock.elapsedRealtime()
)

class EventViewModel : ViewModel() {
    val events: MutableList<Event> = mutableListOf()
    val startTime = SystemClock.elapsedRealtime()
    private val myHash = Random().nextInt()

    fun addEvent(message: String, activityHash: Int) {
        events.add(Event(message, activityHash, myHash))
    }

    override fun onCleared() {
        events.clear()
    }
}

class RecyclerView3 : AppCompatActivity() {
    private val vm: EventViewModel by viewModels()
    private val myHash = Random().nextInt()

    private lateinit var adapater: EventAdapater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecyclerView3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        adapater = EventAdapater(layoutInflater, vm.startTime)
        addEvent("onCreate()")
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL
            )
        )
        binding.recyclerView.adapter = adapater
    }

    override fun onStart() {
        super.onStart()
        addEvent("onStart()")
    }

    override fun onRestart() {
        super.onRestart()
        addEvent("onRestart()")
    }

    override fun onResume() {
        super.onResume()
        addEvent("onResume()")
    }

    override fun onPause() {
        super.onPause()
        addEvent("onPause()")
    }

    override fun onStop() {
        super.onStop()
        addEvent("onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        addEvent("onDestroy()")
    }

    private fun addEvent(message: String){
        vm.addEvent(message, myHash)
        adapater.submitList(ArrayList(vm.events))
    }
}

class EventViewHolder(private val binding: View3RowBinding, private val startTime: Long) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindTo(event: Event) {
        val elapsedSeconds = (event.timeStamp - startTime) / 1000
        binding.time.text = DateUtils.formatElapsedTime(elapsedSeconds)
        binding.message.text = event.message
        binding.viewModelHash.text = Integer.toHexString(event.viewModelHash)
        binding.activityHash.text = Integer.toHexString(event.activityHash)
    }
}

class EventAdapater(private val layoutInflater: LayoutInflater, private val startTime: Long) :
    ListAdapter<Event, EventViewHolder>(EventDiffer) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(View3RowBinding.inflate(layoutInflater, parent, false), startTime)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    private object EventDiffer : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }

    }
}