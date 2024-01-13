package com.example.thebandbook.presentation.screens.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.thebandbook.presentation.viewmodels.CreateEventViewModel

@Composable
fun CustomSwitchWithLabel(
    modifier: Modifier = Modifier,
    viewModel: CreateEventViewModel
) {
    val onPrimary30 = MaterialTheme.colorScheme.onPrimary.copy(0.3f)
    val onPrimary = MaterialTheme.colorScheme.onPrimary
    Column {
        Row {
            Text(
                text = "45 min",
                // Implement correct logic
                color = if (true) onPrimary30 else onPrimary,
                style = MaterialTheme.typography.labelSmall
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "60 min",
                // Implement correct logic
                color = if (true) onPrimary else onPrimary30,
                style = MaterialTheme.typography.labelSmall
            )

        }
        Spacer(Modifier.height(5.dp))
//        CustomSwitch(
//            checked = viewModel.switchState.value,
//            onCheckedChange = { isChecked ->
////                viewModel.onLengthOfSetPressed(isChecked)
////                viewModel.switchState.value = isChecked
//            },
//            modifier = Modifier
//                .padding(end = 12.dp)
//        )
    }
}