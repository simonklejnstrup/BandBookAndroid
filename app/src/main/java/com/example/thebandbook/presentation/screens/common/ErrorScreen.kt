package com.example.thebandbook.presentation.screens.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.thebandbook.R

@Composable
fun ErrorScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painterResource(
                id = R.drawable.ic_denied
            ),
            contentDescription = "Nothing found",
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.Center)
        )
    }
}