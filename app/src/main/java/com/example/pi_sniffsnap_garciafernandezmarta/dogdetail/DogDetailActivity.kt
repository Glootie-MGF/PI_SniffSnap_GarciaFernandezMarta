package com.example.pi_sniffsnap_garciafernandezmarta.dogdetail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.pi_sniffsnap_garciafernandezmarta.model.Dog
import com.example.pi_sniffsnap_garciafernandezmarta.R
import com.example.pi_sniffsnap_garciafernandezmarta.api.ApiResponseStatus
import com.example.pi_sniffsnap_garciafernandezmarta.database.FavoriteDogEntity
import com.example.pi_sniffsnap_garciafernandezmarta.database.FavoritesViewModel
import com.example.pi_sniffsnap_garciafernandezmarta.databinding.ActivityDogDetailBinding

class DogDetailActivity : AppCompatActivity() {

    companion object {
        const val DOG_KEY = "dog"
        const val IS_RECOGNITION_KEY = "is_recognition"
    }

    private val viewModel: DogDetailViewModel by viewModels()
    private val viewModelFav: FavoritesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                val favoriteDogEntity = FavoriteDogEntity(dogId = dog.id, dogName = dog.name, dogImageUrl = dog.imageUrl)
                viewModelFav.addFavoriteDog(favoriteDogEntity)
            }
        }
    }
}