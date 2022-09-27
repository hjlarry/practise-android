package com.example.viewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.viewtest.databinding.ActivityChatBinding
import com.example.viewtest.databinding.MsgLeftItemBinding
import com.example.viewtest.databinding.MsgRightItemBinding

class Chat : AppCompatActivity(), View.OnClickListener {
    private val msgList = ArrayList<Msg>()
    private lateinit var adapter: MsgAdapter
    private lateinit var binding: ActivityChatBinding

    private fun initMsg() {
        msgList.add(Msg("Hello world!", Msg.TYPE_RECEIVED))
        msgList.add(Msg("who are you?", Msg.TYPE_SENT))
        msgList.add(Msg("I am ok", Msg.TYPE_RECEIVED))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initMsg()

        val layoutManager = LinearLayoutManager(this)
        adapter = MsgAdapter(msgList, layoutInflater)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        binding.send.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.send -> {
                val content = binding.inputText.text.toString()
                if (content.isNotEmpty()) {
                    val msg = Msg(content, Msg.TYPE_SENT)
                    msgList.add(msg)
//                   通知适配器有新数据插入
                    adapter.notifyItemInserted(msgList.size - 1)
//                    显示的数据定位到最后一行
                    binding.recyclerView.scrollToPosition(msgList.size - 1)
                    binding.inputText.setText("")
                }
            }
        }
    }
}

class Msg(val content: String, val type: Int) {
    companion object {
        const val TYPE_RECEIVED = 0
        const val TYPE_SENT = 1
    }
}


class MsgAdapter(val msgList: List<Msg>, val inflater: LayoutInflater) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class LeftViewHolder(val binding: MsgLeftItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    inner class RightViewHolder(val binding: MsgRightItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun getItemViewType(position: Int): Int {
        val msg = msgList[position]
        return msg.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == Msg.TYPE_RECEIVED) {
            val view = MsgLeftItemBinding.inflate(inflater, parent, false)
            return LeftViewHolder(view)
        } else {
            val view = MsgRightItemBinding.inflate(inflater, parent, false)
            return RightViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = msgList[position]
        when (holder) {
            is LeftViewHolder -> holder.binding.leftMsg.text = msg.content
            is RightViewHolder -> holder.binding.rightMsg.text = msg.content
        }
    }

    override fun getItemCount(): Int = msgList.size

}