package com.example.thebandbook.presentation.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thebandbook.domain.model.ForumThread

@Composable
fun ThreadBox(
    modifier: Modifier = Modifier,
    thread: ForumThread,
    onThreadSelected: (ForumThread) -> Unit
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.tertiaryContainer,
                shape = RoundedCornerShape(size = 16.dp)
            )
            .clickable { onThreadSelected(thread) },
    ) {
        Box(
            modifier = Modifier
                .weight(5f)
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    shape = RoundedCornerShape(size = 16.dp)
                )
        ) {
            Text(
                modifier = Modifier
                    .padding(10.dp),
                text = thread.comments[0].content,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary,
                lineHeight = 14.sp,
                overflow = TextOverflow.Ellipsis
            )
        }
        Divider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f)
        )
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(end = 10.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val textColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = .5f)
            Text(
                text = "${thread.comments.size} comments",
                style = MaterialTheme.typography.bodySmall,
                color = textColor,
            )
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Arrow right",
                tint = textColor
            )
        }

    }
}