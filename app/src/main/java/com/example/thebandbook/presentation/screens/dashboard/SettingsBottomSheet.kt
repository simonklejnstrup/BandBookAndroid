package com.example.thebandbook.presentation.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.thebandbook.presentation.screens.common.BottomSheetState
import com.example.thebandbook.presentation.screens.common.RedWideButton
import com.example.thebandbook.presentation.viewmodels.DashboardViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsBottomSheet(viewModel: DashboardViewModel) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val bottomSheetState by viewModel.bottomSheetState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(bottomSheetState) {
        if (viewModel.bottomSheetState.value == BottomSheetState.Open) {
            coroutineScope.launch { sheetState.show() }
        } else if (bottomSheetState == BottomSheetState.Closed && sheetState.isVisible) {
            coroutineScope.launch { sheetState.hide() }
        }
    }
    ModalBottomSheet(
        onDismissRequest = { viewModel.closeBottomSheet() },
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        windowInsets = WindowInsets(0)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RedWideButton(
                label ="Logout",
                onClick = {viewModel.onLogout()
                }
            )
        }
    }

}