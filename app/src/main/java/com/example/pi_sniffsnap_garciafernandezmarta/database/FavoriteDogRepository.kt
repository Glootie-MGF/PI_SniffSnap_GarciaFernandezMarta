package com.example.pi_sniffsnap_garciafernandezmarta.database

import androidx.lifecycle.LiveData

class FavoriteDogRepository(private val favoriteDogDao: FavoriteDogDAO) {
    val allFavoriteDogs: LiveData<List<FavoriteDogEntity>> = favoriteDogDao.getAllFavoriteDogs()

    suspend fun insert(favoriteDogEntity: FavoriteDogEntity) {
        favoriteDogDao.addFavoriteDog(favoriteDogEntity)
    }

    suspend fun delete(favoriteDogEntity: FavoriteDogEntity) {
        favoriteDogDao.deleteFavoriteDog(favoriteDogEntity.dogId)
    }
}