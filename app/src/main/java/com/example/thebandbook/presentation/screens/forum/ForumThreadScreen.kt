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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.thebandbook.presentation.screens.common.BottomNavCompensationSpacer
import com.example.thebandbook.presentation.screens.common.CommentInfoRow
import com.example.thebandbook.presentation.screens.common.ErrorScreen
import com.example.thebandbook.presentation.screens.common.VSpacer
import com.example.thebandbook.presentation.viewmodels.ForumThreadViewModel
import com.example.thebandbook.ui.theme.TheBandBookTheme

@Composable
fun ForumThreadScreen(
    navController: NavController?,
    threadId: Int
) {
    var userInput by remember { mutableStateOf("") }
    val currentUser = GoogleAuthClientSingleton.googleAuthUiClient.getSignedInUser()
    val viewModel: ForumThreadViewModel = viewModel()
    val threadState by viewModel.selectedThread.collectAsState()
    val currentThread = threadState
    val commentSubmissionSuccessful by viewModel.commentSubmissionSuccessful.collectAsState(initial = true)

    LaunchedEffect(key1 = commentSubmissionSuccessful) {
        if (commentSubmissionSuccessful) {
            userInput = ""
            viewModel.setCommentSubmissionSuccessful(false)
        }
        viewModel.loadThread(threadId)
    }

    TheBandBookTheme {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight()
                .padding(horizontal = 15.dp)
        ) {
            if (currentThread != null) {
                VSpacer(height = 30.dp)
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = currentThread.title ?: "",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary)
                VSpacer(height = 30.dp)

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(currentThread.comments) { comment ->
                        CommentItem(comment = comment)
                    }
                }

                OutlinedTextField(
                    value = userInput,
                    onValueChange = { userInput = it },
                    label = { Text("Add a comment") },
                    maxLines = 10,
                    trailingIcon = {
                        IconButton(onClick = {
                            if (currentUser != null) {
                                currentThread?.let { currentThread ->
                                    viewModel.submitComment(userInput, currentThread, currentUser)
                                }
                            }
                        }) {
                            Icon(Icons.Default.Send, contentDescription = "Submit")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                BottomNavCompensationSpacer()
            } else {
                ErrorScreen()
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
                    .padding(15.dp),
                text = comment.content,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary,
                lineHeight = 14.sp,
                overflow = TextOverflow.Ellipsis
            )
        }

    }
}

@Preview
@Composable
fun ForumThreadScreenPreview(){
    ForumThreadScreen(navController = null, threadId = 1)
}

