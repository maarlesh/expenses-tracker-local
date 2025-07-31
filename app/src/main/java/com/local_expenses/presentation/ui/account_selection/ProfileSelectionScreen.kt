package com.local_expenses.presentation.account_selection

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.local_expenses.data.local.entity.AccountEntity
import com.local_expenses.data.local.entity.UserEntity

@Composable
fun ProfileSelectionScreen(
    users: List<UserEntity>,
    onNextClick: (UserEntity) -> Unit
) {
    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "I'm signing up as",
            style = MaterialTheme.typography.titleLarge
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Log.d("Get Users", users.toString());
            users.forEachIndexed { index, account ->
                val isSelected = index == selectedIndex

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { selectedIndex = index }
                        .border(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) Color.Magenta else Color.LightGray,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // You can use Image when you add icon/image support
                    // Example: Image(painter = ..., contentDescription = ...)
                    Text(
                        text = account.name,
                        color = if (isSelected) Color.Magenta else Color.Gray,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }

        AppButton(
            onClick = {
                selectedIndex?.let { onNextClick(users[it]) }
            },
            enabled = selectedIndex != null,
            text = "Next"
        )
    }
}


@Composable
fun AppButton(
    onClick: () -> Unit,
    enabled: Boolean,
    text: String,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .padding(top = 16.dp)
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.shadow(6.dp, shape = RoundedCornerShape(28.dp)),
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

