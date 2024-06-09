package com.example.pi_sniffsnap_garciafernandezmarta.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteDogEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDogDAO(): FavoriteDogDAO
}