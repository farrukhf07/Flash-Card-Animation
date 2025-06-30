package com.example.flashcard.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_question")
data class Question(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val question: String,
    val answer: String
)
