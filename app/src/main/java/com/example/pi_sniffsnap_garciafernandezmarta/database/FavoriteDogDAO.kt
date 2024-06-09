package com.example.pi_sniffsnap_garciafernandezmarta.database
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDogDAO {
    @Insert
    suspend fun addFavoriteDog(favoriteDogEntity: FavoriteDogEntity)

    @Query("SELECT * FROM favorite_dogs")
    fun getAllFavoriteDogs(): LiveData<List<FavoriteDogEntity>>
}