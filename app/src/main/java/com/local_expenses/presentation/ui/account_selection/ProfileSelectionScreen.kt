package com.local_expenses.presentation.ui.account_selection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.draw.shadow
import com.local_expenses.presentation.theme.AppGradientBrush2
import com.local_expenses.presentation.ui.common.AppButton


@Composable
fun ProfileSelectionScreen(
    users: List<UserEntity>,
    onNextClick: (UserEntity) -> Unit,
    onCreateUserSelected: () -> Unit,
) {
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    Box(
        modifier = Modifier
//            .systemBarsPadding()
            .fillMaxSize()
            .background(AppGradientBrush2)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        if(users.isNotEmpty())
        {
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
        else{
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
                    text = "Welcome to local Expenses",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    lineHeight = 34.sp
                )
                Spacer(Modifier.height(24.dp))
                Box(
                    modifier = Modifier
                        .height(110.dp)
                        .background(Color.White.copy(alpha = 0.25f), RoundedCornerShape(28.dp))
                        .border(
                            1.dp,
                            Brush.linearGradient(
                                listOf(Color.White.copy(alpha = 0.55f), Color.White.copy(alpha = 0.12f))
                            ),
                            RoundedCornerShape(28.dp)
                        )
                        .clickable {
                            onCreateUserSelected()
                        }
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(44.dp)
                                .shadow(10.dp, shape = CircleShape)
                                .background(MaterialTheme.colorScheme.secondaryContainer, shape = CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add",
                                tint = Color.White,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                        Spacer(
                            modifier = Modifier.width(
                                12.dp
                            )
                        )
                        Text(
                            text = "Create a user profile",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                Spacer(Modifier.height(32.dp))
            }
        }
    }
}