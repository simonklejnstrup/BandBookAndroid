package com.example.thebandbook.presentation.screens.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GreenWideButton(
    label: String,
    onClick: () -> Unit,
) {

    val shape = RoundedCornerShape(4.dp)

    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor =  MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = shape,
        onClick = onClick
    ) {
        Text(text = label)
    }
}