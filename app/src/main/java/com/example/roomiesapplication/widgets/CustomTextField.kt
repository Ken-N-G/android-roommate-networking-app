package com.example.roomiesapplication.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomiesapplication.R
import com.example.roomiesapplication.ui.theme.NotificationRed
import com.example.roomiesapplication.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTexField(
    fieldValue: String,
    onValueChange: (fieldValue: String) -> Unit,
    label: String,
    placeholderText: String,
    leadingIconImageVector: ImageVector? = null,
    contentDescription: String,
    errorText: String,
    counterText: String = "",
    enableSecureField: Boolean = false,
    modifier: Modifier = Modifier
) {
    var revealPassword = remember { mutableStateOf(false) }
    Column(
        modifier = modifier.width(280.dp),
        horizontalAlignment = Alignment.Start
    ) {
        OutlinedTextField(
            value = fieldValue,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.spacing.extraSmall),
            label = {
                Text(
                    label,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.outline
                )
            },
            placeholder = {
                Text(
                    placeholderText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.outline
                )
            },
            leadingIcon = {
                if (leadingIconImageVector != null) {
                    Icon(
                        imageVector = leadingIconImageVector,
                        contentDescription = contentDescription,
                        tint = MaterialTheme.colorScheme.outline
                    )
                }
            },
            trailingIcon = {
                if (enableSecureField) {
                    if (revealPassword.value) {
                        IconButton(
                            onClick = {
                                revealPassword.value = false
                            },) {
                            Icon(
                                painter = painterResource(id = R.drawable.visibility_on),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.outline
                            )
                        }
                    } else {
                        IconButton(
                            onClick = {
                                revealPassword.value = true
                            }) {
                            Icon(
                                painter = painterResource(id = R.drawable.visibility_off),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            },
            visualTransformation =
            if(revealPassword.value || !enableSecureField) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
                   },
            shape = RoundedCornerShape(25)
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                errorText,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
                color = NotificationRed
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                counterText,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}
