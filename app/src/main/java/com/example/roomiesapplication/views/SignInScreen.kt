package com.example.roomiesapplication.views

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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
import com.example.roomiesapplication.viewmodels.SignInScreenViewModel
import com.example.roomiesapplication.widgets.CustomBackNavigationIconButton
import com.example.roomiesapplication.widgets.CustomBottomButton
import com.example.roomiesapplication.widgets.CustomHeader
import com.example.roomiesapplication.widgets.CustomTexField
import com.example.roomiesapplication.widgets.CustomTextButton
import com.example.roomiesapplication.widgets.CustomTextPromptWithButton
import com.example.roomiesapplication.widgets.CustomTopAppBar
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.roomiesapplication.navigation.ScreenRoutes
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    viewModel: SignInScreenViewModel = viewModel(),
    navController: NavController
) {

    val stateData = viewModel.signInState.value
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        CustomTopAppBar(
            title = "Roomies",
            navigationIcon = {
                CustomBackNavigationIconButton {
                    navController.popBackStack()
                }
            }
        )
        CustomHeader(
            headerText = stringResource(R.string.signin_screen_heading),
            headerColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(top = MaterialTheme.spacing.medium)
                .padding(horizontal = MaterialTheme.spacing.extraExtraLarge)
        )
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.padding(top = MaterialTheme.spacing.large)
        ) {
            CustomTexField(
                fieldValue = stateData.Email,
                onValueChange = {
                    viewModel.OnEmailFieldChange(it)
                },
                label = stringResource(id = R.string.email_field_label),
                placeholderText = stringResource(id = R.string.email_field_placeholder),
                leadingIconImageVector = Icons.Filled.Person,
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
                contentDescription = stringResource(id = R.string.password_description),
                errorText = stateData.PasswordErrorMsg,
                enableSecureField = true
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        CustomTextPromptWithButton(
            startText = "Don't have an account?",
            buttonText = "Register here.",
            modifier = Modifier.padding(bottom = MaterialTheme.spacing.medium)
        ) {
            navController.navigate(ScreenRoutes.Registration.route)
        }
        CustomBottomButton(
            text = "Sign in",
            textColor = MaterialTheme.colorScheme.onPrimary,
            textWeight = FontWeight.SemiBold,
            backgroundColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = MaterialTheme.spacing.large)
        ) {
            coroutineScope.launch {
                val response = viewModel.SignIn()
                if (response != null) {
                    Toast.makeText(
                        context,
                        stateData.SignInErrorMsg,
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate(ScreenRoutes.Home.route)
                } else {
                    Toast.makeText(
                        context,
                        stateData.SignInErrorMsg,
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
fun SignInScreenPreview() {
    SignInScreen()
}
 */