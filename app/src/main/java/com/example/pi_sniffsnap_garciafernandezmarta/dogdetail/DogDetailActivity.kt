package com.example.pi_sniffsnap_garciafernandezmarta.dogdetail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.pi_sniffsnap_garciafernandezmarta.model.Dog
import com.example.pi_sniffsnap_garciafernandezmarta.R
import com.example.pi_sniffsnap_garciafernandezmarta.databinding.ActivityDogDetailBinding

class DogDetailActivity : AppCompatActivity() {

    companion object {
        const val DOG_KEY = "dog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dog = intent?.extras?.getParcelable<Dog>(DOG_KEY)

        if (dog == null) {
            Toast.makeText(this, R.string.error_dog_not_found, Toast.LENGTH_LONG).show()
            finish()
            return
        } else {
            binding.dogIndex.text = getString(R.string.dog_index_format, dog.index)
            binding.lifeExpectancy.text = getString(R.string.dog_life_expectancy_format, dog.lifeExpectancy)
            binding.dog = dog
            binding.dogImage.load(dog.imageUrl)
            binding.closeButton.setOnClickListener{
                finish()
            }
        }
    }
}