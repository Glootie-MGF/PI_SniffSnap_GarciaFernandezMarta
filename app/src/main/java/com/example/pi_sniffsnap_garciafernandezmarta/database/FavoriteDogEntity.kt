package com.example.pi_sniffsnap_garciafernandezmarta.database
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_dogs")
data class FavoriteDogEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dogId: Long,
    val dogName: String,
    val dogImageUrl: String
)