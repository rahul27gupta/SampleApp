package com.sampleapp.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.sampleapp.database.dao.ModuleProgressDao
import com.sampleapp.database.dao.QuizResultDao
import com.sampleapp.database.entities.ModuleProgressEntity
import com.sampleapp.database.entities.QuizResultEntity
import com.sampleapp.database.entities.QuestionHistoryConverter

@Database(
    entities = [
        ModuleProgressEntity::class,
        QuizResultEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(QuestionHistoryConverter::class)
abstract class QuizDatabase : RoomDatabase() {
    
    abstract fun moduleProgressDao(): ModuleProgressDao
    abstract fun quizResultDao(): QuizResultDao
    
    companion object {
        @Volatile
        private var INSTANCE: QuizDatabase? = null
        
        fun getDatabase(context: Context): QuizDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuizDatabase::class.java,
                    "quiz_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
