package com.example.viewtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.viewtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.button.setOnClickListener(this)
        binding.imageView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button -> {
                val text = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
                Toast.makeText(this, text, Toast.LENGTH_LONG).show()
                val progressBar = findViewById<ProgressBar>(R.id.progressBar)
                progressBar.progress = progressBar.progress + 10
                val dialog = AlertDialog.Builder(this).run {
                    setTitle("This is a Dialog")
                    setMessage("Some important thing")
                    setCancelable(false)
                    setPositiveButton("OK") { dialog, which -> }
                    setNegativeButton("cancel"){dialog, which ->}
                    create()
                }
                dialog.show()
            }
            R.id.imageView -> {
                binding.imageView.apply { setImageResource(R.drawable.img_2) }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actions, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        R.id.linear ->{
            startActivity(Intent(this, LinearLayout::class.java))
            true
        }
        R.id.relative ->{
            startActivity(Intent(this, RelativeLayout::class.java))
            true
        }
        R.id.custom ->{
            startActivity(Intent(this, CustomLayout::class.java))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}