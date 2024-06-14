package com.example.pi_sniffsnap_garciafernandezmarta.dogdetail

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.pi_sniffsnap_garciafernandezmarta.R
import com.example.pi_sniffsnap_garciafernandezmarta.api.ApiResponseStatus
import com.example.pi_sniffsnap_garciafernandezmarta.database.AppDatabase
import com.example.pi_sniffsnap_garciafernandezmarta.database.FavoriteDogEntity
import com.example.pi_sniffsnap_garciafernandezmarta.databinding.ActivityDogDetailBinding
import com.example.pi_sniffsnap_garciafernandezmarta.model.Dog
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class DogDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDogDetailBinding
    companion object {
        const val DOG_KEY = "dog"
        const val IS_RECOGNITION_KEY = "is_recognition"
    }

    private val viewModel: DogDetailViewModel by viewModels()

    private lateinit var appDatabase: AppDatabase
    private var isFavorite: Boolean = false
    private lateinit var favoriteDog: FavoriteDogEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDogDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDatabase = AppDatabase.getDatabase(this)

        val dog = intent?.extras?.getParcelable<Dog>(DOG_KEY)
        val isRecognition = intent?.extras?.getBoolean(IS_RECOGNITION_KEY, false) ?: false

        if (dog == null) {
            Toast.makeText(this, R.string.error_dog_not_found, Toast.LENGTH_LONG).show()
            finish()
            return
        } else {
            binding.dogIndex.text = getString(R.string.dog_index_format, dog.index)
            binding.lifeExpectancy.text = getString(R.string.dog_life_expectancy_format, dog.lifeExpectancy)
            binding.dog = dog
            binding.dogImage.load(dog.imageUrl)

            viewModel.status.observe(this) { status ->

                when (status) {
                    is ApiResponseStatus.Error -> {
                        binding.loadingWheel.visibility = View.GONE
                        Toast.makeText(this, status.messageId, Toast.LENGTH_SHORT).show()
                    }

                    is ApiResponseStatus.Loading -> binding.loadingWheel.visibility = View.VISIBLE
                    is ApiResponseStatus.Success -> {
                        binding.loadingWheel.visibility = View.GONE
                        Toast.makeText(this, "Dog succesfully added to collection", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
            binding.closeButton.setOnClickListener{
                if (isRecognition){
                    viewModel.addDogToUser(dog.id)
                } else {
                    finish()
                }
            }

            binding.buttonFavorite.setOnClickListener {
                toggleFavorite(dog)
            }
            checkIfFavorite(dog.id)
            favoriteDog = FavoriteDogEntity(dogId = dog.id, dogName = dog.name, dogImageUrl = dog.imageUrl)

            binding.buttonShare.setOnClickListener{
                shareScreenshot()
            }
        }
    }

    private fun shareScreenshot() {
        val rootView = window.decorView.findViewById<View>(android.R.id.content)
        val bitmap = getScreenShotFromView(rootView)

        if (bitmap != null) {
            saveMediaToStorage(bitmap)
        }
    }
    private fun getScreenShotFromView(v: View): Bitmap? {
        var screenshot: Bitmap? = null
        try {
            screenshot = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(screenshot)
            v.draw(canvas)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return screenshot
    }
    private fun saveMediaToStorage(bitmap: Bitmap) {
        val filename = "${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null
        var imageUri: Uri? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
            imageUri = FileProvider.getUriForFile(this, "${applicationContext.packageName}.fileprovider", image)
        }

        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(this, "Screenshot saved to gallery", Toast.LENGTH_SHORT).show()
            imageUri?.let { uri ->
                shareImage(uri)
            }
        }
    }
    private fun shareImage(imageUri: Uri) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, imageUri)
            type = "image/jpeg"
        }
        startActivity(Intent.createChooser(intent, "Share via"))
    }


    private fun toggleFavorite(dog: Dog) {
        lifecycleScope.launch {
            //val favoriteDog = FavoriteDogEntity(dogId = dog.id, dogName = dog.name, dogImageUrl = dog.imageUrl)
            if (isFavorite) {
                appDatabase.favoriteDogDAO().deleteFavoriteDog(favoriteDog.dogId)
                isFavorite = false
            } else {
                appDatabase.favoriteDogDAO().addFavoriteDog(favoriteDog)
                isFavorite = true
            }
            updateFavoriteIcon()
        }
    }

    private fun checkIfFavorite(dogId: Long) {
        appDatabase.favoriteDogDAO().getAllFavoriteDogs().observe(this, Observer { dogs ->
            isFavorite = dogs.any { it.dogId == dogId }
            updateFavoriteIcon()
        })
    }

    private fun updateFavoriteIcon() {
        if (isFavorite) {
            binding.buttonFavorite.setImageResource(R.drawable.ic_star_filled)
        } else {
            binding.buttonFavorite.setImageResource(R.drawable.ic_star_border)
        }
    }
}