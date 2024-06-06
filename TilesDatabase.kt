package com.example.tiles.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Builds the database
@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class TilesDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var INSTANCE: TilesDatabase? = null

        // Build single instance of the database
        fun getInstance(context: Context): TilesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TilesDatabase::class.java,
                    "tiles_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}