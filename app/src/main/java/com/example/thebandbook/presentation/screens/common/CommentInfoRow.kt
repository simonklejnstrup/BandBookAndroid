package com.example.thebandbook.presentation.screens.common

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.thebandbook.domain.model.Comment
import com.example.thebandbook.ui.theme.TheBandBookTheme
import com.example.thebandbook.util.RedVerticalDivider
import com.example.thebandbook.util.formatInstantToDateAndMonth
import com.example.thebandbook.util.formatInstantToHoursAndMinutes

@Composable
fun CommentInfoRow(
    modifier: Modifier = Modifier,
    comment: Comment
) {
    TheBandBookTheme {
        val onPrimary50 = MaterialTheme.colorScheme.onPrimary.copy(alpha = .5f)
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = formatInstantToHoursAndMinutes(comment.createdAt),
                style = MaterialTheme.typography.bodyMedium,
                color = onPrimary50
            )
            RedVerticalDivider()
            Text(
                text = formatInstantToDateAndMonth(comment.createdAt),
                style = MaterialTheme.typography.bodyMedium,
                color = onPrimary50
            )
            RedVerticalDivider()
            Text(
                text = comment.createdBy,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}