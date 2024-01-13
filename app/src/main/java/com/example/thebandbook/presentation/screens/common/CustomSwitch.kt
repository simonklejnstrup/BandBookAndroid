package com.example.thebandbook.presentation.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    // Custom dimensions
    val trackHeight = 20.dp
    val thumbSize = 40.dp
    val trackWidth = 80.dp // Specify the width of the track

    Box(
        modifier = modifier
            .width(trackWidth)
            .height(trackHeight)
            .clip(RoundedCornerShape(50)) // Rounded corners for the track
            .background(if (checked) Color.Gray else Color.Gray) // Track color
            .clickable { onCheckedChange(!checked) }
    ) {
        Box(
            modifier = Modifier
                .size(thumbSize)
                .align(if (checked) Alignment.CenterEnd else Alignment.CenterStart)
                .background(color = Color.DarkGray, shape = CircleShape) // Thumb styling
        )
    }
}

