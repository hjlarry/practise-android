package com.example.materialtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.materialtest.databinding.ActivityFruitBinding

class FruitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFruitBinding

    companion object {
        const val FRUIT_NAME = "fruit_name"
        const val FRUIT_IMAGE_ID = "fruit_image_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFruitBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val fruitName = intent.getStringExtra(FRUIT_NAME) ?: "123"
        val fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID, 0)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.collapsToolbar.title = fruitName
        Glide.with(this).load(fruitImageId).into(binding.fruitImageView)
        binding.fruitTextView.text = fruitName.repeat(500)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}