package com.example.pi_sniffsnap_garciafernandezmarta.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FavoritesViewModel(private val favoriteDogDAO: FavoriteDogDAO): ViewModel() {

    fun addFavoriteDog(favoriteDogEntity: FavoriteDogEntity) {
        // Hacer uso del DAO para insertar el perro en la base de datos
        viewModelScope.launch {
            favoriteDogDAO.addFavoriteDog(favoriteDogEntity)
        }
    }
}