package com.example.pi_sniffsnap_garciafernandezmarta.database
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteDog(favoriteDogEntity: FavoriteDogEntity)

    @Delete
    suspend fun deleteFavoriteDog(favoriteDogEntity: FavoriteDogEntity)

    @Query("SELECT * FROM favorite_dogs")
    fun getAllFavoriteDogs(): LiveData<List<FavoriteDogEntity>>
}