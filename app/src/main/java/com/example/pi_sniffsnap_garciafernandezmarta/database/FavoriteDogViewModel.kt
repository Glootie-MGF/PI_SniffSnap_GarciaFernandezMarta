package com.example.pi_sniffsnap_garciafernandezmarta.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FavoriteDogViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FavoriteDogRepository
    val allFavoriteDogs: LiveData<List<FavoriteDogEntity>>

    init {
        val favoriteDogDao = AppDatabase.getDatabase(application).favoriteDogDao
        repository = FavoriteDogRepository(favoriteDogDao)
        allFavoriteDogs = repository.allFavoriteDogs
    }

    fun insert(favoriteDog: FavoriteDogEntity) = viewModelScope.launch {
        repository.insert(favoriteDog)
    }

    fun delete(favoriteDog: FavoriteDogEntity) = viewModelScope.launch {
        repository.delete(favoriteDog)
    }
}