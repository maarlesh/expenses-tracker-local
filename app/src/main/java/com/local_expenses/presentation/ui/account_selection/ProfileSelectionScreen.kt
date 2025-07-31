package com.local_expenses.presentation.account_selection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.local_expenses.data.local.entity.UserEntity

@Composable
fun ProfileSelectionScreen(
    users: List<UserEntity>,
    onNextClick: (UserEntity) -> Unit
) {
    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    // 1. Vibrant GRADIENT background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF00F2FE), // electric cyan
                        Color(0xFF4A00E0), // electric blue
                        Color(0xFFBC6FF1)  // radiant purple
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(1000f, 2000f)
                )
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Color.White.copy(alpha = 0.18f), RoundedCornerShape(32.dp))
                .border(
                    1.5.dp,
                    Brush.linearGradient(
                        listOf(
                            Color.White.copy(alpha = 0.7f),
                            Color.White.copy(alpha = 0.15f)
                        )
                    ),
                    RoundedCornerShape(32.dp)
                )
                .padding(horizontal = 28.dp, vertical = 26.dp)
        ) {
            Text(
                text = "I'm signing up as",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF2E233D),
                lineHeight = 34.sp
            )

            Spacer(Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                users.take(2).forEachIndexed { index, user ->
                    SelectionProfileCard(
                        name = user.name,
                        isSelected = selectedIndex == index,
                        onClick = { selectedIndex = index }
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            AppButton(
                onClick = { selectedIndex?.let { onNextClick(users[it]) } },
                enabled = selectedIndex != null,
                text = "Next",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun SelectionProfileCard(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor =
        if (isSelected) Color.White.copy(alpha = 0.58f) else Color.White.copy(alpha = 0.22f)
    val borderColor =
        if (isSelected) Color(0xFFFF2E63) else Color.White.copy(alpha = 0.45f)
    val textColor =
        if (isSelected) Color(0xFFFF2E63) else Color(0xFF949CA9)
    val iconTint = textColor
    val alpha = if (isSelected) 1f else 0.65f

    Column(
        modifier = Modifier
            .width(120.dp)
            .clickable { onClick() }
            .background(backgroundColor, RoundedCornerShape(24.dp))
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(vertical = 24.dp)
            .alpha(alpha),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = "profile",
            tint = iconTint,
            modifier = Modifier.size(54.dp)
        )
        Spacer(Modifier.height(12.dp))
        Text(name, color = textColor)
    }
}

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
            containerColor = Color(0xFFFF2E63),
            disabledContainerColor = Color.LightGray,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(28.dp)
    ) {
        Text(text)
    }
}
