package com.example.pi_sniffsnap_garciafernandezmarta.database

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.pi_sniffsnap_garciafernandezmarta.R
import com.example.pi_sniffsnap_garciafernandezmarta.databinding.ActivityFavoritesBinding

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding
    private val viewModel: FavoriteDogViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = FavoriteDogAdapter()
        /*
        binding.favoritesRecyclerView.adapter = adapter

        viewModel.favoriteDogs.observe(this) { dogs ->
            adapter.submitList(dogs)
        }*/
    }
}