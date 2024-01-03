package com.example.thebandbook.presentation.screens.authentication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thebandbook.R
import com.example.thebandbook.presentation.screens.common.GreenWideButton
import com.example.thebandbook.presentation.screens.common.PasswordTextField
import com.example.thebandbook.presentation.screens.common.WideOutlinedTextFieldWithStartIcon
import com.example.thebandbook.presentation.screens.forum.GrayDivider
import com.example.thebandbook.presentation.viewmodels.SignUpViewmodel
import com.example.thebandbook.ui.theme.TheBandBookTheme
import com.example.thebandbook.util.VSpacer

@Composable
fun SignUpScreen(
) {
    val viewModel: SignUpViewmodel = viewModel()
    val newUserData by viewModel.newUserData.collectAsState()

    val columnModifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 18.dp)

    Column{
        NameInputSection(
            columnModifier = columnModifier,
            newUserData = newUserData,
            viewModel = viewModel
        )

        VSpacer(height = 25.dp)

        GrayDivider()

        VSpacer(height = 25.dp)

        EmailAndPasswordInputSection(
            columnModifier = columnModifier,
            newUserData = newUserData,
            viewModel = viewModel
        )

        VSpacer(height = 25.dp)

        GrayDivider()

        VSpacer(height = 25.dp)

        BandInputSection(
            columnModifier = columnModifier,
            newUserData = newUserData,
            viewModel = viewModel
        )

        VSpacer(height = 30.dp)

        Column(
            modifier = columnModifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GreenWideButton(
                label = "Log in",
                onClick = {
                    viewModel.onSignUpPressed()
                })
        }
    }
}

@Composable
fun BandInputSection(
    columnModifier: Modifier,
    newUserData: SignUpViewmodel.NewUserData,
    viewModel: SignUpViewmodel
) {
    Column(
        modifier = columnModifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WideOutlinedTextFieldWithStartIcon(
            modifier = Modifier,
            value = newUserData.band,
            label = "Band",
            onValueChange = { viewModel.setBand(it) },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_music_note),
                contentDescription = "Musical note icon",
                tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = .4f)
            )
        }
    }
}

@Composable
fun EmailAndPasswordInputSection(
    columnModifier: Modifier,
    newUserData: SignUpViewmodel.NewUserData,
    viewModel: SignUpViewmodel
) {
    Column(
        modifier = columnModifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        WideOutlinedTextFieldWithStartIcon(
            modifier = Modifier,
            value = newUserData.email,
            label = "Mail",
            onValueChange = { viewModel.setEmail(it) },
        ) {
            Icon(
                imageVector = Icons.Default.MailOutline,
                contentDescription = "E-mail icon",
                tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = .4f)
            )
        }

        PasswordTextField(
            password = newUserData.password,
            onPasswordChange = { viewModel.setPassword(it) },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Person",
                tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = .4f)
            )
        }
    }
}


@Composable
fun NameInputSection(
    columnModifier: Modifier,
    newUserData: SignUpViewmodel.NewUserData,
    viewModel: SignUpViewmodel
) {
    Column(
        modifier = columnModifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        VSpacer(height = 40.dp)


        WideOutlinedTextFieldWithStartIcon(
            modifier = Modifier,
            value = newUserData.firstname,
            label = "Firstname",
            onValueChange = { viewModel.setFirstname(it) },
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Person",
                tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = .4f)
            )
        }

        WideOutlinedTextFieldWithStartIcon(
            modifier = Modifier,
            value = newUserData.lastname,
            label = "Lastname",
            onValueChange = { viewModel.setLastname(it) },
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Person",
                tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = .4f)
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun SignUpScreenPreview() {
    TheBandBookTheme {
        SignUpScreen()
    }
}

