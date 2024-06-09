package com.example.pi_sniffsnap_garciafernandezmarta.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteDogEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDogDAO(): FavoriteDogDAO

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "sniffsnap_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}