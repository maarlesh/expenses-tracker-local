package com.local_expenses.presentation.ui.creation_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.local_expenses.presentation.theme.AppGradientBrush2
import androidx.compose.ui.graphics.RectangleShape
import com.local_expenses.presentation.ui.common.BottomNavBar


enum class CreationTab {
    Income, Expense, Transfer, Category
}

@Composable
fun CreationScreen(
    navController: NavController,
    viewModel: CreationScreenViewModel,
    userId : Int,
    onHomeTapped: () -> Unit,
    ) {
    var selectedTab by remember { mutableStateOf(CreationTab.Income) }
    val accounts by viewModel.accounts.collectAsState()
    val categories by viewModel.categories.collectAsState()

    Box(
        modifier = Modifier
            .background(AppGradientBrush2)
            .systemBarsPadding()
    ) {
        Column(
            Modifier
                .fillMaxSize(),
        ) {
            // Tab Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                CreationTabItem(
                    label = "Income",
                    isSelected = selectedTab == CreationTab.Income,
                    onClick = { selectedTab = CreationTab.Income }
                )
                CreationTabItem(
                    label = "Expense",
                    isSelected = selectedTab == CreationTab.Expense,
                    onClick = { selectedTab = CreationTab.Expense }
                )
                if(accounts.count() > 1)
                CreationTabItem(
                    label = "Transfer",
                    isSelected = selectedTab == CreationTab.Transfer,
                    onClick = { selectedTab = CreationTab.Transfer }
                )
                CreationTabItem(
                    label = "Category",
                    isSelected = selectedTab == CreationTab.Category,
                    onClick = { selectedTab = CreationTab.Category }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (selectedTab) {
                CreationTab.Income -> CreateIncome(viewModel, accounts, categories)
                CreationTab.Expense -> CreateExpense(viewModel, accounts, categories)
                CreationTab.Transfer -> CreateTransfer(viewModel, accounts)
                CreationTab.Category -> CreateCategory(viewModel, userId)
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                BottomNavBar(
                    onHomeClicked = onHomeTapped
                )
            }
        }
    }
}

@Composable
fun CreationTabItem(label: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected)
        Color.White.copy(alpha = 0.6f)
    else
        Color.White.copy(alpha = 0.25f)

    val borderColor = if (isSelected)
        MaterialTheme.colorScheme.primary
    else
        Color.White.copy(alpha = 0.12f)

    Box(
        modifier = Modifier
            .background(backgroundColor,)
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    listOf(borderColor, borderColor)
                ),
                shape = RectangleShape
            )
            .clickable { onClick() }
            .padding(20.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            maxLines = 1
        )
    }
}

@Composable
fun ExpenseContent(viewModel: CreationScreenViewModel) {
    // TODO: Your expense creation UI goes here
    Text(
        "Expense form or content here",
        modifier = Modifier.padding(16.dp),
        color = Color.White
    )
}

@Composable
fun TransferContent(viewModel: CreationScreenViewModel) {
    // TODO: Your transfer creation UI goes here
    Text(
        "Transfer form or content here",
        modifier = Modifier.padding(16.dp),
        color = Color.White
    )
}


@Composable
fun AccountDropdown(
    accounts: List<String>,
    selectedAccount: String,
    onAccountSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(

    ) {
        OutlinedTextField(
            value = selectedAccount,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true },
            readOnly = true,
            label = { Text("Select Account", color = Color.White) },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown",
                        tint = Color.White
                    )
                }
            },
            singleLine = true,
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier =  Modifier.background(
                AppGradientBrush2
            )
        ) {
            accounts.forEach { account ->
                DropdownMenuItem(
                    text = { Text(account) },
                    onClick = {
                        onAccountSelected(account)
                        expanded = false
                    }
                )
            }
        }
    }
}
