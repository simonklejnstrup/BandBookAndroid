package com.example.thebandbook.presentation.screens.forum

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.thebandbook.R
import com.example.thebandbook.authentication.GoogleAuthClientSingleton
import com.example.thebandbook.domain.model.Comment
import com.example.thebandbook.navigation.AppRoutes
import com.example.thebandbook.navigation.NavigationEvent
import com.example.thebandbook.presentation.screens.common.CommentInfoRow
import com.example.thebandbook.presentation.screens.common.SubmitButton
import com.example.thebandbook.presentation.screens.common.VSpacer
import com.example.thebandbook.presentation.viewmodels.SharedThreadBottomSheetViewModel
import com.example.thebandbook.ui.theme.TheBandBookTheme

@Composable
fun ForumThreadScreen(
    navController: NavController?,
    threadId: Int
) {
    var userInput by remember { mutableStateOf("") }
    val currentUser = GoogleAuthClientSingleton.googleAuthUiClient.getSignedInUser()
    val viewModel: SharedThreadBottomSheetViewModel = viewModel()
    val thread by viewModel.selectedThread.collectAsState()



    // Observe navigation events
    LaunchedEffect(key1 = Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is NavigationEvent.NavigateToForumThreadScreen -> navController?.let {
                    // Include the threadId in the navigation route
                    val route = "${AppRoutes.FORUM_VIEW_THREAD}/${threadId}"
                    navController.navigate(route)
                }
                else -> { }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.loadThread(threadId)
    }

    TheBandBookTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight()
                .padding(horizontal = 15.dp)
        ) {
            if (thread != null) {
                VSpacer(height = 30.dp)
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = thread!!.title ?: "",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary)
                VSpacer(height = 30.dp)

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(thread!!.comments) { comment ->
                        CommentItem(comment = comment)
                    }
                }
                OutlinedTextField(
                    value = userInput,
                    onValueChange = { userInput = it },
                    label = { Text("Add a comment") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                SubmitButton(onClick = {
                    if (currentUser != null) {
                        thread?.let { currentThread ->
                            viewModel.submitComment(userInput, currentThread, currentUser)
                        }
                    }
                })
                // Compensate for bottom nav
                VSpacer(height = 60.dp)
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally)
                ) {
                    Icon(
                        painterResource(
                            id = R.drawable.ic_denied
                        ),
                        contentDescription = "Nothing found",
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun CommentItem(comment: Comment) {
    TheBandBookTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            CommentInfoRow(
                modifier = Modifier.padding(start = 15.dp),
                comment = comment
            )
            CommentBox(
//                modifier = Modifier
//                    .height(200.dp),
                comment = comment
            )
        }
    }
}


@Preview(showBackground = false)
@Composable
fun ThreadBottomSheetContentPreview() {
    ForumThreadScreen(null, 1)
}

@Composable
fun CommentBox(
    modifier: Modifier = Modifier,
    comment: Comment,
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.tertiaryContainer,
                shape = RoundedCornerShape(size = 16.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    shape = RoundedCornerShape(size = 16.dp)
                )
        ) {
            Text(
                modifier = Modifier
                    .padding(10.dp),
                text = comment.content,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary,
                lineHeight = 14.sp,
                overflow = TextOverflow.Ellipsis
            )
        }

    }
}

