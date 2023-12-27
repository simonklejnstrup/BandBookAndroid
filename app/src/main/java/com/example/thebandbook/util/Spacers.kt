package com.example.thebandbook.util

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun VSpacer(height: Dp) {
    Spacer(modifier = Modifier.height(height).fillMaxWidth())
}

@Composable
fun HSpacer(width: Dp) {
    Spacer(modifier = Modifier.width(width).fillMaxHeight())
}
