package com.example.thebandbook.presentation.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.thebandbook.R
import com.example.thebandbook.data.mockEvents
import com.example.thebandbook.data.mockThreads
import com.example.thebandbook.domain.model.Event
import com.example.thebandbook.domain.model.ForumThread
import com.example.thebandbook.domain.model.User
import com.example.thebandbook.navigation.AppRoutes
import com.example.thebandbook.navigation.NavigationEvent
import com.example.thebandbook.presentation.screens.authentication.SignInScreen
import com.example.thebandbook.presentation.screens.calendar.EventItem
import com.example.thebandbook.presentation.screens.common.BottomSheetState
import com.example.thebandbook.presentation.screens.common.CommentInfoRow
import com.example.thebandbook.presentation.screens.common.EventDetailBottomSheet
import com.example.thebandbook.presentation.screens.common.ThreadBottomSheet
import com.example.thebandbook.presentation.screens.common.ThreadBox
import com.example.thebandbook.presentation.viewmodels.DashboardViewModel
import com.example.thebandbook.presentation.viewmodels.SharedEventDetailsBottomSheetViewModel
import com.example.thebandbook.presentation.viewmodels.SharedThreadBottomSheetViewModel
import com.example.thebandbook.ui.theme.TheBandBookTheme
import com.example.thebandbook.util.HSpacer
import com.example.thebandbook.util.VSpacer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    val coroutineScope = rememberCoroutineScope()

    val sharedEventDetailsViewModel: SharedEventDetailsBottomSheetViewModel = viewModel()
    val eventDetailBottomSheetState by sharedEventDetailsViewModel.bottomSheetState.collectAsState()
    val eventDetailModalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val sharedThreadBottomSheetViewModel: SharedThreadBottomSheetViewModel = viewModel()
    val threadBottomSheetState by sharedThreadBottomSheetViewModel.bottomSheetState.collectAsState()
    val threadModalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val dashboardViewModel: DashboardViewModel = viewModel()
//    val dashboardBottomSheetState by dashboardViewModel.bottomSheetState.collectAsState()
//    val dashboardModalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(eventDetailBottomSheetState) {
        when (eventDetailBottomSheetState) {
            BottomSheetState.Open -> coroutineScope.launch { eventDetailModalBottomSheetState.show() }
            BottomSheetState.Closed -> coroutineScope.launch { eventDetailModalBottomSheetState.hide() }
        }
    }

    LaunchedEffect(threadBottomSheetState) {
        when (threadBottomSheetState) {
            BottomSheetState.Open -> coroutineScope.launch { threadModalBottomSheetState.show() }
            BottomSheetState.Closed -> coroutineScope.launch { threadModalBottomSheetState.hide() }
        }
    }

    // Observe navigation events
    LaunchedEffect(key1 = Unit) {
        dashboardViewModel.navigationEvent.collect { event ->
            when (event) {
                is NavigationEvent.NavigateToSignUp -> navController.navigate(AppRoutes.SIGN_UP_SCREEN)
                else -> {}
            }
        }
    }

//    LaunchedEffect(dashboardBottomSheetState) {
//        when (dashboardBottomSheetState) {
//            BottomSheetState.Open -> coroutineScope.launch {
//                dashboardModalBottomSheetState.show() }
//            BottomSheetState.Closed -> coroutineScope.launch { dashboardModalBottomSheetState.hide() }
//        }
//    }

    ThreadBottomSheet(viewModel = sharedThreadBottomSheetViewModel)

    EventDetailBottomSheet(viewModel = sharedEventDetailsViewModel)
    
//    SettingsBottomSheet(viewModel = dashboardViewModel)

    TheBandBookTheme {

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            var isLoggedIn by rememberSaveable { mutableStateOf(true) }
            var currentUser: User = User(
                id = 1,
                firstname = "Simon",
                lastname = "Klejnstrup",
                email = "ok@ok.dk",
                band = "Balkan Basterds"
            )


//        if (isLoggedIn) {
//            DashboardLoggedInContent(
//                user = currentUser,
//                onSettingsClick = {
////                    dashboardViewModel.openDashboardBottomSheet()
//                                  },
//                onThreadSelected = { forumThread ->
//                    sharedThreadBottomSheetViewModel.openBottomSheetWithThread(forumThread)
//                },
//                onEventSelected = { event ->
//                    sharedEventDetailsViewModel.openBottomSheetWithEvent(event)
//                }
//            )

//        } else {
            DashboardNotLoggedInContent(navController = navController)
//        }

            // Compensate for Bottom Navigation Bar
            VSpacer(height = 80.dp)
        }

    }
}

