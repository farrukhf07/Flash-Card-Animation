package com.example.flashcard.views.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flashcard.navigation.DestinationArgs.question
import com.example.flashcard.ui.theme.buttonClr
import com.example.flashcard.ui.theme.red
import com.example.flashcard.ui.theme.white

@Composable
fun FlashCardScreen(
    question: String,
    answer: String,
    flipped: Boolean,
    onFlip: () -> Unit,
    onDelete: () -> Unit,
    onUpdate: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    var isFlipped by remember { mutableStateOf(false) }
    val rotationXAngle by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "rotationX"
    )

    val rotationZAngle by animateFloatAsState(
        targetValue = if (isFlipped) -180f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "rotationZ"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F6FA))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(modifier = Modifier.height(200.dp))

        Box(
            modifier = Modifier
                .width(320.dp)
                .height(220.dp)
                .clip(RoundedCornerShape(20.dp))
                .clickable { isFlipped = !isFlipped },
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        rotationX = rotationXAngle
                        rotationZ = rotationZAngle
                        cameraDistance = 8 * density
                        alpha = if (rotationXAngle <= 90f) 1f else 0f
                    }
                    .background(Color(0xFF1E2A3A))
            ) {
                CardFront(question = question,
                    { onUpdate() },
                    { onDelete() })
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        rotationX = rotationXAngle - 180f
                        rotationZ = rotationZAngle + 180f
                        cameraDistance = 8 * density
                        alpha = if (rotationXAngle > 90f) 1f else 0f
                    }
                    .background(Color(0xFF1E2A3A))
            ) {
                CardBack(answer = answer,
                    flipped = flipped)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        ToggleButtons({ onNext() }, { onPrevious() })

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { isFlipped = !isFlipped
                onFlip()
                      },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2C3E50)),
            shape = CircleShape
        ) {
            Text("ANS", fontStyle = FontStyle.Italic, color = Color.White)
        }
    }
}


@Composable
fun CardFront(
    question: String,
    onUpdate: () -> Unit,
    onDelete: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                tint = Color.Gray,
                modifier = Modifier
                    .padding(start = 15.dp, top = 10.dp)
                    .clickable { onUpdate() }
            )
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.Gray,
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp)
                    .clickable { onDelete() }
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = question,
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic,
            color = Color(0xFFE0E0E0),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CardBack(
    answer: String,
    flipped: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = answer,
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic,
            color = Color(0xFFE0E0E0),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ToggleButtons(
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {
    var selected by remember { mutableStateOf("PREVIOUS") }

    Row(
        modifier = Modifier
            .width(250.dp)
            .height(48.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(Color(0xFF1C2838)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ToggleButton(
            label = "PREVIOUS",
            selected = selected == "PREVIOUS",
            onClick = { selected = "PREVIOUS"
                onPrevious()
                      },
            modifier = Modifier.weight(1f)
        )
        ToggleButton(
            label = "NEXT",
            selected = selected == "NEXT",
            onClick = { selected = "NEXT"
                onNext()
                      },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ToggleButton(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(25.dp))
            .background(if (selected) Color(0xFF475C7C) else Color.Transparent)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = Color.White
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ShowFrontCardContent() {
    FlashCardScreen(
        question = "This is question",
        answer = "Answer",
        flipped = false,
        onFlip = {},
        onDelete = {},
        onUpdate = {},
        onPrevious = {},
        onNext = {}
    )
}
