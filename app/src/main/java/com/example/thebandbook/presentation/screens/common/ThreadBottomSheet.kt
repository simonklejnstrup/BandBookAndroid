package com.example.thebandbook.presentation.screens.common

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.example.thebandbook.domain.model.ForumThread
import com.example.thebandbook.presentation.viewmodels.SharedThreadBottomSheetViewModel
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

            ThreadBottomSheetContent(thread = selectedThread)
        }
    }
}

@Composable
fun ThreadBottomSheetContent(thread: ForumThread?) {
    thread?.let {
        Text("${thread.comments[0].content}")
    }
}