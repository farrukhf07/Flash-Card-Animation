package com.example.flashcard.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.flashcard.model.Question


@Database(entities = [Question::class], version = 1, exportSchema = false)
abstract class QuestionDatabase: RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    companion object{
        @Volatile
        private var INSTANCE: QuestionDatabase ?= null

        fun getDatabase(context: Context): QuestionDatabase{
            return INSTANCE ?: synchronized(this){
                Room.databaseBuilder(context, QuestionDatabase::class.java, "question_database")
                .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}