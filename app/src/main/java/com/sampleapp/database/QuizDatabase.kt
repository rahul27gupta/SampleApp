package com.sampleapp.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.sampleapp.database.dao.ModuleProgressDao
import com.sampleapp.database.entities.ModuleProgressEntity

@Database(
    entities = [
        ModuleProgressEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class QuizDatabase : RoomDatabase() {
    
    abstract fun moduleProgressDao(): ModuleProgressDao
    
    companion object {
        @Volatile
        private var INSTANCE: QuizDatabase? = null
        
        fun getDatabase(context: Context): QuizDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuizDatabase::class.java,
                    "quiz_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
