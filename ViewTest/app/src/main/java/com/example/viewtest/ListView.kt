package com.example.viewtest

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class ListView : AppCompatActivity() {
    //    private val data = listOf(
//        "Apple", "Banana", "Orange", "Watermelon",
//        "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango",
//        "Apple", "Banana", "Orange", "Watermelon", "Pear", "Grape",
//        "Pineapple", "Strawberry", "Cherry", "Mango"
//    )
    private val fruitList = ArrayList<Fruit>()

    private fun initFruits() {
        repeat(20) {
            fruitList.add(Fruit("Apple", R.drawable.apple_pic))
            fruitList.add(Fruit("Banana", R.drawable.banana_pic))
            fruitList.add(Fruit("Orange", R.drawable.orange_pic))
            fruitList.add(Fruit("Watermelon", R.drawable.watermelon_pic))
            fruitList.add(Fruit("Pear", R.drawable.pear_pic))
            fruitList.add(Fruit("Grape", R.drawable.grape_pic))
            fruitList.add(Fruit("Pineapple", R.drawable.pineapple_pic))
            fruitList.add(Fruit("Strawberry", R.drawable.strawberry_pic))
            fruitList.add(Fruit("Cherry", R.drawable.cherry_pic))
            fruitList.add(Fruit("Mango", R.drawable.mango_pic))
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)

        initFruits()
//        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)
        val adapter = FruitAdapter(this, R.layout.fruit_item, fruitList)
        val mylist = findViewById<android.widget.ListView>(R.id.mylist)
        mylist.adapter = adapter
        mylist.setOnItemClickListener{ _, _, position, _ ->
            val fruit = fruitList[position]
            Toast.makeText(this, fruit.name, Toast.LENGTH_SHORT).show()
        }
    }
}

class Fruit(val name: String, val imageId: Int)

//class FruitAdapter(activity: Activity, val resourceId: Int, data: List<Fruit>) :
//    ArrayAdapter<Fruit>(activity, resourceId, data) {
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val view = LayoutInflater.from(context).inflate(resourceId, parent, false)
//        val fruitImage: ImageView = view.findViewById(R.id.fruitImage)
//        val fruitName: TextView = view.findViewById(R.id.fruitName)
//        val fruit = getItem(position)
//        if (fruit != null){
//            fruitImage.setImageResource(fruit.imageId)
//            fruitName.text = fruit.name
//        }
//        return view
//    }
//}


// 优化后，convertView可以将之前加载好的布局进行缓存， ViewHolder不用每次调用findViewById来获取控件实例
class FruitAdapter(activity: Activity, val resourceId: Int, data: List<Fruit>) :
    ArrayAdapter<Fruit>(activity, resourceId, data) {

    inner class ViewHolder(val fruitImage: ImageView, val fruitName:TextView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder : ViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(resourceId, parent, false)
            val fruitImage: ImageView = view.findViewById(R.id.fruitImage)
            val fruitName: TextView = view.findViewById(R.id.fruitName)
            viewHolder = ViewHolder(fruitImage, fruitName)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val fruit = getItem(position)
        if (fruit != null) {
            viewHolder.fruitImage.setImageResource(fruit.imageId)
            viewHolder.fruitName.text = fruit.name
        }
        return view
    }
}
