package com.example.thebandbook.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.thebandbook.ui.theme.TheBandBookTheme

@Composable
fun DashboardScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Text(
            text = "Hello Dashboard",
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    TheBandBookTheme {
        DashboardScreen()
    }
}