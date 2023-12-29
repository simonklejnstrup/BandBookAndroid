package com.example.thebandbook.presentation.screens.calendar


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.thebandbook.R
import com.example.thebandbook.domain.Model.EventType
import com.example.thebandbook.navigation.NavigationEvent
import com.example.thebandbook.presentation.screens.GrayDivider
import com.example.thebandbook.presentation.viewmodels.CreateEventViewModel
import com.example.thebandbook.ui.theme.TheBandBookTheme
import com.example.thebandbook.util.CustomSwitch
import com.example.thebandbook.util.VSpacer
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)

@Composable
fun CreateEventScreen(navController: NavController) {
    val viewModel: CreateEventViewModel = viewModel()
    val eventData by viewModel.eventData.collectAsState()

    // Observe navigation events
    LaunchedEffect(key1 = Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is NavigationEvent.NavigateBack -> navController.popBackStack()
                is NavigationEvent.NavigateToDetails -> navController.navigate("details/${event.id}")
            }
        }
    }

    Column {

        NavigationAndSaveRow(viewModel, navController)

        TitleTextField(viewModel, eventData)

        AddressTextField(viewModel, eventData)

        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            SegmentedControl(viewModel = viewModel)

            AnimatedVisibility(
                visible = (viewModel.selectedEventType.value == EventType.Gig)
            ) {
                Column {
                    TimeInputSection(
                        modifier = Modifier
                            .fillMaxWidth(),
                        viewModel,
                        eventData
                    )

                    GrayDivider()

                    Spacer(modifier = Modifier.height(6.dp))

                    SalaryInputSection(
                        modifier = Modifier
                            .fillMaxWidth(),
                        viewModel,
                        eventData
                    )

                    ContactPersonInputRow(viewModel, eventData)

                    VSpacer(height = 6.dp)

                    NoteInputButton(eventData.note) { viewModel.setNote(it) }
                }
            }

            AnimatedVisibility(
                visible = (viewModel.selectedEventType.value == EventType.Meeting)
            ) {
                Column {
                    TimeInputSection(viewModel = viewModel, eventData = eventData)

                    VSpacer(height = 6.dp)

                    NoteInputButton(eventData.note) { viewModel.setNote(it)
                    }
                }
            }

            AnimatedVisibility(
                visible = (viewModel.selectedEventType.value == EventType.Rehearsal)
            ) {
                Column {
                    TimeInputSection(viewModel = viewModel, eventData = eventData)

                    VSpacer(height = 6.dp)

                    NoteInputButton(eventData.note) { viewModel.setNote(it) }
                }
            }

            // Tom compensate for bottom nav
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteInputButton(
    note: String,
    onNoteChange: (String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(4.dp)
    var text by rememberSaveable { mutableStateOf(note) }

    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor =  MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = shape,
        onClick = { showDialog = true }) {
        Text(text = "Note")
    }

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    BasicTextField(
                        value = text,
                        singleLine = false,
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                        onValueChange = {
                            text = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp), // Adjust as needed
                        textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onPrimary)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 6.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            shape = RoundedCornerShape(4.dp),
                            onClick = {
                                onNoteChange(text)
                                showDialog = false
                            }
                        ) {
                            Text("Save")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactPersonInputRow(
    viewModel: CreateEventViewModel,
    eventData: CreateEventViewModel.EventData
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            value = eventData.nameOfContactPerson,
            onValueChange = { viewModel.setNameOfContactPerson(it) },
            label = { Text("Contact person") },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )

        OutlinedTextField(
            value = eventData.telephoneNumberOfContactPerson,
            onValueChange = { viewModel.setTelephoneNumberOfContactPerson(it) },
            label = { Text("Telephone") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        )
    }
}


@Composable
fun NavigationAndSaveRow(
    viewModel: CreateEventViewModel,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "Back"
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        // Submit Button
        Button(
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor =  MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            onClick = { viewModel.submitEvent() },
        ) {
            Text("Submit")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressTextField(
    viewModel: CreateEventViewModel,
    eventData: CreateEventViewModel.EventData
) {
    TextField(
        value = eventData.address,
        onValueChange = { viewModel.setAddress(it) },
        label = { Text("Address") },
        trailingIcon = {
            Icon(
                imageVector = Icons.Outlined.Clear,
                contentDescription = "Clear",
                modifier = Modifier.clickable { viewModel.clearAddress() }
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleTextField(
    viewModel: CreateEventViewModel,
    eventData: CreateEventViewModel.EventData
) {

    TextField(
        value = eventData.title,
        onValueChange = { viewModel.setTitle(it) },
        label = { Text("Title") },
        trailingIcon = {
            Icon(
                imageVector = Icons.Outlined.Clear,
                contentDescription = "Clear",
                modifier = Modifier.clickable { viewModel.clearTitle() }
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
    )
}

@Composable
fun SalaryInputSection(
    modifier: Modifier,
    viewModel: CreateEventViewModel,
    eventData: CreateEventViewModel.EventData
) {
    Column {
        Icon(
            painter = painterResource(id = R.drawable.ic_salary),
            contentDescription = "Money icon",
            modifier = Modifier
                .size(40.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(6.dp))
        SalaryPerPersonPickerRow(viewModel, eventData)
        TransportBudgetPickerRow(viewModel, eventData)
        RentalGearBudgetPickerRow(viewModel, eventData)
        ExtraBudgetPickerRow(viewModel, eventData)

    }
}

@Composable
fun ExtraBudgetPickerRow(
    viewModel: CreateEventViewModel,
    eventData: CreateEventViewModel.EventData
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Budget for extras",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyLarge
        )
        BudgetPickerButton(
            initialPickerNumber = viewModel.eventData.collectAsState().value.extraCosts.toString(),
            currentNumber = viewModel.eventData.collectAsState().value.extraCosts.toString(),
            viewModel = viewModel,
            onNumberSelected = { viewModel.setExtraCosts(it) }
        )
    }
}

@Composable
fun RentalGearBudgetPickerRow(
    viewModel: CreateEventViewModel,
    eventData: CreateEventViewModel.EventData
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Budget for rental gear",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyLarge
        )
        BudgetPickerButton(
            initialPickerNumber = viewModel.eventData.collectAsState().value.costOfRentalGear.toString(),
            currentNumber = viewModel.eventData.collectAsState().value.costOfRentalGear.toString(),
            viewModel = viewModel,
            onNumberSelected = { viewModel.setCostOfRentalGear(it) }
        )
    }
}

@Composable
fun TransportBudgetPickerRow(
    viewModel: CreateEventViewModel,
    eventData: CreateEventViewModel.EventData
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Budget for transport",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyLarge
        )
        BudgetPickerButton(
            initialPickerNumber = viewModel.eventData.collectAsState().value.costOfTransport.toString(),
            currentNumber = viewModel.eventData.collectAsState().value.costOfTransport.toString(),
            viewModel = viewModel,
            onNumberSelected = { viewModel.setCostOfTransport(it) }
        )
    }
}

@Composable
fun SegmentedControl(viewModel: CreateEventViewModel) {
    val selectedEventType by viewModel.selectedEventType

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        EventType.values().forEach { eventType ->
            Button(
                onClick = { viewModel.onEventTypeSelected(eventType) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (eventType == selectedEventType) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = if (eventType == selectedEventType) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
            ) {
                Text(
                    text = eventType.displayName,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


@Composable
fun DatePickerButton(
    viewModel: CreateEventViewModel,
    eventData: CreateEventViewModel.EventData
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Assign default date if not set
    val selectedDate = viewModel.eventData.value.date ?: LocalDate.now()

    // Update the Calendar instance to match the selected date
    calendar.set(selectedDate.year, selectedDate.monthValue - 1, selectedDate.dayOfMonth)

    TextButton(onClick = {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                // Update state with the selected date
                viewModel.setDate(LocalDate.of(year, month + 1, dayOfMonth))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }) {
        Text(
            text = eventData.date?.format(formatter) ?: "Pick a date",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun TimePickerButton(
    viewModel: CreateEventViewModel,
    time: LocalTime,
    onTimeSelected: (LocalTime) -> Unit
) {
    val context = LocalContext.current

    // Set the initial hour and minute
    val hour = time.hour
    val minute = time.minute

    TextButton(onClick = {
        TimePickerDialog(
            context,
            { _, selectedHour, selectedMinute ->
                // Update state with the selected time
                onTimeSelected(LocalTime.of(selectedHour, selectedMinute))
            },
            hour,
            minute,
            true // Use 24-hour view
        ).show()
    }) {
        Text(
            text = time.format(DateTimeFormatter.ofPattern("HH:mm")),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun GetInTimePickerRow(
    viewModel: CreateEventViewModel,
    eventData: CreateEventViewModel.EventData
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Get in",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyLarge
        )

        TimePickerButton(
            viewModel = viewModel,
            time = eventData.timeOfGetIn,
            onTimeSelected = { newTime -> viewModel.setTimeOfGetIn(newTime) }
        )
    }
}


@Composable
fun TimeInputSection(
    modifier: Modifier = Modifier,
    viewModel: CreateEventViewModel,
    eventData: CreateEventViewModel.EventData

) {
    val onPrimary50 = MaterialTheme.colorScheme.onPrimary.copy(0.5f)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_clock),
                contentDescription = "Time section",
                modifier = Modifier
                    .size(40.dp)
                    .fillMaxWidth()
            )
            DatePickerButton(viewModel, eventData)
        }
        Spacer(modifier = Modifier.height(6.dp))

        GetInTimePickerRow(viewModel, eventData)

        if (viewModel.selectedEventType.value == EventType.Gig) {

            SoundcheckTimePickerRow(viewModel, eventData)

            ConcertTimePickerRow(viewModel, eventData)

            SetConcertInfoPickerRow(viewModel, eventData)

            DoneByTimePickerRow(viewModel, eventData)

        }


    }
}

@Composable
fun SetConcertInfoPickerRow(
    viewModel: CreateEventViewModel,
    eventData: CreateEventViewModel.EventData
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier.weight(2f)
        )
        NumberOfSetsPicker(
            modifier = Modifier
                .weight(2f),
            selectedNumber = 1,
            onNumberSelected = { newNumber -> viewModel.setNumberOfSets(newNumber) },
            viewModel = viewModel,
        )
        Icon(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .size(15.dp),
            imageVector = Icons.Filled.Clear,
            contentDescription = "Multiply icon",
            tint = MaterialTheme.colorScheme.primary
        )
        SetLengthSwitchWithLabel(
            modifier = Modifier
                .padding(5.dp)
                .weight(1f),
            viewModel = viewModel
        )
    }
}


@Composable
fun SalaryPerPersonPickerRow(
    viewModel: CreateEventViewModel,
    eventData: CreateEventViewModel.EventData
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Each",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyLarge
        )

        BudgetPickerButton(
            initialPickerNumber = viewModel.eventData.collectAsState().value.salaryPerPerson.toString(),
            currentNumber = viewModel.eventData.collectAsState().value.salaryPerPerson.toString(),
            viewModel = viewModel,
            onNumberSelected = { viewModel.setSalaryPerPerson(it) }
        )
    }
}

@Composable
fun BudgetPickerButton(
    initialPickerNumber: String,
    currentNumber: String,
    viewModel: CreateEventViewModel,
    onNumberSelected: (Int) -> Unit
) {
    val showSalaryDialog = remember { mutableStateOf(false) }

    TextButton(onClick = { showSalaryDialog.value = true }) {
        Text(
            text = currentNumber,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }

    if (showSalaryDialog.value) {
        NumberPickerDialog(
            initialNumber = initialPickerNumber,
            viewModel = viewModel,
            onDismissRequest = { showSalaryDialog.value = false },
            onNumberSelected = {
                onNumberSelected(it)
                showSalaryDialog.value = false
            }
        )
    }
}

@Composable
fun NumberPickerDialog(
    initialNumber: String,
    viewModel: CreateEventViewModel,
    onDismissRequest: () -> Unit,
    onNumberSelected: (Int) -> Unit
) {
    val currentNumber = initialNumber
    var tempNumber by remember { mutableStateOf(currentNumber) }

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier.padding(16.dp),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = tempNumber,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items((1..9).toList()) { number ->
                        NumberButton(number.toString()) {
                            if (tempNumber.isNotEmpty() && tempNumber.toInt() == 0) tempNumber =
                                number.toString() else tempNumber += number.toString()
                        }
                    }
                    item { Spacer(modifier = Modifier.size(56.dp)) } // Empty space
                    item {
                        NumberButton("0") {
                            tempNumber += "0"
                        }
                    }
                    item {
                        IconButton(onClick = {
                            if (tempNumber.isNotEmpty()) tempNumber = tempNumber.dropLast(1)
                        }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = "Delete"
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier.padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = {
                            onNumberSelected(tempNumber.toInt())
                            onDismissRequest()
                        },
                        enabled = tempNumber.isNotEmpty()
                    ) {
                        Text("OK")
                    }
                    Button(onClick = onDismissRequest) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}

@Composable
fun NumberButton(number: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(56.dp)
    ) {
        Text(text = number)
    }
}


@Composable
fun ConcertTimePickerRow(
    viewModel: CreateEventViewModel,
    eventData: CreateEventViewModel.EventData
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Concert",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyLarge
        )

        TimePickerButton(
            viewModel = viewModel,
            time = eventData.timeOfConcert,
            onTimeSelected = { newTime -> viewModel.setTimeOfConcert(newTime) }
        )
    }
}

@Composable
fun SoundcheckTimePickerRow(
    viewModel: CreateEventViewModel,
    eventData: CreateEventViewModel.EventData
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Soundcheck",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyLarge
        )

        TimePickerButton(
            viewModel = viewModel,
            time = eventData.timeOfSoundcheck,
            onTimeSelected = { newTime -> viewModel.setTimeOfSoundcheck(newTime) }
        )
    }
}

@Composable
fun DoneByTimePickerRow(
    viewModel: CreateEventViewModel,
    eventData: CreateEventViewModel.EventData
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Get out",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyLarge
        )

        TimePickerButton(
            viewModel = viewModel,
            time = eventData.timeOfDone,
            onTimeSelected = { newTime -> viewModel.setTimeOfDone(newTime) }
        )
    }
}

