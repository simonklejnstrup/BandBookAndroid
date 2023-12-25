package com.example.thebandbook.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thebandbook.data.mockEvents
import com.example.thebandbook.domain.Model.EventType
import com.example.thebandbook.ui.theme.TheBandBookTheme


@Composable
fun CalendarScreen(
//    navController: NavController,
    modifier: Modifier = Modifier
) {

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

                Text(
                    text = "Say something",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(30.dp)
                )

                Box {

                    val eventsGroupedByYear = mockEvents.groupBy { it.date.year }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        //                contentPadding = PaddingValues(vertical = 17.dp),
                        verticalArrangement = Arrangement.spacedBy(17.dp),
                    ) {
                        eventsGroupedByYear.forEach { (year, events) ->
                            item {
                                // Display the year as a divider
                                YearDivider(year)
                            }
                            items(events.size) { index ->
                                val event = events[index]
                                val eventType = EventType.fromString(event.type)
                                EventItem(eventType)
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
                        onClick = { /* Handle FAB click here */ },
                        containerColor =
                        MaterialTheme.colorScheme.secondary,
                        //                        Color.Yellow,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "Add",
                            modifier = Modifier.size(40.dp)
                        )
                    }

                }
            }

        }
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
fun EventItem(eventType: EventType) {
    val shape = RoundedCornerShape(12.dp)
    TheBandBookTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .clip(shape)
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AddressBox(
                eventType = eventType)

            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "40 års",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight(700),
                )
                Text(
                    text = "Tølløse",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight(700)
                )
            }
            Box(modifier = Modifier.padding(end = 15.dp)) {
                MapButton()
            }
        }

    }
}

@Composable
fun MapButton() {
    val shape = RoundedCornerShape(12.dp)
    Button(
        onClick = { /*TODO*/ },
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
fun AddressBox(eventType: EventType) {
    TheBandBookTheme {
        val shape = RoundedCornerShape(12.dp)
        Box(modifier = Modifier.padding(horizontal = 15.dp, vertical = 15.dp)) {
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clip(shape)
                    .background(
                        color = when (eventType) {
                            EventType.Gig -> MaterialTheme.colorScheme.primary
                            EventType.Rehearsal -> MaterialTheme.colorScheme.tertiary
                            EventType.Meeting -> MaterialTheme.colorScheme.secondary
                        }
                    )

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                    Text(
                        text = "Jul",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight(700)
                    )
                    Text(text = "22")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview() {
    TheBandBookTheme {
        CalendarScreen()
    }
}
@Preview(showBackground = true)
@Composable
fun AdressBoxGigPreview() {
    TheBandBookTheme {
        AddressBox(EventType.Gig)
    }
}
@Preview(showBackground = true)
@Composable
fun AdressBoxRehearsalPreview() {
    TheBandBookTheme {
        AddressBox(EventType.Rehearsal)
    }
}
@Preview(showBackground = true)
@Composable
fun AdressBoxMeetingPreview() {
    TheBandBookTheme {
        AddressBox(EventType.Meeting)
    }
}


