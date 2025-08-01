package com.local_expenses.presentation.ui.account_selection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.local_expenses.data.local.entity.UserEntity
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import com.local_expenses.presentation.theme.AppGradientBrush
import com.local_expenses.presentation.theme.AppGradientBrush2
import com.local_expenses.presentation.ui.common.AppButton


@Composable
fun ProfileSelectionScreen(
    users: List<UserEntity>,
    onNextClick: (UserEntity) -> Unit
) {
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppGradientBrush2)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
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
                text = "I am signing up as",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                lineHeight = 34.sp
            )
            Spacer(Modifier.height(24.dp))
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 140.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier
                    .heightIn(max = 300.dp)
                    .fillMaxWidth()
            ) {
                itemsIndexed(users) { index, user ->
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