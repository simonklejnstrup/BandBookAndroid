package com.example.thebandbook.presentation.screens.common

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.thebandbook.domain.model.ForumThread
import com.example.thebandbook.util.RedVerticalDivider
import com.example.thebandbook.util.formatInstantToDateAndMonth
import com.example.thebandbook.util.formatInstantToHoursAndMinutes

@Composable
fun ThreadInfoRow(
    modifier: Modifier = Modifier,
    thread: ForumThread
) {
    val onPrimary50 = MaterialTheme.colorScheme.onPrimary.copy(alpha = .5f)
    MaterialTheme {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = formatInstantToHoursAndMinutes(thread.createdAt),
                style = MaterialTheme.typography.bodyMedium,
                color = onPrimary50
            )
            RedVerticalDivider()
            Text(
                text = formatInstantToDateAndMonth(thread.createdAt),
                style = MaterialTheme.typography.bodyMedium,
                color = onPrimary50
            )
            RedVerticalDivider()
            Text(
                text = thread.createdBy,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}