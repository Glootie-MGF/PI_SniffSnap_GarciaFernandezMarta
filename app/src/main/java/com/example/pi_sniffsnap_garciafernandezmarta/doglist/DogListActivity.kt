package com.example.pi_sniffsnap_garciafernandezmarta.doglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import coil.annotation.ExperimentalCoilApi
import com.example.pi_sniffsnap_garciafernandezmarta.api.ApiResponseStatus
import com.example.pi_sniffsnap_garciafernandezmarta.databinding.ActivityDogListBinding
import com.example.pi_sniffsnap_garciafernandezmarta.dogdetail.DogDetailActivity
import com.example.pi_sniffsnap_garciafernandezmarta.dogdetail.DogDetailActivity.Companion.DOG_KEY
import com.example.pi_sniffsnap_garciafernandezmarta.dogdetail.DogDetailComposeActivity
@ExperimentalCoilApi
private const val GRID_SPAN = 4
class DogListActivity : AppCompatActivity() {

    private val dogListViewModel: DogListViewModel by viewModels() // Para poder instanciar un ViewModel de esta manera
    @OptIn(ExperimentalCoilApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // val dogList = getFakeDogs()

        val loadingWheel = binding.loadingWheel

        val recycler = binding.dogRecycler
        // recycler.layoutManager = LinearLayoutManager(this)
        recycler.layoutManager = GridLayoutManager(this, GRID_SPAN)

        val adapter = DogAdapter()
        adapter.setOnItemClickListener {
            // Pasamos el 'dog' a 'DogDetailActivity'
            // Modificamos DogDetailActivity por DogDetailComposeActivity para usar Jetpack Compose
            val intent = Intent(this, DogDetailActivity::class.java)
            intent.putExtra(DOG_KEY, it)
            startActivity(intent)
        }

        adapter.setLongOnItemClickListener {
            dogListViewModel.addDogToUser(it.id)
        }

        recycler.adapter = adapter

        dogListViewModel.dogList.observe(this){
            dogList ->
            adapter.submitList(dogList)
        }

        dogListViewModel.status.observe(this){
            status ->

            when(status) {
                is ApiResponseStatus.Error -> {
                    loadingWheel.visibility = View.GONE
                    Toast.makeText(this, status.messageId, Toast.LENGTH_SHORT).show()
                }
                is ApiResponseStatus.Loading -> loadingWheel.visibility = View.VISIBLE
                is ApiResponseStatus.Success -> loadingWheel.visibility = View.GONE
            }

            /*when(status) {
                ApiResponseStatus.LOADING -> {
                    // Mostraremos un progressbar
                    loadingWheel.visibility = View.VISIBLE
                }
                ApiResponseStatus.ERROR -> {
                    // Ocultar progressbar y mostrar mensaje de error
                    loadingWheel.visibility = View.GONE
                    Toast.makeText(this, "ERROR downloading data", Toast.LENGTH_SHORT).show()
                }
                ApiResponseStatus.SUCCESS -> {
                    // Ocultar progressbar
                    loadingWheel.visibility = View.GONE
                }
                else ->{
                    loadingWheel.visibility = View.GONE
                    Toast.makeText(this, "ERROR undefined status", Toast.LENGTH_SHORT).show()
                }
            }*/
        }

    }
    /*private fun getFakeDogs(): MutableList<Dog> {
        val dogList = mutableListOf<Dog>()
        dogList.add(
            Dog(
                1, 1, "Chihuahua", "Toy", 5.4,
                6.7, "", "12 - 15", "", 10.5,
                12.3
            )
        )
        dogList.add(
            Dog(
                2, 1, "Labrador", "Toy", 5.4,
                6.7, "", "12 - 15", "", 10.5,
                12.3
            )
        )
        dogList.add(
            Dog(
                3, 1, "Retriever", "Toy", 5.4,
                6.7, "", "12 - 15", "", 10.5,
                12.3
            )
        )
        dogList.add(
            Dog(
                4, 1, "San Bernardo", "Toy", 5.4,
                6.7, "", "12 - 15", "", 10.5,
                12.3
            )
        )
        dogList.add(
            Dog(
                5, 1, "Husky", "Toy", 5.4,
                6.7, "", "12 - 15", "", 10.5,
                12.3
            )
        )
        dogList.add(
            Dog(
                6, 1, "Xoloscuincle", "Toy", 5.4,
                6.7, "", "12 - 15", "", 10.5,
                12.3
            )
        )
        return dogList
    }*/
}