@Composable
fun DashboardLoggedInContent(
    user: User,
    onSettingsClick: () -> Unit,
    onThreadSelected: (ForumThread) -> Unit,
    onEventSelected: (Event) -> Unit,
) {

    TheBandBookTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            DashboardHeader(
                onSettingsClick = onSettingsClick,
                user = user
            )
            VSpacer(height = 10.dp)
            Divider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
            )
            VSpacer(height = 10.dp)
            DashboardThreadsSection(onThreadSelected = onThreadSelected)
            VSpacer(height = 10.dp)
            DashboardEventsSection(onEventSelected = onEventSelected)

        }
    }
}

@Composable
fun DashboardThreadsSection(
    onThreadSelected: (ForumThread) -> Unit
) {
    TheBandBookTheme {
        DashboardSectionHeader(label = "Latest threads")

        VSpacer(height = 10.dp)

        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(mockThreads) { forumThread ->
                ThreadListItem(
                    thread = forumThread,
                    onThreadSelected = onThreadSelected
                )
            }
        }
    }
}

@Composable
fun DashboardEventsSection(
    onEventSelected: (Event) -> Unit
) {

    DashboardSectionHeader(label = "Upcoming events")

    VSpacer(height = 10.dp)

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        items(mockEvents) { event ->
            EventItem(
                event = event,
                onClick = onEventSelected
            )
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}


@Composable
fun DashboardSectionHeader(label: String) {
    Text(
        text = label,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onPrimary,
    )

}

@Composable
fun ThreadListItem(
    thread: ForumThread,
    onThreadSelected: (ForumThread) -> Unit
) {
    Column(
    ) {
        CommentInfoRow(
            modifier = Modifier.padding(start = 15.dp),
            comment = thread.comments[0 ]
        )
        ThreadBox(
            modifier = Modifier
                .width(240.dp)
                .height(200.dp),
            thread = thread,
            onThreadSelected = onThreadSelected,
        )
    }
}



@Composable
fun DashboardHeader(
    onSettingsClick: () -> Unit,
    user: User
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = onSettingsClick) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "Settings"
            )
        }
    }
    Text(
        text = "${user.firstname} ${user.lastname}",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onPrimary,
        fontWeight = FontWeight(700)
    )
}



@Composable
fun DashboardNotLoggedInContent(
    navController: NavController
) {
    SignInScreen(navController)
}




@Composable
fun TitleAndLogoRow() {
    TheBandBookTheme {
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "BandBook",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onPrimary)
            HSpacer(width = 7.dp)
            Icon(
                painter = painterResource(id = R.drawable.logo_bandbook),
                contentDescription = "App logo",
                modifier = Modifier.size(50.dp),
                tint = Color.Unspecified)

        }
    }
}

@Preview(showBackground = false)
@Composable
fun DashboardNotLoggedInContentPreview() {
    TheBandBookTheme {
//        val viewModel: DashboardViewModel = viewModel()
//        val loginData = DashboardViewModel.LoginData("j", "c")
        DashboardNotLoggedInContent(
//            viewModel = viewModel, loginData = loginData,
            navController = rememberNavController()
        )
    }
}


@Preview(showBackground = false)
@Composable
fun DashboardScreenPreview() {
    TheBandBookTheme {
        DashboardLoggedInContent(
            user = User(
                id = 1,
                firstname = "Simon",
                lastname = "Klejnstrup",
                email = "ok@ok.dk",
                band = "Balkan Basterds"
            ),
            onSettingsClick = {},
            onThreadSelected = {},
            onEventSelected = {})
    }
}

@Preview(showBackground = false)
@Composable
fun ThreadListItemPreview() {
    TheBandBookTheme {
//        ThreadListItem(mockThreads[0])
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DashboardScreenPreview() {
//    TheBandBookTheme {
//        DashboardScreen()
//    }
//}
