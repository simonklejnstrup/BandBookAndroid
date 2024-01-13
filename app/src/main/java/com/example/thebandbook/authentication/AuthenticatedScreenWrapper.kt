package com.example.thebandbook.authentication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.thebandbook.navigation.AppRoutes

@Composable
fun AuthenticatedScreenWrapper(
    navController: NavController,
    content: @Composable () -> Unit
) {
    val authViewModel: AuthViewModel = viewModel()

    val isUserAuthenticated = authViewModel.isUserAuthenticated()


    if (isUserAuthenticated) {
        content()
    } else {
        LaunchedEffect(Unit) {
            navController.navigate(AppRoutes.SIGN_IN) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        }
    }
}
