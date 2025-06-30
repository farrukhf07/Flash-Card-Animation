package com.example.flashcard.views.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flashcard.ui.theme.red
import com.example.flashcard.ui.theme.white

@Composable
fun FlashCard(
    question: String,
    answer: String,
    flipped: Boolean,
    onFlip: ()-> Unit,
    onDelete: ()-> Unit,
    onUpdate: ()-> Unit,
    onPrevious: ()-> Unit,
    onNext: ()-> Unit
) {
        val rotation = animateFloatAsState(
        targetValue = if (flipped) 180f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "flip"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .graphicsLayer {
                    rotationY = rotation.value
                    cameraDistance = 8 * density
                }
                .background(MaterialTheme.colors.surface, RoundedCornerShape(16.dp))
                .shadow(8.dp, RoundedCornerShape(16.dp))
                .clickable { onFlip() },
            contentAlignment = Alignment.Center
        ) {
            if (rotation.value <= 90f) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Icon",
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 15.dp, top = 18.dp)
                        .clickable { onUpdate() }
                )
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Icon",
                    modifier = Modifier.align(Alignment.TopStart)
                        .padding(start = 37.dp, top =18.dp)
                        .clickable { onDelete() }
                )
                Text(
                    text = question,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                Text(
                    text = answer,
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .padding(16.dp)
                        .graphicsLayer {
                            rotationY = 180f
                        }
                )
            }
        }

        // Flip Button
        Button(
            onClick = { onFlip() },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = red,
                contentColor = white
            ),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(top = 16.dp)
        ) {
            Text(text = if (flipped) "Back" else "Ans")
        }

        // Navigation Buttons
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(top = 280.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {onPrevious()},
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("Previous")
            }
            Button(onClick = {onNext()}) {
                Text("Next")
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun ShowFlashCard() {
    FlashCard(
        question = "This is the Question",
        answer = "This is Answer",
        onDelete = { },
        onPrevious = { },
        onNext = {},
        onUpdate = {},
        onFlip = {},
        flipped = false)
}
