package com.example.myfragment

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
    }
}

class News(val title: String, val content: String)


class NewsContentFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_content_frag, container, false)
    }

    fun refresh(title: String, content: String) {
        val contentLayout = view?.findViewById<View>(R.id.contentLayout)
        val newsTitle = view?.findViewById<TextView>(R.id.newsTitle)
        val newsContent = view?.findViewById<TextView>(R.id.newsContent)
        contentLayout?.visibility = View.VISIBLE
        newsTitle?.text = title
        newsContent?.text = content
    }
}

class NewsTitleFragment : Fragment() {
    private var isTwoPanel = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_title_frag, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().lifecycle.addObserver(object: DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                isTwoPanel = activity?.findViewById<View>(R.id.newsContentLayout) != null
                Log.d("mytest", "onAttach:"+isTwoPanel.toString())
                owner.lifecycle.removeObserver(this)
            }
        })
    }

    private fun getNews(): List<News> {
        val newsList = ArrayList<News>()
        for (i in 1..50) {
            val new = News("This is news title $i", "This is news content $i")
            newsList.add(new)
        }
        return newsList
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(activity)
        val newsTitleRecyclerView = view.findViewById<RecyclerView>(R.id.newsTitleRecycle)
        val adapter = NewsAdapter(getNews())
        newsTitleRecyclerView.layoutManager = layoutManager
        newsTitleRecyclerView.adapter = adapter
    }

    inner class NewsAdapter(val newsList: List<News>) :
        RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val newsTitle: TextView = view.findViewById(R.id.newsTitleItem)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
            val holder = ViewHolder(view)
            holder.itemView.setOnClickListener {
                val news = newsList[holder.adapterPosition]
                if (isTwoPanel) {
                    val fragment =
                        activity?.supportFragmentManager?.findFragmentById(R.id.newsContentFrag) as NewsContentFragment
                    fragment.refresh(news.title, news.content)
                } else {
                    NewsContentActivity.actionStart(parent.context, news.title, news.content)
                }
            }

            return holder
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val news = newsList[position]
            holder.newsTitle.text = news.title
        }

        override fun getItemCount(): Int = newsList.size

    }

}