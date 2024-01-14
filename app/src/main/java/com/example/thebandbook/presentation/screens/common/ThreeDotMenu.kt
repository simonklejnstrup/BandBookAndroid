package com.example.thebandbook.presentation.screens.common

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.PopupProperties

@Composable
fun ThreeDotMenu() {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                Icons.Default.MoreVert, contentDescription = "More options",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            properties = PopupProperties(focusable = true)
        ) {
            DropdownMenuItem(
                text = { Text("Edit") },
                onClick = {
                    // Todo: Handle  click
                    expanded = false
                }
            )
        }
    }
}