package com.example.thebandbook.presentation.screens.forum

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.thebandbook.navigation.AppRoutes
import com.example.thebandbook.navigation.NavigationEvent
import com.example.thebandbook.presentation.viewmodels.CreateThreadViewModel
import com.example.thebandbook.ui.theme.TheBandBookTheme
import kotlinx.coroutines.launch

@Composable
fun ForumCreateThreadScreen(modifier: Modifier = Modifier, navController: NavController) {
    // State to hold user input
    var userInput by remember { mutableStateOf("") }
    val viewModel: CreateThreadViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is NavigationEvent.NavigateBack ->  navController.popBackStack()
                is NavigationEvent.NavigateToForum -> navController.navigate(AppRoutes.FORUM)
                else -> {}
            }
        }
    }

    TheBandBookTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize() // Fill the maximum size available
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Back button
                    IconButton(onClick = { viewModel.onBack() }) {
                        Icon(Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary)
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Submit Button
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                viewModel.submitPost(userInput)
                            }
                        }
                    ) {
                        Text("Submit")
                    }
                }
                // User input TextField
                TextField(
                    value = userInput,
                    onValueChange = { userInput = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    label = { Text("Enter your message") },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
                    maxLines = Int.MAX_VALUE // Adjust based on your requirement
                )


            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ForumCreateThreadScreenPreview() {
    MaterialTheme {
        ForumCreateThreadScreen(navController = rememberNavController())
    }
}