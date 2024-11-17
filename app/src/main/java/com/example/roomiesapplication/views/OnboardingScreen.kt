package com.example.roomiesapplication.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.roomiesapplication.R
import com.example.roomiesapplication.navigation.ScreenRoutes
import com.example.roomiesapplication.ui.theme.spacing
import com.example.roomiesapplication.widgets.CustomBody
import com.example.roomiesapplication.widgets.CustomBottomButton
import com.example.roomiesapplication.widgets.CustomHeader

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(top = MaterialTheme.spacing.medium),
            painter = painterResource(id = R.drawable.hang_out_pana),
            contentDescription = stringResource(id = R.string.onboard_description)
        )
        CustomHeader(
            headerText = stringResource(id = R.string.onboard_screen_heading),
            headerColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(
                    top = MaterialTheme.spacing.medium
                )
                .padding(horizontal = MaterialTheme.spacing.extraExtraLarge)
        )
        CustomBody(
            bodyText = stringResource(id = R.string.onboard_screen_body),
            bodyColor = MaterialTheme.colorScheme.outline,
            modifier = Modifier
                .padding(top = MaterialTheme.spacing.large)
                .padding(horizontal = MaterialTheme.spacing.extraExtraLarge)
        )
        Spacer(modifier = Modifier.weight(1f))
        CustomBottomButton(
            text = "Get Started",
            textColor = MaterialTheme.colorScheme.onPrimary,
            textWeight = FontWeight.SemiBold,
            backgroundColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = MaterialTheme.spacing.large)
        ) {
            navController.navigate(ScreenRoutes.SignIn.route)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen(navController = rememberNavController())
}