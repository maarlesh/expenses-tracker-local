package com.local_expenses.presentation.ui.common

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppButton(
    onClick: () -> Unit,
    enabled: Boolean,
    text: String,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.shadow(10.dp, RoundedCornerShape(28.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            disabledContainerColor = Color.LightGray,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(28.dp)
    ) {
        Text(text,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}