package com.example.viewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class MyRecyclerView : AppCompatActivity() {
    private val fruitList = ArrayList<Fruit>()

    private fun initFruits() {
        repeat(20) {
            fruitList.add(Fruit(getRandomLengthString("Apple"),
                R.drawable.apple_pic))
            fruitList.add(Fruit(getRandomLengthString("Banana"),
                R.drawable.banana_pic))
            fruitList.add(Fruit(getRandomLengthString("Orange"),
                R.drawable.orange_pic))
            fruitList.add(Fruit(getRandomLengthString("Watermelon"),
                R.drawable.watermelon_pic))
            fruitList.add(Fruit(getRandomLengthString("Pear"),
                R.drawable.pear_pic))
            fruitList.add(Fruit(getRandomLengthString("Grape"),
                R.drawable.grape_pic))
            fruitList.add(Fruit(getRandomLengthString("Pineapple"),
                R.drawable.pineapple_pic))
            fruitList.add(Fruit(getRandomLengthString("Strawberry"),
                R.drawable.strawberry_pic))
            fruitList.add(Fruit(getRandomLengthString("Cherry"),
                R.drawable.cherry_pic))
            fruitList.add(Fruit(getRandomLengthString("Mango"),
                R.drawable.mango_pic))

        }
    }

    private fun getRandomLengthString(str:String): String {
        val n = (1..20).random()
        val builder = StringBuilder()
        repeat(n){
            builder.append(str)
        }
        return builder.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        initFruits()
//        val layoutManager = LinearLayoutManager(this)
//        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        val adapter = FruitAdapter2(fruitList)
        recyclerView.adapter = adapter
    }
}


class FruitAdapter2(val fruitList: List<Fruit>) :
    RecyclerView.Adapter<FruitAdapter2.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fruitImage: ImageView = view.findViewById(R.id.fruitImage)
        val fruitName: TextView = view.findViewById(R.id.fruitName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fruit_item, parent, false)
        val viewHolder = MyViewHolder(view)
        viewHolder.itemView.setOnClickListener{
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]
            Toast.makeText(parent.context, "you click view ${fruit.name}", Toast.LENGTH_SHORT).show()
        }
        viewHolder.fruitImage.setOnClickListener{
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]
            Toast.makeText(parent.context, "you click image ${fruit.name}", Toast.LENGTH_SHORT).show()
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val fruit = fruitList[position]
        holder.fruitImage.setImageResource(fruit.imageId)
        holder.fruitName.text = fruit.name
    }

    override fun getItemCount(): Int = fruitList.size


}