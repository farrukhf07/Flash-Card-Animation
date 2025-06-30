package com.example.flashcard.views.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flashcard.model.Question
import com.example.flashcard.ui.theme.red
import com.example.flashcard.ui.theme.white
import com.example.flashcard.viewModel.QuestionVM
import com.example.flashcard.views.components.CustomTextField

@Composable
fun UpdateQuestionScreen(
    btnBack:()-> Unit,
    onUpdate:()->Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val viewModel: QuestionVM = viewModel()
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "BtnBack",
                modifier = Modifier
                    .size(35.dp)
                    .clickable { btnBack() })
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "Update Question",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(35.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(text = "Write  the  Question  and  the  Answer!!",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                fontSize = 22.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(20.dp))
            CustomTextField(
                text = viewModel.questionArg.value?.question.toString(),
                onTextChanged = {viewModel.setQuestion(it)},
                label = "Enter Question",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {focusManager.moveFocus(FocusDirection.Down)}
                )
            )
            Spacer(modifier = Modifier.height(15.dp))
            CustomTextField(
                text = viewModel.questionArg.value?.answer.toString(),
                onTextChanged = {viewModel.setAnswer(it)},
                label = "Enter Answer",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {keyboardController?.hide()}
                )
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                if (viewModel.errorNotFound()){
                    val question = Question(
                        id = 0,
                        question = viewModel.question.value,
                        answer = viewModel.answer.value
                    )
                    viewModel.updatedQuestion()
                    onUpdate()
                    Toast.makeText(context, "Added to watch list", Toast.LENGTH_SHORT).show()
                } else{
                    viewModel.checkValues()
                }
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = red,
                    contentColor = white
                ),
                modifier = Modifier
                    .width(250.dp)
                    .padding(bottom = 40.dp)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Update",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = white
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowUpdateQuestion() {
    UpdateQuestionScreen(btnBack = {}, onUpdate = {})
}