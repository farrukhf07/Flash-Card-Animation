package com.example.flashcard.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.flashcard.model.Question

@Dao
interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: Question)

    @Query("SELECT * FROM tbl_question")
    fun readAll(): LiveData<List<Question>>

    @Update
    suspend fun updateQuestion(movie:Question)

    @Delete
    suspend fun deleteQuestion(movie: Question)


    @Query("SELECT * FROM tbl_question WHERE id = :id")
    suspend fun getQuestionById(id: Int): Question?
}