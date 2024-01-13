package com.example.thebandbook.presentation.screens.authentication

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.thebandbook.authentication.GoogleAuthClientSingleton
import com.example.thebandbook.navigation.AppRoutes
import com.example.thebandbook.navigation.NavigationEvent
import com.example.thebandbook.presentation.screens.common.GreenWideButton
import com.example.thebandbook.presentation.screens.common.PasswordTextField
import com.example.thebandbook.presentation.screens.common.VSpacer
import com.example.thebandbook.presentation.screens.common.WideOutlinedTextFieldWithStartIcon
import com.example.thebandbook.presentation.screens.dashboard.TitleAndLogoRow
import com.example.thebandbook.presentation.viewmodels.FirebaseSignInViewmodel
import com.example.thebandbook.presentation.viewmodels.SignInViewModel
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
//    state: SignInState,
//    onGoogleSignInClick: () -> Unit,
    navController: NavController
) {
    val firebaseSignInViewModel = viewModel<FirebaseSignInViewmodel>()
    val state by firebaseSignInViewModel.signInState.collectAsStateWithLifecycle()
    val viewModel: SignInViewModel = viewModel()
    val signInData by viewModel.signInData.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        if (GoogleAuthClientSingleton.googleAuthUiClient.getSignedInUser() != null) {
            navController.navigate(AppRoutes.DASHBOARD)
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == ComponentActivity.RESULT_OK) {
                coroutineScope.launch {
                    val signInResult =
                        GoogleAuthClientSingleton.googleAuthUiClient.signInWithIntent(
                            intent = result.data ?: return@launch
                        )
                    firebaseSignInViewModel.onSingInResult(signInResult)
                }
            }
        })

    LaunchedEffect(key1 = state.isSignInSuccesful) {
        if (state.isSignInSuccesful) {
            navController.navigate(AppRoutes.DASHBOARD)
            firebaseSignInViewModel.resetState()
        }
    }


    LaunchedEffect(key1 = state.signInErrorMessage) {
        state.signInErrorMessage?.let { error ->
            Toast.makeText(
                context, error, Toast.LENGTH_LONG
            ).show()
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is NavigationEvent.NavigateToSignUp -> navController.navigate(AppRoutes.SIGN_UP_SCREEN)
                else -> {}
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        VSpacer(height = 100.dp)

        TitleAndLogoRow()

        VSpacer(height = 70.dp)

        WideOutlinedTextFieldWithStartIcon(
            modifier = Modifier,
            value = signInData.email,
            label = "Mail",
            onValueChange = { viewModel.setEmail(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        PasswordTextField(
            password = signInData.password,
            onPasswordChange = { viewModel.setPassword(it) },
            modifier = Modifier
                .fillMaxWidth()
        )

        VSpacer(height = 8.dp)

        TextButton(onClick = {
            viewModel.navigateToSignUpScreen()
        }) {
            Text(
                text = "Sign up",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight(600)
            )
        }

        VSpacer(height = 8.dp)


        GreenWideButton(
            label = "Sign in",
            onClick = { //Todo:
            })

        VSpacer(height = 70.dp)

        Button(
            onClick = {
                coroutineScope.launch {
                    val signInIntentSender = GoogleAuthClientSingleton.googleAuthUiClient.signIn()
                    launcher.launch(
                        IntentSenderRequest.Builder(
                            signInIntentSender ?: return@launch
                        ).build()
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(4.dp)
                ),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.inversePrimary,
                disabledContentColor = MaterialTheme.colorScheme.surface
            )

        ) {
            Text("Sign in with Google")
        }
    }
}