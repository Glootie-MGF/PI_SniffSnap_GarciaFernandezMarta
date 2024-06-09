package com.example.pi_sniffsnap_garciafernandezmarta.database

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pi_sniffsnap_garciafernandezmarta.databinding.ActivityFavoritesBinding

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding
    private val viewModel: FavoriteDogViewModel by viewModels()
    private lateinit var adapter: FavoriteDogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = FavoriteDogAdapter()
        binding.favoritesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.favoritesRecyclerView.adapter = adapter

        viewModel.allFavoriteDogs.observe(this, Observer { dogs ->
            adapter.setFavoriteDogs(dogs)
        })
    }
}