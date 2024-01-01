package com.example.thebandbook.presentation.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thebandbook.data.mockThreads
import com.example.thebandbook.domain.model.Comment
import com.example.thebandbook.domain.model.ForumThread
import com.example.thebandbook.presentation.viewmodels.SharedThreadBottomSheetViewModel
import com.example.thebandbook.ui.theme.TheBandBookTheme
import com.example.thebandbook.util.VSpacer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThreadBottomSheet(viewModel: SharedThreadBottomSheetViewModel) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val bottomSheetState by viewModel.bottomSheetState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val selectedThread by viewModel.selectedThread.collectAsState()

    LaunchedEffect(bottomSheetState) {
        if (bottomSheetState == BottomSheetState.Open && !sheetState.isVisible) {
            coroutineScope.launch { sheetState.show() }
        } else if (bottomSheetState == BottomSheetState.Closed && sheetState.isVisible) {
            coroutineScope.launch { sheetState.hide() }
        }
    }

    if (selectedThread != null) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.closeBottomSheet() },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            windowInsets = WindowInsets(0)
        ) {
            ThreadBottomSheetContent(
                thread = selectedThread,
                viewModel = viewModel)
        }
    }
}

@Composable
fun ThreadBottomSheetContent(
    thread: ForumThread?,
    viewModel: SharedThreadBottomSheetViewModel
) {
    Column(modifier = Modifier.fillMaxSize()) {  // Parent Column to fill the entire bottom sheet
        thread?.let {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),  // Make LazyColumn flexible in height
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(thread.comments) { comment ->
                    CommentItem(comment = comment)
                }
            }
        }

        val textFieldValue by viewModel.textFieldValue.collectAsState()
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = {
                if (thread != null) {
                    viewModel.submitComment(it, thread)
                }
            },
            label = { Text("Add a comment") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        VSpacer(height = 50.dp)
    }
}

@Composable
fun CommentItem(comment: Comment) {
    TheBandBookTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
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
    val viewModel: SharedThreadBottomSheetViewModel = viewModel()
    ThreadBottomSheetContent(mockThreads[0],viewModel )
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