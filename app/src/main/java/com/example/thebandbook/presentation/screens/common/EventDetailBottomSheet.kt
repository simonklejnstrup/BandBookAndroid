package com.example.thebandbook.presentation.screens.common

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thebandbook.domain.model.Event
import com.example.thebandbook.presentation.screens.calendar.AddressBox
import com.example.thebandbook.presentation.screens.calendar.FinanceSection
import com.example.thebandbook.presentation.screens.calendar.MapButton
import com.example.thebandbook.presentation.screens.calendar.NoteSection
import com.example.thebandbook.presentation.screens.calendar.ScheduleSection
import com.example.thebandbook.presentation.viewmodels.SharedEventDetailsBottomSheetViewModel
import com.example.thebandbook.util.HSpacer
import com.example.thebandbook.util.VSpacer
import com.example.thebandbook.util.openMap
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailBottomSheet(viewModel: SharedEventDetailsBottomSheetViewModel) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val bottomSheetState by viewModel.bottomSheetState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val selectedEvent by viewModel.selectedEvent.collectAsState()


    LaunchedEffect(bottomSheetState) {
        if (bottomSheetState == BottomSheetState.Open && !sheetState.isVisible) {
            coroutineScope.launch { sheetState.show() }
        } else if (bottomSheetState == BottomSheetState.Closed && sheetState.isVisible) {
            coroutineScope.launch { sheetState.hide() }
        }
    }

    if (selectedEvent != null) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.closeBottomSheet() },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            windowInsets = WindowInsets(0)
        ) {

            EventDetailBottomSheetContent(
                event = selectedEvent
            )
        }
    }

}

@Composable
fun EventDetailBottomSheetContent(event: Event?) {
    val context = LocalContext.current
    event?.let {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        AddressBox(event = event)
                        VSpacer(height = 4.dp)
                        Text(
                            text = event.date.format(DateTimeFormatter.ofPattern("yyyy")),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                        )
                    }
                    HSpacer(width = 10.dp)

                    Column(
                        modifier = Modifier
                            .weight(3f),
                        horizontalAlignment = Alignment.Start
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
                        Row {
                            Text(
                                modifier = Modifier.weight(5f),
                                text = event.address,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                                lineHeight = 13.sp
                            )
                            Spacer(modifier = Modifier.weight(1f))

                        }
                    }
                    MapButton(
                        onClick = {
                            openMap(event.address, context)
                        }
                    )
                }
            }
            VSpacer(height = 10.dp)
            // Contact Information
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                val u = Uri.parse("tel:" + event.telephoneNumberOfContactPerson)
                val i = Intent(Intent.ACTION_DIAL, u)
                IconButton(
                    onClick = {
                        try {
                            context.startActivity(i)
                        } catch (s: SecurityException) {

                            Toast.makeText(context, "An error occurred", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Call,
                        contentDescription = "Call person of contact"
                    )
                }
                HSpacer(8.dp)
                Text(
                    text = event.nameOfContactPerson,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            VSpacer(height = 10.dp)

            // Schedule - Icons and times
            // Repeat this Row for each schedule item: get in, soundcheck, concert, end

            ScheduleSection(event = event)

            VSpacer(height = 25.dp)

            Text(
                text = "Concert length: ${event.numberOfSets} x ${event.lengthOfEachSet}",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyLarge
            )

            VSpacer(height = 25.dp)

            FinanceSection(event = event)

            VSpacer(height = 25.dp)

            NoteSection(event = event)

            VSpacer(height = 80.dp)

        }
    }
}