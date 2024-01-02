package com.example.thebandbook.presentation.screens.authentication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.thebandbook.navigation.AppRoutes
import com.example.thebandbook.navigation.NavigationEvent
import com.example.thebandbook.presentation.screens.common.GreenWideButton
import com.example.thebandbook.presentation.screens.common.PasswordTextField
import com.example.thebandbook.presentation.screens.common.WideOutlinedTextFieldWithStartIcon
import com.example.thebandbook.presentation.screens.dashboard.TitleAndLogoRow
import com.example.thebandbook.presentation.viewmodels.SignInViewModel
import com.example.thebandbook.util.VSpacer

@Composable
fun SignInScreen(
    navController: NavController
) {
    val viewModel: SignInViewModel = viewModel()
    val signInData by viewModel.signInData.collectAsState()

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
            onValueChange = {viewModel.setEmail(it)},
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
                text =  "Sign up",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight(600)
            )
        }

        VSpacer(height = 8.dp)


        GreenWideButton(
            label = "Sign in",
            onClick = {
                viewModel.onSignInPressed()

            })
    }
}