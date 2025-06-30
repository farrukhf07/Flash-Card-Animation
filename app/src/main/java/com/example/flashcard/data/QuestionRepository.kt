package com.example.flashcard.data

import androidx.lifecycle.LiveData
import com.example.flashcard.model.Question

class QuestionRepository (private val questionDao: QuestionDao) {
    val readAllData: LiveData<List<Question>> = questionDao.readAll()

    suspend fun insertQuestion(question: Question){
        questionDao.insertQuestion(question)
    }

    suspend fun updateQuestion(question: Question){
        questionDao.updateQuestion(question)
    }

    suspend fun deleteQuestion(question: Question){
        questionDao.deleteQuestion(question)
    }
}