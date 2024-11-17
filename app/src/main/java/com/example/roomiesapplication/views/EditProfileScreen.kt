package com.example.roomiesapplication.views

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.roomiesapplication.R
import com.example.roomiesapplication.ui.theme.spacing
import com.example.roomiesapplication.viewmodels.EditProfileScreenViewModel
import com.example.roomiesapplication.widgets.CustomBackNavigationIconButton
import com.example.roomiesapplication.widgets.CustomBottomButton
import com.example.roomiesapplication.widgets.CustomTexField
import com.example.roomiesapplication.widgets.CustomTopAppBar
import kotlinx.coroutines.launch

@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: EditProfileScreenViewModel = viewModel(),
    navController: NavController
) {

    val stateData = viewModel.editProfileScreenState.value
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTopAppBar(
            title = "Roomies",
            navigationIcon = {
                CustomBackNavigationIconButton {
                    navController.popBackStack()
                }
            }
        )
        CustomTexField(
            fieldValue = stateData.Fullname,
            onValueChange = {
                viewModel.OnFullnameFieldChange(it)
            },
            label = stringResource(id = R.string.fullname_field_label),
            placeholderText = stringResource(id = R.string.fullname_field_placeholder),
            contentDescription = stringResource(id = R.string.fullname_description),
            errorText = stateData.FullnameErrorMsg,
            leadingIconImageVector = Icons.Filled.Info,
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.medium)
        )
        CustomTexField(
            fieldValue = stateData.Username,
            onValueChange = {
                viewModel.OnUsernameFieldChange(it)
            },
            label = stringResource(id = R.string.username_field_label),
            placeholderText = stringResource(id = R.string.username_field_placeholder),
            contentDescription = stringResource(id = R.string.username_description),
            errorText = stateData.UsernameErrorMsg,
            leadingIconImageVector = Icons.Filled.Person,
            modifier = Modifier.padding(bottom = MaterialTheme.spacing.medium),
        )
        CustomTexField(
            fieldValue = stateData.ProfileDescription,
            onValueChange = {
                viewModel.OnProfileDescriptionFieldChange(it)
            },
            label = stringResource(id = R.string.profile_description_field_label),
            placeholderText = stringResource(id = R.string.profile_description_field_placeholder),
            contentDescription = stringResource(id = R.string.profile_description_description),
            errorText = stateData.ProfileDescriptionError,
            leadingIconImageVector = Icons.Filled.Face,
            modifier = Modifier.padding(bottom = MaterialTheme.spacing.medium),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium)
        ) {
            Text(
               "Notify others that you are searching for roommates",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.weight(1f))
            Checkbox(
                checked = stateData.IsAcceptingRoommates,
                onCheckedChange ={
                    viewModel.OnAcceptingStatusChange(it)
                }
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        CustomBottomButton(
            text = "Save profile",
            textColor = MaterialTheme.colorScheme.onPrimary,
            textWeight = FontWeight.SemiBold,
            backgroundColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = MaterialTheme.spacing.large)
        ) {
            coroutineScope.launch {
                val result = viewModel.UpdateUserEntry()
                if (result) {
                    Toast.makeText(
                        context,
                        stateData.EditProfileErrorMsg,
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.popBackStack()
                } else {
                    Toast.makeText(
                        context,
                        stateData.EditProfileErrorMsg,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}