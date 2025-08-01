package com.local_expenses.presentation.ui.account_selection

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SelectionProfileCard(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor =
        if (isSelected) Color.White.copy(alpha = 0.44f) else Color.White.copy(alpha = 0.22f)
    val borderColor =
        if (isSelected) MaterialTheme.colorScheme.secondaryContainer else Color.White.copy(alpha = 0.45f)
    val textColor =
        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary
    val iconTint = textColor
    val alpha = if (isSelected) 1f else 0.65f
    Log.d("Color Themes", textColor.toString());
    Column(
        modifier = Modifier
            .width(120.dp)
            .clickable { onClick() }
            .background(backgroundColor, RoundedCornerShape(24.dp))
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(18.dp)
            )
            .padding(vertical = 24.dp)
            .alpha(alpha),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.PersonPin,
            contentDescription = "profile",
            tint = iconTint,
            modifier = Modifier.size(54.dp)
        )
        Spacer(Modifier.height(12.dp))
        Text(
            name,
            color = textColor,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}