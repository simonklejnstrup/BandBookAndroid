package com.example.thebandbook.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.example.thebandbook.ui.theme.TheBandBookTheme

@Composable
fun ForumScreen(
//    navController: NavController,
    modifier: Modifier = Modifier
) {
    TheBandBookTheme {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight() // This will make the Column take up the entire available height
                    .then(modifier), // Apply any additional modifiers passed to this composable
                verticalArrangement = Arrangement.Center, // This centers the children vertically
                horizontalAlignment = Alignment.CenterHorizontally // Center children horizontally as well
            ) {

                Text(
                    text = "Say something",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(30.dp)
                )

                Box {

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(17.dp),
                    ) {
                        items(count = 20) { index ->
                            ThreadItem(index)
                        }
                        // Frees the last card from the bottom nav
                        item {
                            Spacer(modifier = Modifier.height(80.dp))
                        }
                    }
                    // Gradient overlay
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 1f),
                                        Color.Transparent
                                    ),
                                    startY = Float.POSITIVE_INFINITY,
                                    endY = 0f
                                )
                            )
                    )
                    FloatingActionButton(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(bottom = 96.dp, end = 16.dp),
                        onClick = { /* Handle FAB click here */ },
                        containerColor =
                        MaterialTheme.colorScheme.secondary,
                        //                        Color.Yellow,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "Add",
                            modifier = Modifier.size(40.dp)
                        )
                    }

                }
            }

        }
    }
}

@Composable
fun ThreadItem(index: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.DarkGray
                //color = MaterialTheme.colorScheme.secondaryContainer,
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.DarkGray
                    //color = MaterialTheme.colorScheme.secondaryContainer,
                )
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "20:43",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight(700)
                )
                RedDivider()
                Text(
                    text = "22 jul",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight(700)
                )
                RedDivider()
                Text(
                    text = "Mathias Harbeck",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight(700)
                )
                // Makes the menu button align to the right
                Spacer(modifier = Modifier.weight(1f))
                ThreeDotMenu()
            }
            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
        GrayDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "17 comments",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight(700)
            )
            Icon(
                Icons.Default.KeyboardArrowRight, contentDescription = "See comments",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun RedDivider() {
    Box(
        modifier = Modifier
            .padding(horizontal = 6.dp)
            .width(1.dp)
            .height(12.dp)
            .background(color = MaterialTheme.colorScheme.primary)
    )
}

@Composable
fun GrayDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(
                Color.Gray
//                color = MaterialTheme.colorScheme.primary
            )
    )
}

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

@Preview
@Composable
fun PreviewThreeDotMenu() {
    ThreeDotMenu()
}

@Preview(showBackground = true)
@Composable
fun ThreadItemPreview() {
    ThreadItem(index = 1)
}

@Preview(showBackground = true)
@Composable
fun ForumScreenPreview() {
    ForumScreen()
}