@Composable
fun SetLengthSwitchWithLabel(
    modifier: Modifier = Modifier,
    viewModel: CreateEventViewModel
) {
    val onPrimary30 = MaterialTheme.colorScheme.onPrimary.copy(0.3f)
    val onPrimary = MaterialTheme.colorScheme.onPrimary
    Column {
        Row {
            Text(
                text = "45 min",
                color = if (viewModel.switchState.value) onPrimary30 else onPrimary,
                style = MaterialTheme.typography.labelSmall
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "60 min",
                color = if (viewModel.switchState.value) onPrimary else onPrimary30,
                style = MaterialTheme.typography.labelSmall
            )

        }
        Spacer(Modifier.height(5.dp))
        CustomSwitch(
            checked = viewModel.switchState.value,
            onCheckedChange = { isChecked ->
                viewModel.onLengthOfSetPressed(isChecked)
                viewModel.switchState.value = isChecked
            },
            modifier = Modifier
                .padding(end = 12.dp)
        )
    }
}

@Composable
fun NumberOfSetsPicker(
    modifier: Modifier = Modifier,
    selectedNumber: Int,
    onNumberSelected: (Int) -> Unit,
    viewModel: CreateEventViewModel
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        // Numbers 1 to 4
        (1..4).forEach { number ->
            Text(
                text = number.toString(),
                style = MaterialTheme.typography.headlineLarge,
                color = if (viewModel.eventData.value.numberOfSets == number) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimary.copy(
                    0.3f
                ),
                modifier = Modifier
                    .padding(1.dp)
                    .clickable { onNumberSelected(number) }
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun CreateEventScreenPreview() {
    val navController = rememberNavController()

    TheBandBookTheme {
        CreateEventScreen(navController = navController)
    }
}



