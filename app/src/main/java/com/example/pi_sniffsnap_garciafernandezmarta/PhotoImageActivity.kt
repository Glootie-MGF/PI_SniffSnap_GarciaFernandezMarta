package com.example.pi_sniffsnap_garciafernandezmarta

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.load
import com.example.pi_sniffsnap_garciafernandezmarta.databinding.ActivityPhotoImageBinding
import java.io.File

class PhotoImageActivity : AppCompatActivity() {
    companion object{
        const val PHOTO_URI_KEY = "photo_uri"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPhotoImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val photoUri = intent.extras?.getString(PHOTO_URI_KEY)
        val uri = Uri.parse(photoUri)
        val path = uri.path

        if (path == null){
            Toast.makeText(this, "ERROR Showing image, no photo uri", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Mostramos la foto en el ImageView usando la librería Coil para File
        binding.wholeImage.load(File(path))
    }
}