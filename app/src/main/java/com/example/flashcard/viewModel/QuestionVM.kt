package com.example.flashcard.viewModel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.flashcard.data.QuestionDatabase
import com.example.flashcard.data.QuestionRepository
import com.example.flashcard.model.Question
import com.example.flashcard.navigation.DestinationArgs
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.net.URLDecoder

class QuestionVM(application: Application, savedStateHandle: SavedStateHandle):AndroidViewModel(application) {
    val readAllData: LiveData<List<Question>>
    private val repository: QuestionRepository

    val questionArg = mutableStateOf<Question?>(null)

    init {
        val questionDao = QuestionDatabase.getDatabase(application).questionDao()
        repository = QuestionRepository(questionDao)
        readAllData = repository.readAllData
        // for arguments
        val questionJson = savedStateHandle.get<String>(DestinationArgs.question)
        questionArg.value = questionJson?.let { Gson().fromJson(URLDecoder.decode(it,"UTF-8"),Question::class.java) }
    }

    fun insertQuestion(question: Question) {
        viewModelScope.launch {
            repository.insertQuestion(question)
        }
    }

    fun updateQuestion(question: Question) {
        viewModelScope.launch {
            repository.updateQuestion(question)
        }
    }

    fun deleteQuestion(question: Question) {
        viewModelScope.launch {
            repository.deleteQuestion(question)
        }
    }

    val question = mutableStateOf("")
    val questionError = mutableStateOf(false)
    val answer = mutableStateOf("")
    val answerError = mutableStateOf(false)

    fun setQuestion(data:String){
        question.value = data
    }
    fun setAnswer(data:String){
        answer.value = data
    }
    fun errorNotFound():Boolean{
        return question.value.isNotEmpty() || answer.value.isEmpty()
    }

    fun checkValues(){
        questionError.value = question.value.isEmpty()
        answerError.value = answer.value.isEmpty()
    }
    // to update the Argument
    fun setUpdateQuestion(data: String){
        questionArg.value = questionArg.value?.copy(question = data)
    }
    fun updatedQuestion(){
        val updateQuestion = questionArg.value
        val map = HashMap<String, Any>()
        map["id"] = updateQuestion?.id ?: 0
        map["question"] = updateQuestion?.question ?: ""
        map["answer"] = updateQuestion?.answer ?: ""
        if (updateQuestion != null) {
            updateQuestion(updateQuestion   )
        }
    }
}