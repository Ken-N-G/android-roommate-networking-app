package com.example.roomiesapplication.views

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.roomiesapplication.R
import com.example.roomiesapplication.ui.theme.spacing
import com.example.roomiesapplication.viewmodels.AddPostScreenViewModel
import com.example.roomiesapplication.widgets.CustomBackNavigationIconButton
import com.example.roomiesapplication.widgets.CustomBottomButton
import com.example.roomiesapplication.widgets.CustomHeader
import com.example.roomiesapplication.widgets.CustomTexField
import com.example.roomiesapplication.widgets.CustomTextPromptWithButton
import com.example.roomiesapplication.widgets.CustomTopAppBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AddPostScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AddPostScreenViewModel = viewModel()
) {

    val stateData = viewModel.addPostState.value
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
            headerText = stringResource(R.string.add_post_screen_heading),
            headerColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(
                    top = MaterialTheme.spacing.medium,
                    bottom = MaterialTheme.spacing.large
                )
                .padding(horizontal = MaterialTheme.spacing.extraExtraLarge)
        )
        CustomTexField(
            fieldValue = stateData.PostContent,
            onValueChange = {
                viewModel.OnPostContentFieldChange(it)
            },
            label = stringResource(id = R.string.post_content_field_label),
            placeholderText = stringResource(id = R.string.post_content_field_placeholder),
            leadingIconImageVector = Icons.Filled.Info,
            contentDescription = stringResource(id = R.string.post_content_description),
            errorText = stateData.AddPostErrorMsg,
            modifier = Modifier.padding(bottom = MaterialTheme.spacing.medium)
        )
        Spacer(modifier = Modifier.weight(1f))
        CustomBottomButton(
            text = "Add Post",
            textColor = MaterialTheme.colorScheme.onPrimary,
            textWeight = FontWeight.SemiBold,
            backgroundColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = MaterialTheme.spacing.large)
        ) {
            coroutineScope.launch {
                val response = viewModel.AddPost()
                if (response != null) {
                    Toast.makeText(
                        context,
                        stateData.AddPostErrorMsg,
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.popBackStack()
                } else {
                    Toast.makeText(
                        context,
                        stateData.AddPostErrorMsg,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}