package com.example.pi_sniffsnap_garciafernandezmarta.database
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDogDAO {
    @Insert
    suspend fun addFavoriteDog(favoriteDogEntity: FavoriteDogEntity)

    @Query("DELETE FROM favorite_dogs WHERE id = :favoriteDogEntity-id")
    suspend fun deleteFavoriteDog(favoriteDogEntity: Long)

    @Query("SELECT * FROM favorite_dogs")
    fun getAllFavoriteDogs(): LiveData<List<FavoriteDogEntity>>
}