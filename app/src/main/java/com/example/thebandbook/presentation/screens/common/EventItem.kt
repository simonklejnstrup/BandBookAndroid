package com.example.thebandbook.presentation.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thebandbook.domain.model.Event
import com.example.thebandbook.presentation.screens.calendar.AddressBox
import com.example.thebandbook.presentation.screens.calendar.MapButton
import com.example.thebandbook.ui.theme.TheBandBookTheme
import com.example.thebandbook.util.openMap

@Composable
fun EventItem(
    event: Event,
    onClick: (Event) -> Unit
) {
    val shape = RoundedCornerShape(12.dp)
    val context = LocalContext.current

    TheBandBookTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape)
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                )
                .clickable { onClick(event) }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.Top
            ) {
                AddressBox(
                    event = event
                )
                HSpacer(width = 8.dp)
                Column(
                    modifier = Modifier
                        .weight(1f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = event.title,
                        fontSize = 22.sp,
                        fontWeight = FontWeight(700),
                        style = LocalTextStyle.current.merge(
                            TextStyle(
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                ),
                                lineHeightStyle = LineHeightStyle(
                                    alignment = LineHeightStyle.Alignment.Top,
                                    trim = LineHeightStyle.Trim.Both
                                )
                            )
                        ),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = event.address,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = 16.sp
                    )
                }
                HSpacer(width = 8.dp)
                MapButton(
                    onClick = {
                        openMap(event.address, context)
                    }
                )
            }

        }
    }
}