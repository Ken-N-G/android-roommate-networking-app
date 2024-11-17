package com.example.roomiesapplication.views

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.roomiesapplication.R
import com.example.roomiesapplication.ui.theme.spacing
import com.example.roomiesapplication.viewmodels.RegistrationScreenViewModel
import com.example.roomiesapplication.widgets.CustomBackNavigationIconButton
import com.example.roomiesapplication.widgets.CustomBottomButton
import com.example.roomiesapplication.widgets.CustomHeader
import com.example.roomiesapplication.widgets.CustomTexField
import com.example.roomiesapplication.widgets.CustomTextPromptWithButton
import com.example.roomiesapplication.widgets.CustomTopAppBar
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.roomiesapplication.navigation.ScreenRoutes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    viewModel: RegistrationScreenViewModel = viewModel(),
    navController: NavController
) {
    val stateData = viewModel.registrationState.value
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        CustomTopAppBar(
            title = "Roomies",
            navigationIcon = {
                CustomBackNavigationIconButton {
                    navController.popBackStack()
                }
            }
        )
        CustomHeader(
            headerText = stringResource(R.string.registration_screen_heading),
            headerColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(
                    top = MaterialTheme.spacing.medium,
                    bottom = MaterialTheme.spacing.large
                )
                .padding(horizontal = MaterialTheme.spacing.extraExtraLarge)
        )
        CustomTexField(
            fieldValue = stateData.Email,
            onValueChange = {
                viewModel.OnEmailFieldChange(it)
            },
            label = stringResource(id = R.string.email_field_label),
            placeholderText = stringResource(id = R.string.email_field_placeholder),
            leadingIconImageVector = Icons.Filled.Email,
            contentDescription = stringResource(id = R.string.email_description),
            errorText = stateData.EmailErrorMsg,
            modifier = Modifier.padding(bottom = MaterialTheme.spacing.medium)
        )
        CustomTexField(
            fieldValue = stateData.Password,
            onValueChange = {
                viewModel.OnPasswordFieldChange(it)
            },
            label = stringResource(id = R.string.password_field_label),
            placeholderText = stringResource(id = R.string.password_field_placeholder),
            leadingIconImageVector = Icons.Filled.Lock,
            contentDescription = stringResource(id = R.string.username_description),
            errorText = stateData.PasswordErrorMsg,
            modifier = Modifier.padding(bottom = MaterialTheme.spacing.medium),
            enableSecureField = true
        )
        CustomTexField(
            fieldValue = stateData.Fullname,
            onValueChange = {
                viewModel.OnFullnameFieldChange(it)
            },
            label = stringResource(id = R.string.fullname_field_label),
            placeholderText = stringResource(id = R.string.fullname_field_placeholder),
            leadingIconImageVector = Icons.Filled.Info,
            contentDescription = stringResource(id = R.string.fullname_description),
            errorText = stateData.FullnameErrorMsg,
            modifier = Modifier.padding(bottom = MaterialTheme.spacing.medium)
        )
        CustomTexField(
            fieldValue = stateData.Username,
            onValueChange = {
                viewModel.OnUsernameFieldChange(it)
            },
            label = stringResource(id = R.string.username_field_label),
            placeholderText = stringResource(id = R.string.username_field_placeholder),
            leadingIconImageVector = Icons.Filled.Person,
            contentDescription = stringResource(id = R.string.username_description),
            errorText = stateData.UsernameErrorMsg
        )
        Spacer(modifier = Modifier.weight(1f))
        CustomTextPromptWithButton(
            startText = "Already have an account?",
            buttonText = "Sign in here."
        ) {
            navController.navigate(ScreenRoutes.SignIn.route)
        }
        CustomBottomButton(
            text = "Register",
            textColor = MaterialTheme.colorScheme.onPrimary,
            textWeight = FontWeight.SemiBold,
            backgroundColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = MaterialTheme.spacing.large)
        ) {
            coroutineScope.launch {
                viewModel.Register()
                delay(3000L)
                if(stateData.RegistrationSuccessful) {
                    Toast.makeText(
                        context,
                        stateData.RegistrationErrorMsg,
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate(ScreenRoutes.Home.route)
                } else {
                    Toast.makeText(
                        context,
                        stateData.RegistrationErrorMsg,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
/*
@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    RegistrationScreen()
}

 */