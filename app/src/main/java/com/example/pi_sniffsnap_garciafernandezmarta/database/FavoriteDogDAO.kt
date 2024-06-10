package com.example.pi_sniffsnap_garciafernandezmarta.database
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDogDAO {
    // Consultas SQL en corrutinas
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteDog(favoriteDogEntity: FavoriteDogEntity)

    @Query("DELETE FROM favorite_dogs WHERE dogId = :dogId")
    suspend fun deleteFavoriteDog(dogId: Long)

    @Query("SELECT * FROM favorite_dogs")
    fun getAllFavoriteDogs(): LiveData<List<FavoriteDogEntity>>
}