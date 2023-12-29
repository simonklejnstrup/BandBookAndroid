package com.example.thebandbook.presentation.screens.calendar

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.thebandbook.R
import com.example.thebandbook.data.mockEvents
import com.example.thebandbook.domain.Model.Event
import com.example.thebandbook.domain.Model.EventType
import com.example.thebandbook.navigation.AppRoutes
import com.example.thebandbook.ui.theme.TheBandBookTheme
import com.example.thebandbook.util.HSpacer
import com.example.thebandbook.util.VSpacer
import com.example.thebandbook.util.openMap
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val skipPartiallyExpanded by remember { mutableStateOf(true) }
    val edgeToEdgeEnabled by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )
    var selectedEvent by remember { mutableStateOf<Event?>(null) }


    TheBandBookTheme {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight() // This will make the Column take up the entire available height
                    .then(modifier), // Apply any additional modifiers passed to this composable
                verticalArrangement = Arrangement.Center, // This centers the children vertically
                horizontalAlignment = Alignment.CenterHorizontally // Center children horizontally as well
            ) {

                Box {

                    val eventsGroupedByYear = mockEvents.groupBy { it.date.year }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        //                contentPadding = PaddingValues(vertical = 17.dp),
                        verticalArrangement = Arrangement.spacedBy(17.dp),
                    ) {
                        eventsGroupedByYear.forEach { (year, events) ->
                            item {
                                YearDivider(year)
                            }
                            items(events.size) { index ->
                                val event = events[index]

                                EventItem(
                                    event = event,
                                    onClick = {
                                        selectedEvent = event
                                        openBottomSheet = !openBottomSheet
                                    }
                                )
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(80.dp))
                        }
                    }
                    // Gradient overlay
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 1f),
                                        Color.Transparent
                                    ),
                                    startY = Float.POSITIVE_INFINITY,
                                    endY = 0f
                                )
                            )
                    )
                    FloatingActionButton(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(bottom = 96.dp, end = 16.dp),
                        onClick = { navController.navigate(AppRoutes.CREATE_EVENT_SCREEN) },
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "Add",
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    // Sheet content
                    if (openBottomSheet) {
                        val windowInsets = if (edgeToEdgeEnabled)
                            WindowInsets(0) else BottomSheetDefaults.windowInsets

                        ModalBottomSheet(
                            onDismissRequest = { openBottomSheet = false },
                            sheetState = bottomSheetState,
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            windowInsets = windowInsets
                        ) {

                            EventDetailSheetContent(
                                event = selectedEvent
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EventDetailSheetContent(
    event: Event?
) {
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

@Composable
fun NoteSection(event: Event) {
    // Notes
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .fillMaxWidth()
            .heightIn(max = 200.dp)
            .padding(12.dp)
    ) {

        LazyColumn {
            items(listOf(event.note)) { item ->
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}

@Composable
fun FinanceSection(event: Event) {
    Column {
        Icon(
            painter = painterResource(id = R.drawable.ic_salary),
            contentDescription = "Money icon",
            modifier = Modifier
                .size(40.dp)
                .fillMaxWidth()
        )
        VSpacer(height = 8.dp)
        Row(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FinancialItem(amount = event.salaryPerPerson, label = "Per Person")
            FinancialItemDivider()
            FinancialItem(amount = event.costOfRentalGear, label = "Rental Gear")
            FinancialItemDivider()
            FinancialItem(amount = event.costOfTransport, label = "Transport")
            FinancialItemDivider()
            FinancialItem(amount = event.extraCosts, label = "Extra")
        }
    }
}

@Composable
fun FinancialItemDivider() {
    Box(modifier = Modifier
        .height(40.dp)
        .width(1.dp)
        .background(color = MaterialTheme.colorScheme.primary))
}


@Composable
fun ScheduleSection(event: Event) {
    Column {
        Icon(
            modifier = Modifier
                .size(40.dp),
            painter = painterResource(id = R.drawable.ic_clock),
            contentDescription = "Clock icon")
        VSpacer(height = 8.dp)
        Box {
            Divider(
                thickness = 2.dp,
                modifier = Modifier.padding(top = 47.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SceduleItem(label = "Get in", time = event.timeOfGetIn)
                SceduleItem(label = "Soundcheck", time = event.timeOfSoundcheck)
                SceduleItem(label = "Concert", time = event.timeOfConcert)
                SceduleItem(label = "Get out", time = event.timeOfDone)
            }

        }
    }
}

@Composable
fun SceduleItem(label: String, time: LocalTime) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScheduleLabel(label = label)
        VSpacer(height = 3.dp)
        TimeBubble(time = time)
    }
}

@Composable
fun TimeBubble(time: LocalTime) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(
                color = MaterialTheme.colorScheme.primary
            )

    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = time.format(DateTimeFormatter.ofPattern("hh:mm")),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight(700),
        )

    }
}

@Composable
fun ScheduleLabel(label: String) {
    Text(
        text = label,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onPrimary,
        fontWeight = FontWeight(700)
    )
}


@Composable
fun FinancialItem(amount: Int, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
        )
        Text(
            text = "$amount",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun YearDivider(year: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(modifier = Modifier.weight(1f))
        Text(
            text = year.toString(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Divider(modifier = Modifier.weight(1f))
    }
}

@Composable
fun EventItem(
    event: Event,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(12.dp)
    val context = LocalContext.current

    TheBandBookTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .clip(shape)
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                )
                .clickable { onClick() }
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

@Composable
fun MapButton(
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(12.dp)
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(45.dp)
            .width(70.dp),
        shape = shape,
        border = BorderStroke(0.5.dp, Color.Gray),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Filled.Place,
                contentDescription = "Find on map",
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Map",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight(400)
            )
        }
    }
}

@Composable
fun AddressBox(
    modifier: Modifier = Modifier,
    event: Event
) {
    TheBandBookTheme {
        val shape = RoundedCornerShape(12.dp)
        Box(modifier = modifier) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(shape)
                    .background(
                        color = when (EventType.fromString(event.type)) {
                            EventType.Gig -> MaterialTheme.colorScheme.primary
                            EventType.Rehearsal -> MaterialTheme.colorScheme.tertiary
                            EventType.Meeting -> MaterialTheme.colorScheme.secondary
                        }
                    )

            ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 4.dp),
                        text = event.date.format(DateTimeFormatter.ofPattern("MMM")),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight(700),
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 2.dp),
                        text = event.date.format(DateTimeFormatter.ofPattern("dd")),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight(700),
                    )
            }
        }
    }
}


@Preview(showBackground = false)
@Composable
fun EventDetailSheetContentPreview() {
    TheBandBookTheme {
        EventDetailSheetContent(
//            onDismiss = {},
            mockEvents[0]
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview() {
    TheBandBookTheme {
        CalendarScreen(rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun AddressBoxGigPreview() {
    TheBandBookTheme {
        AddressBox(
            event = mockEvents[0]
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddressBoxRehearsalPreview() {
    TheBandBookTheme {
        AddressBox(event = mockEvents[1])
    }
}

@Preview(showBackground = true)
@Composable
fun AddressBoxMeetingPreview() {
    TheBandBookTheme {
        AddressBox(event = mockEvents[3])
    }
}


