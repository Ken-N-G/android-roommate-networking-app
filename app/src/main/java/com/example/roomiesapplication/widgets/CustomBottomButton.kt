package com.example.roomiesapplication.widgets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomiesapplication.ui.theme.spacing

@Composable
fun CustomBottomButton(
    text: String,
    textColor: Color,
    textWeight: FontWeight,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(25),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        modifier = modifier
            .height(40.dp)
            .width(240.dp),
        contentPadding = PaddingValues(vertical = MaterialTheme.spacing.small),
        enabled = isEnabled
    ){
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = textWeight,
            textAlign = TextAlign.Center,
            color = textColor
        )
    }
}