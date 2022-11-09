package com.example.viewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.viewtest.databinding.ActivityRecyclerView2Binding
import com.example.viewtest.databinding.View2RowBinding
import kotlin.random.Random

class RecyclerView2 : AppCompatActivity() {
    private val random = Random(0)

    private fun buildItems() = List(25) { random.nextInt() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecyclerView2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@RecyclerView2)

            addItemDecoration(
                DividerItemDecoration(
                    this@RecyclerView2, DividerItemDecoration.VERTICAL
                )
            )

            adapter = ColorAdapater(layoutInflater).apply {
                submitList(buildItems())
            }
        }
    }
}


class ColorViewHolder(private val binding: View2RowBinding) : ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener {
            Toast.makeText(binding.label.context, binding.label.text, Toast.LENGTH_SHORT).show()
        }
    }

    fun bindTo(color: Int) {
        binding.label.text = binding.label.context.getString(R.string.label_template, color)
        binding.color.setBackgroundColor(color)
    }
}


class ColorAdapater(private val layoutInflater: LayoutInflater) :
    ListAdapter<Int, ColorViewHolder>(ColorDiffer) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        return ColorViewHolder(View2RowBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    private object ColorDiffer : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }

    }
}