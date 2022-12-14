package com.example.cameravideo

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.content.FileProvider
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var imgUri: Uri
    private lateinit var outputImg: File
    private val mediaPlayer = MediaPlayer()

    val startCap = registerForActivityResult(StartActivityForResult()) { result ->
        onActivityResult(1, result)
    }

    val startFile = registerForActivityResult(StartActivityForResult()) { result ->
        onActivityResult(2, result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initMediaPlayer()

        showPic()
        playMusic()
    }

    private fun showPic() {
        val btn = findViewById<Button>(R.id.capture)
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
            startCap.launch(intent)
        }

        val btn2 = findViewById<Button>(R.id.fromAlbum)
        btn2.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startFile.launch(intent)
        }
    }

    private fun onActivityResult(requestCode: Int, result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            var bitmap: Bitmap? = null
            when (requestCode) {
                1 -> {
                    bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(imgUri))
                }
                2 -> {
                    val uri = result.data?.data
                    val furi = uri?.let { contentResolver.openFileDescriptor(it, "r") }
                    bitmap = BitmapFactory.decodeFileDescriptor(furi?.fileDescriptor)
                }
            }
            val imgView = findViewById<ImageView>(R.id.imageView)
            imgView.setImageBitmap(bitmap)

        } else {
            Toast.makeText(this, "some thing error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initMediaPlayer() {
        val assetManager = assets
        val fd = assetManager.openFd("music.mp3")
        mediaPlayer.setDataSource(fd.fileDescriptor, fd.startOffset, fd.length)
        mediaPlayer.prepare()
    }

    private fun playMusic() {
        val play = findViewById<Button>(R.id.play)
        play.setOnClickListener {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start()
            }
        }

        val pause = findViewById<Button>(R.id.pause)
        pause.setOnClickListener {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause()
            } else {
                mediaPlayer.start()
            }
        }

        val stop = findViewById<Button>(R.id.stop)
        stop.setOnClickListener {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.reset()
                initMediaPlayer()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
    }
}