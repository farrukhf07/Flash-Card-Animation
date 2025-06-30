package com.example.flashcard.views.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flashcard.model.Question
import com.example.flashcard.viewModel.QuestionVM
import com.example.flashcard.views.components.FlashCard
import com.example.flashcard.views.components.FlashCardScreen

@Composable
fun MainScreen(
    btnAdd:() -> Unit,
    btnEdit:(Question)->Unit,
) {
    val viewModel: QuestionVM = viewModel()
    val questionList by viewModel.readAllData.observeAsState(emptyList())
    val currentIndex = remember { mutableStateOf(0) }
    val flipped = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF2F6FA))) {

        // 🔝 Top Bar with Add Button & Title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Icon",
                modifier = Modifier.clickable { btnAdd() }
            )

            Text(
                text = "Flash Card",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.width(64.dp)) // just to center the text
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (questionList.isNotEmpty()) {
            val question = questionList.getOrNull(currentIndex.value)

            question?.let {
               FlashCardScreen(question = it.question,
                   answer = it.answer,
                   flipped = flipped.value,
                   onFlip = {flipped.value =! flipped.value},
                   onDelete = {viewModel.deleteQuestion(it)},
                   onUpdate = {btnEdit(it)},
                   onPrevious = {
                       if (currentIndex.value > 0){
                           currentIndex.value -= 1
                           flipped.value = false
                       }
                   },
                   onNext = {
                       if (currentIndex.value < questionList.lastIndex){
                           currentIndex.value += 1
                           flipped.value = false
                       }
                   }
               )
        }
    }
}
}
