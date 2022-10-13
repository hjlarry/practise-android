package com.example.cameravideo

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import java.io.File

class MainActivity : AppCompatActivity() {
    lateinit var imgUri: Uri
    lateinit var outputImg: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startmyActivity =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(imgUri))
                    val imgView = findViewById<ImageView>(R.id.imageView)
                    imgView.setImageBitmap(bitmap)
                } else {
                    Toast.makeText(this, "some thing error", Toast.LENGTH_SHORT).show()
                }
            }

        val btn = findViewById<Button>(R.id.button)
        btn.setOnClickListener {
            outputImg = File(externalCacheDir, "output_img.jpg")
            if (outputImg.exists()) {
                outputImg.delete()
            }
            outputImg.createNewFile()
            imgUri =
                FileProvider.getUriForFile(this, "com.example.cameravideo.fileprovider", outputImg)
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri)

            startmyActivity.launch(intent)
        }
    }

}